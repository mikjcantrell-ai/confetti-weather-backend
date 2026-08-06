package com.confettiweather.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * JPA entity representing a Confetti Weather song / track.
 *
 * <p>Hibernate will auto-create the {@code songs} table in {@code confetti.db}
 * on first startup (ddl-auto=update).
 */
@Entity
@Table(name = "songs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Display title of the song. */
    @Column(nullable = false)
    private String title;

    /**
     * Spotify track URI or full URL.
     * e.g. "https://open.spotify.com/track/4LvdAmtQev8e3n9pSkXvlu"
     */
    @Column(name = "spotify_url", length = 1024)
    private String spotifyUrl;

    /**
     * Iframe-ready Spotify embed URL.
     * e.g. "https://open.spotify.com/embed/track/4LvdAmtQev8e3n9pSkXvlu"
     */
    @Column(name = "embed_url", length = 1024)
    private String embedUrl;

    /**
     * URL to the cover art / album artwork image.
     * If null, Angular falls back to the default album_art asset.
     */
    @Column(name = "image_url", length = 1024)
    private String imageUrl;

    /** Primary genre tag. e.g. "Roots · Folk · Country" */
    @Column
    private String genre;

    /** Four-digit release year. */
    @Column(name = "release_year")
    private Integer releaseYear;

    /** Comma-separated list of AI tools used to produce the track. */
    @Column(name = "ai_tools_used", length = 512)
    private String aiToolsUsed;

    /**
     * Whether this track is featured on the home page hero / music section.
     * Defaults to true for seeded tracks.
     */
    @Column(name = "featured_status", nullable = false)
    private boolean featuredStatus = true;

    /**
     * Admin-controlled display order. Lower = shown first.
     * Defaults to 0 (insertion order).
     */
    @Column(name = "display_order", nullable = false)
    private int displayOrder = 0;

    /** Short teaser / tagline shown on the track card. */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
