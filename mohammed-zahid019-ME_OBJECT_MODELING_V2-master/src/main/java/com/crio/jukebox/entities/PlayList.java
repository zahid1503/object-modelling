package com.crio.jukebox.entities;

//import java.util.ArrayList;
import java.util.List;

public class PlayList extends BaseEntity {
    private String userId;
    private String name;
    private List<String> songs;

    public PlayList(String playListId, String userId, String name, List<String> songs) {
        this.userId = userId;
        this.id = playListId;
        this.name = name;
        this.songs = songs;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSongs() {
        return songs;
    }

    public void setSongs(List<String> songs) {
        this.songs = songs;
    }

    public String songsPrint() {
        String print = "";
        for (int i = 0; i < songs.size(); i++) {
            print += songs.get(i);
            if (i != songs.size() - 1) {
                print += " ";
            }
        }

        return print;
    }

    @Override
    public String toString() {
        return "Playlist ID - " + this.id + "\n" + "Playlist Name - " + this.name + "\n"
                + "Song IDs - " + songsPrint();
    }

}
