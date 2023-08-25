package com.crio.jukebox.commands;

    
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.exceptions.PlayListEmptyException;
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

@DisplayName("PlayPlayListCommandTest")
@ExtendWith(MockitoExtension.class)
public class PlayPlayListCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    IPlayListService playListService;

    @InjectMocks
    PlayPlayListCommand playPlayListCommand;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }


    @Test
    @DisplayName("Play Playlist Successful")
    public void play_playListSuccessful() {
        // PLAY-PLAYLIST { USER_ID } { Playlist-ID }
        Song song = new Song("1", "Hey Pilla", "smooth", "Rgv", "Sid,Bhanu");
        when(playListService.playPlayList("10", "20")).thenReturn(song);
        String expected = "Current Song Playing" + "\n" + song.toString();

        playPlayListCommand.execute(List.of("PLAY-PLAYLIST", "10", "20"));

        Assertions.assertEquals(expected, outputStreamCaptor.toString().trim());
        verify(playListService, times(1)).playPlayList("10", "20");
    }

    @Test
    @DisplayName("Play Playlist Throws Exception")
    public void play_playListThrowsException() {
        // PLAY-PLAYLIST { USER_ID } { Playlist-ID }
        String expected = "Playlist is empty.";
        doThrow(new PlayListEmptyException(expected)).when(playListService).playPlayList("10",
                "20");

        playPlayListCommand.execute(List.of("PLAY-PLAYLIST", "10", "20"));

        Assertions.assertEquals(expected, outputStreamCaptor.toString().trim());
        verify(playListService, times(1)).playPlayList("10", "20");
    }


    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
