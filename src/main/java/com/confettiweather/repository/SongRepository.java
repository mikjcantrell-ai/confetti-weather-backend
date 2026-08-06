package com.confettiweather.repository;

import com.confettiweather.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    /** Featured songs for the home page, sorted by admin-set display order. */
    List<Song> findByFeaturedStatusTrueOrderByDisplayOrderAsc();

    /** Filter by genre tag. */
    List<Song> findByGenreContainingIgnoreCase(String genre);

    /** All songs sorted by display order then ID. */
    List<Song> findAllByOrderByDisplayOrderAscIdAsc();
}
