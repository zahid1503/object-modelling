package com.crio.jukebox.services;

import java.io.BufferedReader;
//public class SongService implements ISongService {import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.repositories.SongRepository;


public class SongService implements ISongService {
    
    private SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public boolean existById(String id) {
        return songRepository.existsById(id);
    }

    public Optional<Song> getSongById(String id) {
        return songRepository.findById(id);
    }

    @Override
    public void loadData(String fileName) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();
        while (line != null) {
            List<String> data = Arrays.asList(line.split(","));
            String featuredArtist = data.get(5).replace("#", ",");
            Song song =
                    new Song(data.get(0), data.get(1), data.get(2), data.get(3), featuredArtist);
            songRepository.save(song);
            // read next line
            line = reader.readLine();
        }
        reader.close();
    }

}
    

