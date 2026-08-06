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
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        try {
            return mapper.readTree(response.getBody());
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Spotify response", e);
        }
    }

    public List<SpotifyTrackDto> searchTracks(String query) {
        String token = getAccessToken();
        query = query.replace("band:", "artist:");
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8).replace("+", "%20");
        String url = String.format("https://api.spotify.com/v1/search?q=%s&type=track&limit=20", encodedQuery);
        JsonNode root = get(url, token);
        JsonNode items = root.path("tracks").path("items");

        List<SpotifyTrackDto> tracks = new ArrayList<>();
        if (items.isMissingNode()) return tracks;
        for (JsonNode item : items) {
            SpotifyTrackDto dto = new SpotifyTrackDto();
            dto.setId(item.get("id").asText());
            dto.setName(item.get("name").asText());
            dto.setSpotifyUrl(item.path("external_urls").path("spotify").asText());
            dto.setEmbedUrl("https://open.spotify.com/embed/track/" + dto.getId());
            JsonNode images = item.path("album").path("images");
            if (images.isArray() && images.size() > 0) {
                dto.setImageUrl(images.get(0).get("url").asText());
            }
            tracks.add(dto);
        }
        return tracks;
    }
}
