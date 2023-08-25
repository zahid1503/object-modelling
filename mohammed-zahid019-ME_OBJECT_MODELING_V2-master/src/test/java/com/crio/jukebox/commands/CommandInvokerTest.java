package com.crio.jukebox.commands;


import static org.mockito.ArgumentMatchers.anyList;
import java.util.ArrayList;
import com.crio.jukebox.exceptions.CommandNotPresentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("CommandInvokeTest")
@ExtendWith(MockitoExtension.class)
public class CommandInvokerTest {
    
    @Mock
    LoadDataCommand loadDataCommand;

    @Mock
    CreateUserCommand createUserCommand;

    @Mock
    CreatePlayListCommand createPlayListCommand;

    @Mock
    DeletePlayListCommand deletePlayListCommand;

    @Mock
    ModifyPlayListCommand modifyPlayListCommand;

    @Mock
    PlayPlayListCommand play_PlayListCommand;

    @Mock
    PlaySongCommand playSongCommand;

    @InjectMocks
    CommandInvoker commandInvoker;


    @BeforeEach
    public void setup() {
        commandInvoker.registry("LOAD-DATA", loadDataCommand);
        commandInvoker.registry("CREATE-USER", createUserCommand);
        commandInvoker.registry("CREATE-PLAYLIST", createPlayListCommand);
        commandInvoker.registry("DELETE-PLAYLIST", deletePlayListCommand);
        commandInvoker.registry("MODIFY-PLAYLIST", modifyPlayListCommand);
        commandInvoker.registry("PLAY-PLAYLIST", play_PlayListCommand);
        commandInvoker.registry("PLAY-SONG", playSongCommand);
    }

    @Test
    @DisplayName("Execute Commands - All commands should execute properly")
    public void executeCommands() {
        commandInvoker.executeCommand("LOAD-DATA", anyList());
        commandInvoker.executeCommand("CREATE-USER", anyList());
        commandInvoker.executeCommand("CREATE-PLAYLIST", anyList());
        commandInvoker.executeCommand("DELETE-PLAYLIST", anyList());
        commandInvoker.executeCommand("MODIFY-PLAYLIST", anyList());
        commandInvoker.executeCommand("PLAY-PLAYLIST", anyList());
        commandInvoker.executeCommand("PLAY-SONG", anyList());
    }

    @Test
    @DisplayName("Execute command throw error when incorrect command is passed")
    public void executeCommandThrowException() {
        Assertions.assertThrows(CommandNotPresentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                commandInvoker.executeCommand("Random", new ArrayList<String>());
            }
        });
    }
}
