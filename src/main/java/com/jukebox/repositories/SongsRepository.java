package com.jukebox.repositories;

import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import com.jukebox.entities.Song;

public class SongsRepository implements ISongsRepository {

    private final Map<String,Song> songMap;
    private Integer autoIncrement = 0;

    public SongsRepository() {
        songMap = new HashMap<String,Song>();
    }

    public SongsRepository(Map<String , Song> songMap){
        this.songMap = songMap;
        this.autoIncrement = songMap.size();
    }

    @Override
    public Song save(Song entity) {
        if(entity.getSongId() == null){
            autoIncrement = songMap.size() + 1;
            Song song = new Song(Integer.toString(autoIncrement), entity.getSongName(), entity.getGenre(),
                    entity.getAlbum(),  entity.getArtist(), entity.getFeaturedArtists());
            songMap.put(song.getSongId() , song);
            return song;
        }
        songMap.put(entity.getSongId() , entity);
        return entity;
    }

    @Override
    public List<Song> findAll() {
        return songMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<Song> findById(String id) {
        return Optional.ofNullable(songMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return songMap.containsKey(id);
    }

    @Override
    public void delete(Song entity) {
        //Leaving for now
    }

    @Override
    public void deleteById(String id) {
        //Leaving for now
    }

}
