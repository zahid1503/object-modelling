package com.crio.jukebox.commands;


import java.util.ArrayList;
import java.util.List;
import com.crio.jukebox.entities.PlayList;
import com.crio.jukebox.services.IPlayListService;


public class ModifyPlayListCommand implements ICommand {

    private IPlayListService playListService;


    public ModifyPlayListCommand(IPlayListService playListService) {
        this.playListService = playListService;
    }


    @Override
    public void execute(List<String> data) {
        
        List<String> songs = new ArrayList<>();
        data.stream().skip(4).forEach(e -> {
            songs.add(e);
        });

        // MODIFY-PLAYLIST ADD-SONG { USER_ID } {Playlist-ID } { List of Song IDs }
        try {
            PlayList playList =
                    playListService.modifyPlayList(data.get(1), data.get(2), data.get(3), songs);
            System.out.println(playList.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
