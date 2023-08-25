package com.crio.jukebox.appConfig;

//import com.crio.codingame.repositories.IUserRepository;


    import com.crio.jukebox.commands.*;
    import com.crio.jukebox.repositories.PlayListRepository;
    import com.crio.jukebox.repositories.SongRepository;
    import com.crio.jukebox.repositories.UserRepository;
    import com.crio.jukebox.services.PlayListService;
    import com.crio.jukebox.services.SongService;
    import com.crio.jukebox.services.UserService;
    
    public class ApplicationConfig {

        private final UserRepository userRepository = new UserRepository();
        private final SongRepository songRepository = new SongRepository();
        private final PlayListRepository playListRepository = new PlayListRepository();
    
        private final UserService userService = new UserService(userRepository);
        private final SongService songService = new SongService(songRepository);
        private final PlayListService playListService =
                new PlayListService(playListRepository, songService);
    
        private final CreateUserCommand createUserCommand = new CreateUserCommand(userService);
        private final CreatePlayListCommand createPlayListCommand =
                new CreatePlayListCommand(playListService);
        private final DeletePlayListCommand deletePlayListCommand =
                new DeletePlayListCommand(playListService);
        private final LoadDataCommand loadDataCommand = new LoadDataCommand(songService);
        private final ModifyPlayListCommand modifyPlayListCommand = new ModifyPlayListCommand(playListService);
        private final PlayPlayListCommand play_PlayListCommand =
                new PlayPlayListCommand(playListService);
        private final PlaySongCommand playSongCommand = new PlaySongCommand(playListService);
        
    
        private final CommandInvoker commandInvoker = new CommandInvoker();
    
    
        public CommandInvoker getCommandInvoker() {
            
            commandInvoker.registry("LOAD-DATA", loadDataCommand);
            commandInvoker.registry("CREATE-USER", createUserCommand);
            commandInvoker.registry("CREATE-PLAYLIST", createPlayListCommand);
            commandInvoker.registry("DELETE-PLAYLIST", deletePlayListCommand);
            commandInvoker.registry("MODIFY-PLAYLIST", modifyPlayListCommand);
            commandInvoker.registry("PLAY-PLAYLIST", play_PlayListCommand);
            commandInvoker.registry("PLAY-SONG", playSongCommand);
    
            return commandInvoker;
        }
        
    
}
