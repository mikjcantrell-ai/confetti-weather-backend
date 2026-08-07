package com.confettiweather.model;

public class SpotifyTrackDto {
    private String id;
    private String name;
    private String spotifyUrl;
    private String embedUrl;
    private String imageUrl;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    private String title;
    private String artistName;
    private String artistId;
    private String album;
    private Integer releaseYear;

    public String getTitle() { return title != null ? title : name; }
    public void setTitle(String title) { this.title = title; this.name = title; }
    public String getName() { return title != null ? title : name; }
    public void setName(String name) { this.name = name; this.title = name; }
    
    public String getArtistName() { return artistName; }
    public void setArtistName(String artistName) { this.artistName = artistName; }
    public String getArtistId() { return artistId; }
    public void setArtistId(String artistId) { this.artistId = artistId; }
    public String getAlbum() { return album; }
    public void setAlbum(String album) { this.album = album; }
    public Integer getReleaseYear() { return releaseYear; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }

    public String getSpotifyUrl() { return spotifyUrl; }
    public void setSpotifyUrl(String spotifyUrl) { this.spotifyUrl = spotifyUrl; }
    public String getEmbedUrl() { return embedUrl; }
    public void setEmbedUrl(String embedUrl) { this.embedUrl = embedUrl; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
