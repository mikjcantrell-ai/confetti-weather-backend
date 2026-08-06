package com.confettiweather.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Singleton artist profile — always ID = 1.
 * Holds site-wide artist metadata editable through the admin dashboard.
 */
@Entity
@Table(name = "artist_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Display name of the artist / project. */
    @Column(nullable = false)
    private String name = "Confetti Weather";

    /** Public website URL for the artist (e.g. mikstermedia.com). */
    @Column(name = "website_url", length = 1024)
    private String websiteUrl;

    /** Contact / booking email address. */
    @Column(name = "contact_email", length = 512)
    private String contactEmail;

    /** Short tagline shown in headers and track cards. */
    @Column(length = 512)
    private String tagline;

    /** Spotify artist/album profile URL. */
    @Column(name = "spotify_url", length = 1024)
    private String spotifyUrl;

    /** Instagram profile URL. */
    @Column(name = "instagram_url", length = 1024)
    private String instagramUrl;

    /** Facebook page URL. */
    @Column(name = "facebook_url", length = 1024)
    private String facebookUrl;
}
