package com.crio.jukebox.commands;

    import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import com.crio.jukebox.entities.Song;
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

@DisplayName("PlaySongCommandTest")
@ExtendWith(MockitoExtension.class)
public class PlaySongCommandTest {
    
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    IPlayListService playListService;

    @InjectMocks
    PlaySongCommand playSongCommand;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    // PLAY-SONG { USER_ID }  BACK
    @Test
    @DisplayName("Play Song Successful- NEXT operation")
    public void playSongSuccessfulNextOperation() {
        Song song = new Song("2", "Palli", "Pop", "No idea", "Regular,Rap");
        String expected = "Current Song Playing" + "\n" + song.toString();

        when(playListService.playPlayListSong("10", "NEXT")).thenReturn(song);
        playSongCommand.execute(List.of("PLAY-SONG","10","NEXT"));
        Assertions.assertEquals(expected, outputStreamCaptor.toString().trim());

        verify(playListService, times(1)).playPlayListSong("10", "NEXT");

    }
    
    @Test
    @DisplayName("Play Song Successful- BACK operation")
    public void playSongSuccessfulBackOperation() {
        Song song = new Song("2", "Palli", "Pop", "No idea", "Regular,Rap");
        String expected = "Current Song Playing" + "\n" + song.toString();

        when(playListService.playPlayListSong("10", "BACK")).thenReturn(song);
        playSongCommand.execute(List.of("PLAY-SONG", "10", "BACK"));
        Assertions.assertEquals(expected, outputStreamCaptor.toString().trim());

        verify(playListService, times(1)).playPlayListSong("10", "BACK");

    }

    @Test
    @DisplayName("Play Song Successful- Song Id")
    public void playSongSuccessfulSongId() {
        Song song = new Song("2", "Palli", "Pop", "No idea", "Regular,Rap");
        String expected = "Current Song Playing" + "\n" + song.toString();

        when(playListService.playPlayListSong("10", "2")).thenReturn(song);
        playSongCommand.execute(List.of("PLAY-SONG", "10", "2"));
        Assertions.assertEquals(expected, outputStreamCaptor.toString().trim());

        verify(playListService, times(1)).playPlayListSong("10", "2");

    }

    @Test
    @DisplayName("Play Song throws exception- Song Id")
    public void playSongThrowsExceptionSongId() {
        
        String expected = "Song Not Found in the current active playlist.";

        doThrow(new SongNotAvailableException(expected))
                .when(playListService).playPlayListSong("10", "2");
        
        playSongCommand.execute(List.of("PLAY-SONG", "10", "2"));

        Assertions.assertEquals(expected, outputStreamCaptor.toString().trim());

        verify(playListService, times(1)).playPlayListSong("10", "2");

    }
}
