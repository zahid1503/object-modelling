package com.crio.jukebox.entities;

public class Song extends BaseEntity {
    private String name;
    private String genre;
    private String albumName;
    private String featuredArtist;

    public Song(String id, String name, String genre, String albumName, String featuredArtist) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.albumName = albumName;
        this.featuredArtist = featuredArtist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getFeaturedArtist() {
        return featuredArtist;
    }

    public void setFeaturedArtist(String featuredArtist) {
        this.featuredArtist = featuredArtist;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    @Override
    public String toString() {
        return "Song - " + this.name + "\n" + "Album - " + this.albumName + "\n" + "Artists - "
                + this.featuredArtist;
    }
}