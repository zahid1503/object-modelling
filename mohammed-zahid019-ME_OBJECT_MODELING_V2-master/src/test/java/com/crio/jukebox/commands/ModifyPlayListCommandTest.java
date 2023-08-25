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
import com.crio.jukebox.services.IPlayListService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("ModifyPlayListCommandTest")
@ExtendWith(MockitoExtension.class)
public class ModifyPlayListCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    IPlayListService playListService;

    @InjectMocks
    ModifyPlayListCommand modifyPlayListCommand;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Modify Playlist Successfully in Adding songs")
    public void modifyPlayListAddSongs() throws Exception {
        // MODIFY-PLAYLIST ADD-SONG { USER_ID } {Playlist-ID } { List of Song IDs }
        // MODIFY-PLAYLIST ADD-SONG 1 1 6 7
        // Playlist ID - { Playlist ID  }
        // Playlist Name - { Playlist Name }
        // Song IDs - { List of final Song IDs }
        PlayList playList = new PlayList("20", "10", "ABC_Playlist", List.of("6", "7", "8"));
        String expected = playList.toString();
        when(playListService.modifyPlayList("ADD-SONG", "10", "20", List.of("6", "7", "8")))
                .thenReturn(playList);
        modifyPlayListCommand
                .execute(List.of("MODIFY-PLAYLIST", "ADD-SONG", "10", "20", "6", "7", "8"));
        Assertions.assertEquals(expected, outputStreamCaptor.toString().trim());

        verify(playListService, times(1)).modifyPlayList("ADD-SONG", "10", "20",
                List.of("6", "7", "8"));
    }


    @Test
    @DisplayName("Modify Playlist Successfully for Delete Song")
    public void modifyPlayListDeleteSongs() throws Exception {
        PlayList playList = new PlayList("20", "10", "ABC_Playlist", List.of("1", "2", "3"));
        String expected = playList.toString();
        when(playListService.modifyPlayList("DELETE-SONG", "10", "20", List.of("6", "7", "8")))
                .thenReturn(playList);
        modifyPlayListCommand
                .execute(List.of("MODIFY-PLAYLIST", "DELETE-SONG", "10", "20", "6", "7", "8"));

        Assertions.assertEquals(expected, outputStreamCaptor.toString().trim());

        verify(playListService, times(1)).modifyPlayList("DELETE-SONG", "10", "20",
                List.of("6", "7", "8"));
    }
    

    @Test
    @DisplayName("Modify Playlist throws exception in Adding songs")
    public void modifyPlayListAddSongsThrowsException() throws Exception {
        // MODIFY-PLAYLIST ADD-SONG { USER_ID } {Playlist-ID } { List of Song IDs }
        // MODIFY-PLAYLIST ADD-SONG 1 1 6 7
        // Playlist ID - { Playlist ID }
        // Playlist Name - { Playlist Name }
        // Song IDs - { List of final Song IDs }
        String expected = "Some Requested Songs Not Available. Please try again.";
        doThrow(new SongNotAvailableException(expected)).when(playListService)
                .modifyPlayList("ADD-SONG", "10", "20", List.of("6", "7", "8"));
        
        
        modifyPlayListCommand
                .execute(List.of("MODIFY-PLAYLIST", "ADD-SONG", "10", "20", "6", "7", "8"));

        Assertions.assertEquals(expected, outputStreamCaptor.toString().trim());

        verify(playListService, times(1)).modifyPlayList("ADD-SONG", "10", "20",
                List.of("6", "7", "8"));
    }


    @Test
    @DisplayName("Modify Playlist throws exception for Delete Song")
    public void modifyPlayListDeleteSongsException() throws Exception {
        
        String expected =
                "Some Requested Songs for Deletion are not present in the playlist. Please try again.";
        
        doThrow(new SongNotAvailableException(expected)).when(playListService)
                .modifyPlayList("DELETE-SONG", "10", "20", List.of("6", "7", "8"));
        
        modifyPlayListCommand
                .execute(List.of("MODIFY-PLAYLIST", "DELETE-SONG", "10", "20", "6", "7", "8"));

        Assertions.assertEquals(expected, outputStreamCaptor.toString().trim());

        verify(playListService, times(1)).modifyPlayList("DELETE-SONG", "10", "20",
                List.of("6", "7", "8"));
    }


    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
