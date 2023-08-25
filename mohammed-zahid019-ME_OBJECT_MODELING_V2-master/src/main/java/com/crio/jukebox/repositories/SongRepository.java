package com.crio.jukebox.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.crio.jukebox.entities.Song;

public class SongRepository implements CRUDRepository<Song, String> {

    private Integer entityId;

    private final Map<String, Song> songMap;

    public SongRepository() {
        songMap = new HashMap<>();
        entityId = songMap.size();
    }

    @Override
    public Song save(Song entity) {
        if (entity.getId() == null) {
            entityId++;
            Song song = new Song(Integer.toString(entityId), entity.getName(), entity.getGenre(),
                    entity.getAlbumName(), entity.getFeaturedArtist());
            songMap.put(Integer.toString(entityId), song);
            return song;
        }
        songMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<Song> findAll() {
        return new ArrayList<>(songMap.values());
    }

    @Override
    public Optional<Song> findById(String id) {
        return Optional.of(songMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return songMap.containsKey(id);
    }

    @Override
    public void delete(Song entity) {
        songMap.remove(entity.getId());
    }

    @Override
    public void deleteById(String id) {
        songMap.remove(id);
    }

    @Override
    public long count() {
        return songMap.size();
    }
}





