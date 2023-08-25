package com.crio.jukebox.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.crio.jukebox.entities.PlayList;

/**
 * PlayListRepository to store playlists in map
 */
public class PlayListRepository implements CRUDRepository<PlayList, String> {

    private Integer entityId;

    
    private final Map<String, PlayList> playListMap;

    public PlayListRepository() {
        playListMap = new HashMap<>();
        entityId = playListMap.size();
    }

    @Override
    public PlayList save(PlayList entity) {
        if (entity.getId() == null) {
            entityId++;
            PlayList playList = new PlayList(Integer.toString(entityId), entity.getUserId(),
                    entity.getName(), entity.getSongs());
            playListMap.put(Integer.toString(entityId), playList);
            return playList;
        }
        playListMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<PlayList> findAll() {
        return new ArrayList<>(playListMap.values());
    }

    @Override
    public Optional<PlayList> findById(String id) {
        return Optional.of(playListMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return playListMap.containsKey(id);
    }

    @Override
    public void delete(PlayList entity) {
        playListMap.remove(entity.getId());
    }

    @Override
    public void deleteById(String id) {
        playListMap.remove(id);
    }

    @Override
    public long count() {
        return playListMap.size();
    }

}


