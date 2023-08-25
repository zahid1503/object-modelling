package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.services.ISongService;
import com.crio.jukebox.services.SongService;

public class LoadDataCommand implements ICommand {


    private ISongService songService;

 
    public LoadDataCommand(SongService songService) {
        this.songService = songService;
    }


    @Override
    public void execute(List<String> data) {
        try {
            songService.loadData(data.get(1));
            System.out.println("Songs Loaded successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
