package com.confettiweather.controller;

import com.confettiweather.model.SpotifyTrackDto;
import com.confettiweather.service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spotify")
public class SpotifyController {

    @Autowired
    private SpotifyService spotifyService;

    @GetMapping("/artist-songs")
    public ResponseEntity<List<SpotifyTrackDto>> getArtistSongs(@RequestParam String artistUrl) {
        try {
            return ResponseEntity.ok(spotifyService.getArtistSongs(artistUrl));
        } catch (org.springframework.web.client.HttpServerErrorException e) {
            System.err.println("Spotify API Error: " + e.getMessage());
            return ResponseEntity.status(org.springframework.http.HttpStatus.BAD_GATEWAY).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
