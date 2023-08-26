package com.jukebox.entities;

public class Song {
    private String songId;
    private String songName;
    private String genre;
    private String album;
    private String artist;
    private String featuredArtists;

    public Song(Song song){
        this(song.songId, song.songName, song.genre, song.album, song.artist, song.featuredArtists);
    }
    public Song(String songId, String songName, String genre, String album , String artist , String featuredArtists) {
        this(songName, genre, album , artist, featuredArtists);
        this.songId = songId;
    }
    public Song(String songName , String genre , String album , String artist , String featuredArtists){
        this.songName = songName;
        this.genre = genre;
        this.album = album;
        this.artist = artist;
        this.featuredArtists = featuredArtists;
    }
    public String getSongId() {
        return this.songId;
    }
    public String getSongName() {
        return this.songName;
    }
    public String getGenre() {
        return this.genre;
    }
    public String getAlbum() {
        return this.album;
    }
    public String getArtist() {
        return this.artist;
    }
    public String getFeaturedArtists() {
        return this.featuredArtists;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((songId == null) ? 0 : songId.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Song other = (Song) obj;
        if (songId == null) {
            if (other.songId != null)
                return false;
        } else if (!songId.equals(other.songId))
            return false;
        return true;
    }
}

