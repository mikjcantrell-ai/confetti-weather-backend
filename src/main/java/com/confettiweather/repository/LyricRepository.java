package com.confettiweather.repository;

import com.confettiweather.model.Lyric;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LyricRepository extends JpaRepository<Lyric, Long> {

    /**
     * Fetch all lyric blocks for a given song, ordered for display.
     * Used by LyricController GET /api/songs/{songId}/lyrics.
     */
    List<Lyric> findBySongIdOrderByDisplayOrderAsc(Long songId);

    /** Remove all lyrics for a song (e.g. when replacing full lyric content). */
    void deleteBySongId(Long songId);
}
