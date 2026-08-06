package com.confettiweather.model;

public class SpotifyTrackDto {
    private String id;
    private String name;
    private String spotifyUrl;
    private String embedUrl;
    private String imageUrl;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpotifyUrl() { return spotifyUrl; }
    public void setSpotifyUrl(String spotifyUrl) { this.spotifyUrl = spotifyUrl; }
    public String getEmbedUrl() { return embedUrl; }
    public void setEmbedUrl(String embedUrl) { this.embedUrl = embedUrl; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
