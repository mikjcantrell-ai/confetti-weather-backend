package com.confettiweather.controller;

import com.confettiweather.model.Song;
import com.confettiweather.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for Confetti Weather songs.
 *
 * <p>Public endpoints (GET) are open to all.
 * Write endpoints (POST/PUT/DELETE) require HTTP Basic admin auth
 * (configured in SecurityConfig).
 */
@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    /** GET /api/songs — all songs, ordered by displayOrder */
    @GetMapping
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    /** GET /api/songs/featured — songs shown on the home page */
    @GetMapping("/featured")
    public List<Song> getFeaturedSongs() {
        return songService.getFeaturedSongs();
    }

    /** GET /api/songs/{id} — single song by ID */
    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id) {
        return songService.getSongById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** GET /api/songs/genre/{genre} — filter by genre tag */
    @GetMapping("/genre/{genre}")
    public List<Song> getSongsByGenre(@PathVariable String genre) {
        return songService.getSongsByGenre(genre);
    }

    /** POST /api/songs — create a new song (admin only) */
    @PostMapping
    public Song createSong(@RequestBody Song song) {
        return songService.createSong(song);
    }

    /** PUT /api/songs/{id} — update a song (admin only) */
    @PutMapping("/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable Long id, @RequestBody Song song) {
        return songService.updateSong(id, song)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** DELETE /api/songs/{id} — remove a song (admin only) */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }
}
