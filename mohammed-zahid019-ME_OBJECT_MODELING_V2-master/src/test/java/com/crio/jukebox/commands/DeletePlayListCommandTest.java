package com.crio.jukebox.commands;

    
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import com.crio.jukebox.exceptions.PlayListNotFoundException;
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

@DisplayName("DeletePlayListCommandTest")
@ExtendWith(MockitoExtension.class)
public class DeletePlayListCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    IPlayListService playListService;

    @InjectMocks
    DeletePlayListCommand deletePlayListCommand;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Delete PlayList Successfully")
    public void deletePlayListSuccessfully() {
        String expected = "Delete Successful";

        deletePlayListCommand.execute(List.of("DELETE-PLAYLIST", "2", "10"));

        Assertions.assertEquals(expected, outputStreamCaptor.toString().trim());

        verify(playListService, times(1)).deletePlayList("2", "10");

    }


    @Test
    @DisplayName("Delete PlayList throws NoPlayListFoundException")
    public void deletePlayListThrowException() {
        String expected = "Playlist Not Found";
        doThrow(new PlayListNotFoundException(expected)).when(playListService).deletePlayList("10",
                "20");
        deletePlayListCommand.execute(List.of("DELETE-PLAYLIST", "10", "20"));        
        
        Assertions.assertEquals(expected, outputStreamCaptor.toString().trim());
        verify(playListService, times(1)).deletePlayList("10", "20");
    }


    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
