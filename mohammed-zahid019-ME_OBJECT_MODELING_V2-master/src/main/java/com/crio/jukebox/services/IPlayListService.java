package com.crio.jukebox.services;

import java.util.List;
import java.util.Optional;
import com.crio.jukebox.entities.PlayList;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.exceptions.PlayListEmptyException;
import com.crio.jukebox.exceptions.SongNotAvailableException;



    
    
    public interface IPlayListService {
    
        public PlayList createPlayList(String userId, String name, List<String> songs);
    
        public Optional<PlayList> getPlayList(String playListId);
    
        public void deletePlayList(String userId, String playListId);
    
        public PlayList modifyPlayList(String operation, String userId, String entityId,
                List<String> songs) throws SongNotAvailableException;
    
        public Song playPlayList(String userId, String playListId) throws PlayListEmptyException;
    
       public Song playPlayListSong(String userId, String operation) throws SongNotAvailableException;
    
    
}
