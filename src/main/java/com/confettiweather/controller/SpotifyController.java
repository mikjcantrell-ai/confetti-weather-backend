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

    @GetMapping("/search")
    public ResponseEntity<List<SpotifyTrackDto>> searchTracks(@RequestParam String query) {
        try {
            return ResponseEntity.ok(spotifyService.searchTracks(query));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
