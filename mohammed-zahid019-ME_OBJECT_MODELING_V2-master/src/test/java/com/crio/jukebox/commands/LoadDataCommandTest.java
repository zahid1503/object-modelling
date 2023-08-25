package com.crio.jukebox.commands;

    import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import com.crio.jukebox.services.SongService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("LoadDataCommandTest")
@ExtendWith(MockitoExtension.class)
public class LoadDataCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    SongService songService;
    
    @InjectMocks
    LoadDataCommand loadDataCommand;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Load Data command should work sucessful")
    public void loadData() throws Exception {
        String expected = "Songs Loaded successfully";
        loadDataCommand.execute(List.of("LOAD_DATA", "songs.csv"));
        Assertions.assertEquals(expected, outputStreamCaptor.toString().trim());
        verify(songService, times(1)).loadData("songs.csv");
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
