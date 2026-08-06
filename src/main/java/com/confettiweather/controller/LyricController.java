package com.confettiweather.controller;

import com.confettiweather.model.Lyric;
import com.confettiweather.service.LyricService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * REST controller for song lyrics.
 *
 * <p>Public:
 *   GET /api/songs/{songId}/lyrics
 *
 * <p>Admin (HTTP Basic required):
 *   POST   /api/songs/{songId}/lyrics         — add a lyric block
 *   PUT    /api/lyrics/{id}                   — edit a lyric block
 *   DELETE /api/lyrics/{id}                   — delete a lyric block
 *   PUT    /api/songs/{songId}/lyrics/reorder — bulk reorder {id: order}
 */
@RestController
@RequiredArgsConstructor
public class LyricController {

    private final LyricService lyricService;

    /** GET /api/songs/{songId}/lyrics */
    @GetMapping("/api/songs/{songId}/lyrics")
    public List<Lyric> getLyrics(@PathVariable Long songId) {
        return lyricService.getLyricsBySong(songId);
    }

    /** POST /api/songs/{songId}/lyrics — admin: add a block */
    @PostMapping("/api/songs/{songId}/lyrics")
    public Lyric createLyric(@PathVariable Long songId, @RequestBody Lyric lyric) {
        return lyricService.createLyric(songId, lyric);
    }

    /** PUT /api/lyrics/{id} — admin: edit a block */
    @PutMapping("/api/lyrics/{id}")
    public ResponseEntity<Lyric> updateLyric(@PathVariable Long id, @RequestBody Lyric lyric) {
        return lyricService.updateLyric(id, lyric)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** DELETE /api/lyrics/{id} — admin: delete a block */
    @DeleteMapping("/api/lyrics/{id}")
    public ResponseEntity<Void> deleteLyric(@PathVariable Long id) {
        lyricService.deleteLyric(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * PUT /api/songs/{songId}/lyrics/reorder — admin: bulk reorder.
     * Body: { "10": 1, "11": 2, "12": 3, … }
     */
    @PutMapping("/api/songs/{songId}/lyrics/reorder")
    public ResponseEntity<Void> reorder(@PathVariable Long songId,
                                        @RequestBody Map<Long, Integer> orderMap) {
        lyricService.reorder(orderMap);
        return ResponseEntity.ok().build();
    }
}
