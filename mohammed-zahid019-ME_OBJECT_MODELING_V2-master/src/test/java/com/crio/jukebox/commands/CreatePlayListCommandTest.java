package com.crio.jukebox.commands;


    import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import com.crio.jukebox.entities.PlayList;
import com.crio.jukebox.exceptions.SongNotAvailableException;
import com.crio.jukebox.services.PlayListService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("CreatePlayListCommandTest")
@ExtendWith(MockitoExtension.class)
public class CreatePlayListCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    PlayListService playListService;

    @InjectMocks
    CreatePlayListCommand createPlayListCommand;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }


    @Test
    @DisplayName("Create PlayList Successfully")
    public void createPlayList() {
        String expected = "Playlist ID - 1";
        PlayList playList =
                new PlayList("1", "1", "MY_PLAYLIST_1", List.of("1", "2", "3", "4", "5"));
        when(playListService.createPlayList("1", "MY_PLAYLIST_1", List.of("1", "2", "3", "4", "5")))
                .thenReturn(playList);
        createPlayListCommand.execute(List.of("CREATE-PLAYLIST", "1", "MY_PLAYLIST_1", "1", "2",
                "3", "4", "5"));
        Assertions.assertEquals(expected, outputStreamCaptor.toString().trim());
        verify(playListService, times(1)).createPlayList("1", "MY_PLAYLIST_1",
                List.of("1", "2", "3", "4", "5"));
    }

    @Test
    @DisplayName("Create PlayList Throw SongNotAvailableException")
    public void createPlayListThrowsError() {
        String expected = "Some Requested Songs Not Available. Please try again.";
        doThrow(new SongNotAvailableException(expected))
                        .when(playListService).createPlayList("1", "MY_PLAYLIST_1",
                        List.of("1", "2", "3", "4", "5"));
                                        
        createPlayListCommand
                .execute(List.of("CREATE-PLAYLIST", "1", "MY_PLAYLIST_1", "1", "2", "3", "4", "5"));
        
        Assertions.assertEquals(expected, outputStreamCaptor.toString().trim());

        verify(playListService, times(1)).createPlayList("1", "MY_PLAYLIST_1",
                List.of("1", "2", "3", "4", "5"));
    }
    
    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
