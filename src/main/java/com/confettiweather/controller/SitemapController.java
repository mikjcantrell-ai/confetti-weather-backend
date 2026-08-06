package com.confettiweather.controller;

import com.confettiweather.model.Song;
import com.confettiweather.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SitemapController {

    private final SongService songService;

    @GetMapping(value = "/sitemap", produces = MediaType.APPLICATION_XML_VALUE)
    public String getSitemap() {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");

        // Static routes
        xml.append(buildUrl("https://confettiweather.com/", "weekly", "1.0"));
        xml.append(buildUrl("https://confettiweather.com/music", "weekly", "0.9"));
        xml.append(buildUrl("https://confettiweather.com/about", "monthly", "0.8"));
        xml.append(buildUrl("https://confettiweather.com/contact", "monthly", "0.7"));

        // Dynamic routes for songs
        List<Song> songs = songService.getAllSongs();
        for (Song song : songs) {
            xml.append(buildUrl("https://confettiweather.com/lyrics/" + song.getId(), "monthly", "0.6"));
        }

        xml.append("</urlset>");
        return xml.toString();
    }

    private String buildUrl(String loc, String changefreq, String priority) {
        return "  <url>\n" +
               "    <loc>" + loc + "</loc>\n" +
               "    <changefreq>" + changefreq + "</changefreq>\n" +
               "    <priority>" + priority + "</priority>\n" +
               "  </url>\n";
    }
}
