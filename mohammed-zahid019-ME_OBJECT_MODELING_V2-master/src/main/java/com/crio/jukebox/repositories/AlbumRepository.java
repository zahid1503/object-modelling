package com.crio.jukebox.repositories;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.crio.jukebox.entities.Album;


public class AlbumRepository implements CRUDRepository<Album, String> {

    private Integer entityId;

    private final Map<String, Album> albumMap;

    public AlbumRepository() {
        albumMap = new HashMap<>();
        entityId = albumMap.size();
    }


    @Override
    public Album save(Album entity) {
        if (entity.getId() == null) {
            entityId++;
            Album album =
                    new Album(Integer.toString(entityId), entity.getName(), entity.getArtist());
            albumMap.put(Integer.toString(entityId), album);
            return album;
        }
        albumMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<Album> findAll() {
        return new ArrayList<>(albumMap.values());
    }

    @Override
    public Optional<Album> findById(String id) {
        return Optional.of(albumMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return albumMap.containsKey(id);
    }

    @Override
    public void delete(Album entity) {
        albumMap.remove(entity.getId());
    }


    @Override
    public void deleteById(String id) {
        albumMap.remove(id);
    }

    @Override
    public long count() {
        return albumMap.size();
    }

}

