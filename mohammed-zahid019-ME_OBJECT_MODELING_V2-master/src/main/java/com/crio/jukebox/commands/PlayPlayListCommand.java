package com.crio.jukebox.commands;


    import java.util.List;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.services.IPlayListService;

public class PlayPlayListCommand implements ICommand {

    private IPlayListService playListService;

    public PlayPlayListCommand(IPlayListService playListService) {
        this.playListService = playListService;
    }

    @Override
    public void execute(List<String> data) {
        try {
            Song song = playListService.playPlayList(data.get(1), data.get(2));
            System.out.println("Current Song Playing");
            System.out.println(song.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
