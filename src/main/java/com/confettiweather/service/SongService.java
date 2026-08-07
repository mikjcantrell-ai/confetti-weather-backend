package com.confettiweather.service;

import com.confettiweather.model.Song;
import com.confettiweather.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final com.confettiweather.repository.LyricRepository lyricRepository;

    /** All songs ordered by displayOrder then id. */
    public List<Song> getAllSongs() {
        return songRepository.findAllByOrderByDisplayOrderAscIdAsc();
    }

    /** Songs marked featured=true, for the home page. */
    public List<Song> getFeaturedSongs() {
        return songRepository.findByFeaturedStatusTrueOrderByDisplayOrderAsc();
    }

    public Optional<Song> getSongById(Long id) {
        return songRepository.findById(id);
    }

    public List<Song> getSongsByGenre(String genre) {
        return songRepository.findByGenreContainingIgnoreCase(genre);
    }

    public Song createSong(Song song) {
        return songRepository.save(song);
    }

    public Optional<Song> updateSong(Long id, Song updated) {
        return songRepository.findById(id).map(existing -> {
            existing.setTitle(updated.getTitle());
            existing.setSpotifyUrl(updated.getSpotifyUrl());
            existing.setEmbedUrl(updated.getEmbedUrl());
            existing.setImageUrl(updated.getImageUrl());
            existing.setGenre(updated.getGenre());
            existing.setReleaseYear(updated.getReleaseYear());
            existing.setAiToolsUsed(updated.getAiToolsUsed());
            existing.setFeaturedStatus(updated.isFeaturedStatus());
            existing.setDisplayOrder(updated.getDisplayOrder());
            existing.setDescription(updated.getDescription());
            return songRepository.save(existing);
        });
    }

    @org.springframework.transaction.annotation.Transactional
    public void deleteSong(Long id) {
        lyricRepository.deleteBySongId(id);
        songRepository.deleteById(id);
    }
    
    @org.springframework.transaction.annotation.Transactional
    public void batchDelete(List<Long> ids) {
        for (Long id : ids) {
            lyricRepository.deleteBySongId(id);
        }
        songRepository.deleteAllById(ids);
    }
}
