package com.crio.jukebox.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.crio.jukebox.entities.PlayList;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.exceptions.InvalidUserException;
import com.crio.jukebox.exceptions.PlayListEmptyException;
import com.crio.jukebox.exceptions.PlayListNotFoundException;
import com.crio.jukebox.exceptions.SongNotAvailableException;
import com.crio.jukebox.repositories.PlayListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("PlayListServiceTest")
@ExtendWith(MockitoExtension.class)
public class PlayListServiceTest {
    
    @Mock
    PlayListRepository playListRepository;

    @Mock
    Song song;

    @Mock
    SongService songService;

    @InjectMocks
    PlayListService playListService;

    @Test
    @DisplayName("Create Playlist")
    public void createPlayList() {
        PlayList playList = new PlayList("10", "20", "PlayList_Name", List.of("1", "2", "3"));
        when(playListRepository.save(any(PlayList.class))).thenReturn(playList);
        when(songService.existById(anyString())).thenReturn(true);

        PlayList actual =
                playListService.createPlayList("20", "PlayList_Name", List.of("1", "2", "3"));

        Assertions.assertEquals(playList, actual);
        verify(playListRepository, times(1)).save(any(PlayList.class));
        verify(songService, times(3)).existById(anyString());
    }
    

    @Test
    @DisplayName("Create PlayList throws exception")
    public void createPlayListThrowsException() {
        when(songService.existById("3")).thenReturn(false);

        Assertions.assertThrows(SongNotAvailableException.class, () -> {
            playListService.createPlayList("20", "PlayList_Name", List.of("3"));
        });

        verify(playListRepository, times(0)).save(any(PlayList.class));
        verify(songService, times(1)).existById("3");
    }
    

    @Test
    @DisplayName("Delete Playlist Successful")
    public void deletePlayListSuccessful() {
        PlayList playList = new PlayList("10", "20", "PlayList_Name", List.of("1", "2", "3"));
        when(playListRepository.findById("10")).thenReturn(Optional.of(playList));

        playListService.deletePlayList("20", "10");

        verify(playListRepository, times(1)).findById("10");
        verify(playListRepository, times(1)).deleteById("10");
    }
    

    @Test
    @DisplayName("Delete Playlist throws exception")
    public void deletePlayListThrowsException() {
        when(playListRepository.findById("10")).thenReturn(Optional.empty());

        Assertions.assertThrows(PlayListNotFoundException.class, () -> {
            playListService.deletePlayList("20", "10");
        });

        verify(playListRepository, times(1)).findById("10");
    }
    

    @Test
    @DisplayName("Modify PlayList Add Songs Successful")
    public void modifyPlayList() {

        List<String> songList = new ArrayList<>();
        songList.addAll(List.of("1", "2", "3"));
        PlayList playList = new PlayList("20", "10", "name", songList);
        when(songService.existById(any())).thenReturn(true);
        when(playListRepository.findById("20")).thenReturn(Optional.of(playList));


        List<String> songsToAdd = new ArrayList<>();
        songsToAdd.add("2");
        songsToAdd.add("4");
        songsToAdd.add("5");
        songsToAdd.add("2");
        PlayList actual = playListService.modifyPlayList("ADD-SONG", "10", "20", songsToAdd);


        Assertions.assertEquals("[1, 2, 3, 4, 5]", actual.getSongs().toString());

        verify(songService, times(4)).existById(anyString());
        verify(playListRepository, times(1)).findById("20");
    }
    

    @Test
    @DisplayName("Modify PlayList Throws Exception")
    public void modifyPlayListThrowsException() {

        List<String> songList = new ArrayList<>();
        songList.addAll(List.of("1", "2", "3"));
        PlayList playList = new PlayList("20", "10", "PlayListName", songList);
        when(songService.existById(anyString())).thenReturn(false);
        when(playListRepository.findById("20")).thenReturn(Optional.of(playList));

        List<String> songsToAdd = new ArrayList<>();
        songsToAdd.add("2");
        songsToAdd.add("4");
        songsToAdd.add("5");
        songsToAdd.add("2");

        Assertions.assertThrows(SongNotAvailableException.class, () -> {
            playListService.modifyPlayList("ADD-SONG", "10", "20", songsToAdd);
        });

    }


    @Test
    @DisplayName("Modify Playlist Delete Songs Successful")
    public void modifyPlaylistDeleteSongs() {
        List<String> songList = new ArrayList<>();
        songList.addAll(List.of("1", "2", "3"));
        PlayList playList = new PlayList("20", "10", "name", songList);
        when(playListRepository.findById("20")).thenReturn(Optional.of(playList));

        List<String> songsToDelete = new ArrayList<>();
        songsToDelete.add("2");
        songsToDelete.add("2");
        PlayList actual = playListService.modifyPlayList("DELETE-SONG", "10", "20", songsToDelete);


        Assertions.assertEquals("[1, 3]", actual.getSongs().toString());

        verify(playListRepository, times(1)).findById("20");
    }

    @Test
    @DisplayName("Modify PlayList Throws Exception - Delete Song")
    public void modifyPlayListThrowsExceptionForDeleteSong() {

        List<String> songList = new ArrayList<>();
        songList.addAll(List.of("1", "2", "3"));
        PlayList playList = new PlayList("20", "10", "PlayListName", songList);
        when(playListRepository.findById("20")).thenReturn(Optional.of(playList));

        List<String> songsToDelete = new ArrayList<>();
        songsToDelete.add("2");
        songsToDelete.add("4");

        Assertions.assertThrows(SongNotAvailableException.class, () -> {
            playListService.modifyPlayList("DELETE-SONG", "10", "20", songsToDelete);
        });
    }


    @Test
    @DisplayName("Play PlayList Successful")
    public void playPlayList() {
        PlayList playList = new PlayList("10", "20", "PlayName_10",
                new ArrayList<>(Arrays.asList("1", "2", "3")));
        when(playListRepository.findById("10")).thenReturn(Optional.of(playList));
        Song song = new Song("1", "Palli", "Soft", "No idea", "Sid,Ram");
        when(songService.getSongById("1")).thenReturn(Optional.of(song));

        Assertions.assertEquals(song, playListService.playPlayList("20", "10"));
        verify(playListRepository, times(1)).findById("10");
        verify(songService, times(1)).getSongById("1");
    }
    

    @Test
    @DisplayName("Play PlayList throws PlayList Not Found Exception")
    public void playPlayListThrowsException() {
        when(playListRepository.findById("10")).thenReturn(Optional.empty());

        Assertions.assertThrows(PlayListNotFoundException.class, () -> {
            playListService.playPlayList("20", "10");
        });

        verify(playListRepository, times(1)).findById("10");
    }


    @Test
    @DisplayName("Play PlayList throws Invalid User Exception")
    public void playPlayListThrowsUserInvalidException() {
        when(playListRepository.findById("10"))
                .thenReturn(Optional.of(new PlayList("10", "15", "Name", List.of("1"))));
        

        Assertions.assertThrows(InvalidUserException.class, () -> {
            playListService.playPlayList("20", "10");
        });

        verify(playListRepository, times(1)).findById("10");
    }


    @Test
    @DisplayName("Play PlayList throws playlist empty Exception")
    public void playPlayListThrowsPlayListEmptyException() {
        when(playListRepository.findById("10"))
                .thenReturn(Optional.of(new PlayList("10", "20", "Name", new ArrayList<>())));


        Assertions.assertThrows(PlayListEmptyException.class, () -> {
            playListService.playPlayList("20", "10");
        });

        verify(playListRepository, times(1)).findById("10");
    }


    @Test
    @DisplayName("Play playlist song throws Invalid User Exception")
    public void playPlayListSong() {
        PlayList playList = new PlayList("10", "20", "Name", new ArrayList<>());
        when(playListRepository.findById(any()))
                .thenReturn(Optional.of(playList));
                

        Assertions.assertThrows(InvalidUserException.class, () -> {
            playListService.playPlayListSong("15", "NEXT");
        });

        verify(playListRepository, times(1)).findById(any());
    }


    @Test
    @DisplayName("Play playlist successful- BACK")
    public void playPlaylistBACKSuccessful() {
        playListService.setRunningPlayListId("10");
        playListService.setRunningSongIndex(4);

        List<String> songList = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5"));
        
        when(playListRepository.findById("10"))
                .thenReturn(Optional.of(new PlayList("10", "20", "Playlist_Name", songList)));

        Song song = new Song("4", "Palli", "Soft", "RappersHere", "Sid,Ram");
        
        when(songService.getSongById("4")).thenReturn(Optional.of(song));

        Assertions.assertEquals(song, playListService.playPlayListSong("20", "BACK"));

        verify(playListRepository, times(1)).findById("10");
        verify(songService, times(1)).getSongById("4");

        playListService.setRunningPlayListId(null);
        playListService.setRunningSongIndex(null);
    }


    @Test
    @DisplayName("Play playlist successful- NEXT")
    public void playPlaylistNEXTSuccessful() {
        playListService.setRunningPlayListId("10");
        playListService.setRunningSongIndex(2);

        List<String> songList = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5"));

        when(playListRepository.findById("10"))
                .thenReturn(Optional.of(new PlayList("10", "20", "Playlist_Name", songList)));

        Song song = new Song("4", "Palli", "Soft", "RappersHere", "Sid,Ram");

        when(songService.getSongById("4")).thenReturn(Optional.of(song));

        Assertions.assertEquals(song, playListService.playPlayListSong("20", "NEXT"));

        verify(playListRepository, times(1)).findById("10");
        verify(songService, times(1)).getSongById("4");

        playListService.setRunningPlayListId(null);
        playListService.setRunningSongIndex(null);
    }


    @Test
    @DisplayName("Play playlist successful- Song Id")
    public void playPlaylistSongIdSuccessful() {
        playListService.setRunningPlayListId("10");
        playListService.setRunningSongIndex(2);

        List<String> songList = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5"));

        when(playListRepository.findById("10"))
                .thenReturn(Optional.of(new PlayList("10", "20", "Playlist_Name", songList)));

        Song song = new Song("1", "Palli", "Soft", "RappersHere", "Sid,Ram");

        when(songService.getSongById("1")).thenReturn(Optional.of(song));

        Assertions.assertEquals(song, playListService.playPlayListSong("20", "1"));

        verify(playListRepository, times(1)).findById("10");
        verify(songService, times(1)).getSongById("1");

        playListService.setRunningPlayListId(null);
        playListService.setRunningSongIndex(null);
    }


    @Test
    @DisplayName("Play playlist throw exception- Song Id")
    public void playPlaylistThrowException() {
        playListService.setRunningPlayListId("10");

        List<String> songList = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5"));

        when(playListRepository.findById("10"))
                .thenReturn(Optional.of(new PlayList("10", "20", "Playlist_Name", songList)));

        Assertions.assertThrows(SongNotAvailableException.class, ()->{
            playListService.playPlayListSong("20", "6");
        });

        verify(playListRepository, times(1)).findById("10");

        playListService.setRunningPlayListId(null);
    }
}
