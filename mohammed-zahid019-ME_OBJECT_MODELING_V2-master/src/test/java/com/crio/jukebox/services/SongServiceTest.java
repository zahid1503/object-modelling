package com.crio.jukebox.services;

    
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.repositories.SongRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("SongServiceTest")
@ExtendWith(MockitoExtension.class)
public class SongServiceTest {
    
    @Mock
    SongRepository songRepository;

    @InjectMocks
    SongService songService;

    @Test
    @DisplayName("Exists by Id")
    public void existsById() {
        when(songRepository.existsById("1")).thenReturn(true);
        Assertions.assertTrue(songService.existById("1"));
        verify(songRepository, times(1)).existsById("1");
    }

    @Test
    @DisplayName("Get Song by Id")
    public void getSongById() {
        Optional<Song> op = Optional.of(new Song("10", "Palli", "Rap", "No Id", "Sid,Ram"));
        when(songRepository.findById("10")).thenReturn(op);
        Assertions.assertEquals(op, songService.getSongById("10"));
        verify(songRepository, times(1)).findById("10");
    }


    @Test
    @DisplayName("Load Data")
    public void loadData() throws Exception {
        when(songRepository.save(any(Song.class))).thenReturn(any(Song.class));
        songService.loadData("songs.csv");
        verify(songRepository, times(30)).save(any(Song.class));
    }
}
