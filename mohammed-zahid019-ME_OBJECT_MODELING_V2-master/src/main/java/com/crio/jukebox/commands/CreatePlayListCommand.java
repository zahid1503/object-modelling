package com.crio.jukebox.commands;


import java.util.ArrayList;
import java.util.List;
import com.crio.jukebox.entities.PlayList;
import com.crio.jukebox.services.PlayListService;


public class CreatePlayListCommand implements ICommand {

    private PlayListService playListService;


    public CreatePlayListCommand(PlayListService playListService) {
        this.playListService = playListService;
    }


    @Override
    public void execute(List<String> data) {
        try {
            List<String> songs = new ArrayList<>();
            data.stream().skip(3).forEach(e -> {
                songs.add(e);
            });
            PlayList playList = playListService.createPlayList(data.get(1), data.get(2), songs);
            System.out.println("Playlist ID - " + playList.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

