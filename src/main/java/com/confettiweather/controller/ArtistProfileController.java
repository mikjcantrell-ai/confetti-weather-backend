package com.confettiweather.controller;

import com.confettiweather.model.ArtistProfile;
import com.confettiweather.service.ArtistProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for the singleton artist profile.
 *
 * <p>GET /api/artist  — public (used by About page, Music page)
 * <p>PUT /api/artist  — admin (HTTP Basic required)
 */
@RestController
@RequestMapping("/api/artist")
@RequiredArgsConstructor
public class ArtistProfileController {

    private final ArtistProfileService service;

    /** GET /api/artist — fetch the public artist profile */
    @GetMapping
    public ArtistProfile getProfile() {
        return service.getProfile();
    }

    /** PUT /api/artist — admin: update any artist profile fields */
    @PutMapping
    public ArtistProfile updateProfile(@RequestBody ArtistProfile profile) {
        return service.updateProfile(profile);
    }
}
