package com.confettiweather.repository;

import com.confettiweather.model.ArtistProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistProfileRepository extends JpaRepository<ArtistProfile, Long> {
}
