package cz.upol.zp4jv.swing2;

import java.util.List;

/**
 * Trida reprezentujici Playlist
 *
 * @author Jan Kvapil
 * @version 1.0
 * @since 2019-02-17
 */
public class Playlist {

    private String name;
    private String genre;
    private List<Track> tracks;

    public Playlist() {
        name = "none";
        genre = "none";
        tracks = null;
    }

    public Playlist(String name, String genre, List tracks) {
        this.name = name;
        this.genre = genre;
        this.tracks = tracks;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public List<Track> getTracks() {
        return tracks;
    }

}
