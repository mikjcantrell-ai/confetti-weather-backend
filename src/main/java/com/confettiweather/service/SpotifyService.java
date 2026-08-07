package com.confettiweather.service;

import com.confettiweather.model.SpotifyTrackDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpotifyService {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    private String getAccessToken() {
        String url = "https://accounts.spotify.com/api/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        try {
            JsonNode node = mapper.readTree(response.getBody());
            return node.get("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get Spotify token", e);
        }
    }

    private JsonNode get(String url, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(java.net.URI.create(url), HttpMethod.GET, request, String.class);
        try {
            return mapper.readTree(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Spotify response", e);
        }
    }

    public List<SpotifyTrackDto> getArtistSongs(String queryOrUrl) {
        String token = getAccessToken();
        String url;
        
        if (queryOrUrl.contains("artist/")) {
            String[] parts = queryOrUrl.split("artist/");
            String id = parts[1].split("\\?")[0];
            url = String.format("https://api.spotify.com/v1/artists/%s/top-tracks?market=US", id);
        } else if (queryOrUrl.contains("track/")) {
            String[] parts = queryOrUrl.split("track/");
            String id = parts[1].split("\\?")[0];
            url = String.format("https://api.spotify.com/v1/tracks/%s", id);
        } else {
            String query = queryOrUrl.replace("band:", "artist:");
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8).replace("+", "%20");
            url = String.format("https://api.spotify.com/v1/search?q=%s&type=track", encodedQuery);
        }
        
        System.out.println("Calling Spotify URL: " + url);
        JsonNode root = get(url, token);
        List<SpotifyTrackDto> tracks = new ArrayList<>();
        
        if (queryOrUrl.contains("track/")) {
            if (!root.isMissingNode() && root.has("id")) {
                tracks.add(parseTrack(root));
            }
        } else {
            JsonNode items = root.has("tracks") && root.get("tracks").isArray() ? root.get("tracks") : root.path("tracks").path("items");
            if (!items.isMissingNode() && items.isArray()) {
                for (JsonNode item : items) {
                    if (item.has("id")) tracks.add(parseTrack(item));
                }
            }
        }
        return tracks;
    }

    private SpotifyTrackDto parseTrack(JsonNode item) {
        SpotifyTrackDto dto = new SpotifyTrackDto();
        dto.setId(item.get("id").asText());
        dto.setTitle(item.get("name").asText());
        dto.setSpotifyUrl(item.path("external_urls").path("spotify").asText());
        dto.setEmbedUrl("https://open.spotify.com/embed/track/" + dto.getId());
        
        JsonNode artists = item.path("artists");
        if (artists.isArray() && artists.size() > 0) {
            dto.setArtistName(artists.get(0).get("name").asText());
            dto.setArtistId(artists.get(0).get("id").asText());
        }
        
        JsonNode album = item.path("album");
        if (!album.isMissingNode()) {
            dto.setAlbum(album.get("name").asText());
            JsonNode images = album.path("images");
            if (images.isArray() && images.size() > 0) {
                dto.setImageUrl(images.get(0).get("url").asText());
            }
            if (album.has("release_date")) {
                String releaseDate = album.get("release_date").asText();
                if (releaseDate.length() >= 4) {
                    try {
                        dto.setReleaseYear(Integer.parseInt(releaseDate.substring(0, 4)));
                    } catch (Exception e) {}
                }
            }
        }
        return dto;
    }
}
