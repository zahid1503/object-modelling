package com.crio.jukebox.entities;

import java.util.List;

public class Album extends BaseEntity {

        private String name;
        private String artist;
        private List<String> songs;
    
        public Album(String id, String name, String artist) {
            this.id = id;
            this.name = name;
            this.artist = artist;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public String getArtist() {
            return artist;
        }
    
        public void setArtist(String artist) {
            this.artist = artist;
        }
    
        public List<String> getSongs() {
            return songs;
        }
    
        public void setSongs(List<String> songs) {
            this.songs = songs;
        }
    
    
    }
    

