package com.confettiweather.service;

import com.confettiweather.model.Lyric;
import com.confettiweather.model.Song;
import com.confettiweather.repository.LyricRepository;
import com.confettiweather.repository.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LyricService {

    private final LyricRepository lyricRepository;
    private final SongRepository  songRepository;

    /** All lyric blocks for a song, in display order. */
    public List<Lyric> getLyricsBySong(Long songId) {
        return lyricRepository.findBySongIdOrderByDisplayOrderAsc(songId);
    }

    /** Create a new lyric block attached to a song. */
    @Transactional
    public Lyric createLyric(Long songId, Lyric lyric) {
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new EntityNotFoundException("Song not found: " + songId));
        lyric.setSong(song);
        if (lyric.getDisplayOrder() == 0) {
            int maxOrder = lyricRepository.findBySongIdOrderByDisplayOrderAsc(songId)
                    .stream().mapToInt(Lyric::getDisplayOrder).max().orElse(0);
            lyric.setDisplayOrder(maxOrder + 1);
        }
        return lyricRepository.save(lyric);
    }

    /** Update an existing lyric block's label, type, content, or order. */
    @Transactional
    public Optional<Lyric> updateLyric(Long lyricId, Lyric updated) {
        return lyricRepository.findById(lyricId).map(existing -> {
            existing.setSectionLabel(updated.getSectionLabel());
            existing.setSectionType(updated.getSectionType());
            existing.setContent(updated.getContent());
            existing.setDisplayOrder(updated.getDisplayOrder());
            return lyricRepository.save(existing);
        });
    }

    /** Delete a single lyric block by ID. */
    @Transactional
    public void deleteLyric(Long lyricId) {
        lyricRepository.deleteById(lyricId);
    }

    /**
     * Bulk-reorder: accepts a map of {lyricId -> newDisplayOrder} and
     * persists all in one transaction.
     */
    @Transactional
    public void reorder(Map<Long, Integer> orderMap) {
        orderMap.forEach((id, order) ->
            lyricRepository.findById(id).ifPresent(l -> {
                l.setDisplayOrder(order);
                lyricRepository.save(l);
            })
        );
    }

    // Legacy helpers kept for DataSeeder compatibility
    public Lyric saveLyric(Lyric lyric) { return lyricRepository.save(lyric); }
    public void deleteLyricsBySong(Long songId) { lyricRepository.deleteBySongId(songId); }
}
