package com.crio.jukebox.entities;

    import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

@DisplayName("PlayListTest")
public class PlayListTest {

    @InjectMocks
    PlayList playList;

    @Test
    @DisplayName("Songs Print Successfull")
    public void songsPrint() {
        playList = new PlayList("10", "20", "PlayList Name",
                new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5")));
        String expected = "1 2 3 4 5";

        Assertions.assertEquals(expected, playList.songsPrint());
    }
}
