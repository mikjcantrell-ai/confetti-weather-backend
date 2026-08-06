package com.confettiweather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Confetti Weather Spring Boot REST API.
 *
 * <p>Runs on port 8082 (see application.properties).
 * SQLite database file: {@code confetti.db} (created automatically on first run).
 */
@SpringBootApplication
public class ConfettiWeatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfettiWeatherApplication.class, args);
    }
}
