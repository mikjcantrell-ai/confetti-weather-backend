package com.confettiweather.service;

import com.confettiweather.model.ArtistProfile;
import com.confettiweather.repository.ArtistProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArtistProfileService {

    private final ArtistProfileRepository repo;

    /** Returns the singleton artist profile (always ID = 1). */
    public ArtistProfile getProfile() {
        return repo.findById(1L).orElseGet(() -> {
            // Auto-create on first access if the DataSeeder hasn't run yet
            ArtistProfile p = new ArtistProfile();
            p.setName("Confetti Weather");
            return repo.save(p);
        });
    }

    /** Admin: update the profile fields that are present in the request body. */
    @Transactional
    public ArtistProfile updateProfile(ArtistProfile incoming) {
        ArtistProfile existing = getProfile();
        if (incoming.getName()        != null) existing.setName(incoming.getName());
        if (incoming.getWebsiteUrl()  != null) existing.setWebsiteUrl(incoming.getWebsiteUrl());
        if (incoming.getContactEmail()!= null) existing.setContactEmail(incoming.getContactEmail());
        if (incoming.getTagline()     != null) existing.setTagline(incoming.getTagline());
        if (incoming.getSpotifyUrl()  != null) existing.setSpotifyUrl(incoming.getSpotifyUrl());
        if (incoming.getInstagramUrl()!= null) existing.setInstagramUrl(incoming.getInstagramUrl());
        if (incoming.getFacebookUrl() != null) existing.setFacebookUrl(incoming.getFacebookUrl());
        return repo.save(existing);
    }
}
