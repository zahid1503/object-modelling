package com.crio.jukebox.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import com.crio.jukebox.entities.PlayList;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.exceptions.InvalidUserException;
import com.crio.jukebox.exceptions.PlayListEmptyException;
import com.crio.jukebox.exceptions.PlayListNotFoundException;
import com.crio.jukebox.exceptions.SongNotAvailableException;
import com.crio.jukebox.repositories.PlayListRepository;

//import com.crio.jukebox.repositories.JUserRepository;


public class PlayListService implements IPlayListService {

    private PlayListRepository playListRepository;
    private ISongService songService;
    private String runningPlayListId;
    private Integer runningSongIndex;

    public PlayListService(PlayListRepository playListRepository, ISongService songService) {
        this.playListRepository = playListRepository;
        this.songService = songService;
    }

    @Override
    public PlayList createPlayList(String userId, String name, List<String> songs) {

        isSongsExistInPool(songs);

        return playListRepository.save(new PlayList(null, userId, name, songs));
    }

    @Override
    public Optional<PlayList> getPlayList(String playListId) {
        return playListRepository.findById(playListId);
    }

    @Override
    public void deletePlayList(String userId, String playListId) {
        Optional<PlayList> optionalPlayList = playListRepository.findById(playListId);
        if (optionalPlayList.isEmpty()) {
            throw new PlayListNotFoundException("Playlist Not Found");
        }
        if (!optionalPlayList.get().getUserId().equals(userId)) {
            throw new InvalidUserException("Not a valid user to perform operation");
        }
        playListRepository.deleteById(playListId);
    }

    @Override
    public PlayList modifyPlayList(String operation, String userId, String playListId,
            List<String> songs) throws SongNotAvailableException {
                
        Optional<PlayList> optional = playListRepository.findById(playListId);
        if (optional.isEmpty()) {
            throw new PlayListNotFoundException("Playlist not found");
        }
        PlayList playList = optional.get();
        if (!playList.getUserId().equals(userId)) {
            throw new InvalidUserException("Not a valid user to perform operation");
        }
        if (operation.equalsIgnoreCase("ADD-SONG")) {
            playList = addSongsToPlayList(playList, songs);
        } else if (operation.equalsIgnoreCase("DELETE-SONG")) {
            playList = deleteSongsFromPlayList(playList, songs);
        }
        playListRepository.save(playList);
        return playList;
    }

    public PlayList addSongsToPlayList(PlayList playList, List<String> songs) {
        isSongsExistInPool(songs);
        Set<String> checkSet = new HashSet<>(playList.getSongs());
        songs.forEach(song -> {
            if (!checkSet.contains(song)) {
                playList.getSongs().add(song);
            }
        });
        return playList;
    }

    public void isSongsExistInPool(List<String> songs) {
        for (String song : songs) {
            if (!songService.existById(song)) {
                throw new SongNotAvailableException(
                        "Some Requested Songs Not Available. Please try again");
            }
        }
    }


    public PlayList deleteSongsFromPlayList(PlayList playList, List<String> songs) {
        isSongsExistInPlayList(playList, songs);
        songs.stream().forEach(song -> {
            playList.getSongs().remove(song);
        });
        return playList;
    }

    public void isSongsExistInPlayList(PlayList playList, List<String> songs) {
        Set<String> checkSet = new HashSet<>(playList.getSongs());
        songs.stream().forEach(song -> {
            if (!checkSet.contains(song)) {
                throw new SongNotAvailableException(
                        "Some Requested Songs for Deletion are not present in the playlist. Please try again.");
            }
        });
    }


    @Override
    public Song playPlayList(String userId, String playListId) throws PlayListEmptyException {

        this.runningPlayListId = playListId;
        Optional<PlayList> optional = playListRepository.findById(playListId);
        if (optional.isEmpty()) {
            throw new PlayListNotFoundException("Playlist not found");
        }
        PlayList playList = optional.get();
        if (!playList.getUserId().equals(userId)) {
            throw new InvalidUserException("Not a valid user to perform operation");
        }
        if (playList.getSongs().size() == 0) {
            throw new PlayListEmptyException("Playlist is empty");
        }
        this.runningSongIndex = 0;
        return playSongFromRunningPlayList(playList);
    }


    @Override
    public Song playPlayListSong(String userId, String operation) throws SongNotAvailableException {

        Optional<PlayList> optional = playListRepository.findById(this.runningPlayListId);
        if (optional.isEmpty()) {
            throw new PlayListNotFoundException("Playlist not found");
        }
        PlayList playList = optional.get();
        if (!playList.getUserId().equals(userId)) {
            throw new InvalidUserException("Not a valid user to perform operation");
        }
        if (playList.getSongs().size() == 0) {
            throw new PlayListEmptyException("Playlist is empty");
        }
        if (operation.equalsIgnoreCase("BACK")) {
            this.runningSongIndex--;
            if (this.runningSongIndex < 0) {
                this.runningSongIndex = playList.getSongs().size() - 1;
            }
        } else if (operation.equalsIgnoreCase("NEXT")) {
            this.runningSongIndex++;
            if (this.runningSongIndex > playList.getSongs().size() - 1) {
                this.runningSongIndex = 0;
            }
        } else {
            int index = -1;
            for (int i = 0; i < playList.getSongs().size(); i++) {
                if (operation.equals(playList.getSongs().get(i))) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                throw new SongNotAvailableException(
                        "Given song id is not a part of the active playlist");
            }
            this.runningSongIndex = index;
        }

        return playSongFromRunningPlayList(playList);
    }

    private Song playSongFromRunningPlayList(PlayList playList) {
        
        Optional<Song> song =
                songService.getSongById(playList.getSongs().get(this.runningSongIndex));
        return song.get();
    }

    public void setRunningPlayListId(String id) {
        this.runningPlayListId = id;
    }

    public void setRunningSongIndex(Integer id) {
        this.runningSongIndex = id;
    }

    
    

}
