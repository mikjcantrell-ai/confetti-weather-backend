package com.confettiweather.repository;

import com.confettiweather.model.SiteContent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SiteContentRepository extends JpaRepository<SiteContent, String> {
    List<SiteContent> findAllByOrderBySectionAscKeyAsc();
}
