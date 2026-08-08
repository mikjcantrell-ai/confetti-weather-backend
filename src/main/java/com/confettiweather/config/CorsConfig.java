package com.confettiweather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Global CORS configuration for the Confetti Weather API.
 *
 * <p>The Angular dev server runs at {@code http://localhost:4201}.
 * This allows that origin to reach all {@code /api/**} endpoints.
 * In production, replace with the deployed frontend URL.
 */
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOriginPatterns("http://localhost:*", "https://confettiweather.com", "https://www.confettiweather.com")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                        .allowedHeaders("Content-Type", "Authorization", "Accept",
                                        "X-Requested-With", "Cache-Control")
                        .exposedHeaders()
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}
