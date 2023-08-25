package com.crio.jukebox.commands;


    import java.util.List;
import com.crio.jukebox.services.IPlayListService;

public class DeletePlayListCommand implements ICommand {

    private IPlayListService playListService;

    public DeletePlayListCommand(IPlayListService playListService) {
        this.playListService = playListService;
    }

    @Override
    public void execute(List<String> data) {
        try {
            playListService.deletePlayList(data.get(1), data.get(2));
            System.out.println("Delete Successful");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
