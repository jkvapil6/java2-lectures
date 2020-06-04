package cz.upol.zp4jv.xml;


import java.util.List;

/**
 * Trida reprezentujici Playlist
 *
 * @author Jan Kvapil
 * @version 1.0
 * @since 2019-02-17
 */
public class Playlist {

    private int numberOfTracks;
    private String name;
    private String genre;
    private List<Track> tracks;

    public Playlist() {
        numberOfTracks = 0;
        name = "none";
        genre = "none";
        tracks = null;
    }

    public Playlist(int numberOfTracks, String name, String genre, List tracks) {
        this.numberOfTracks = numberOfTracks;
        this.name = name;
        this.genre = genre;
        this.tracks = tracks;
    }

    public void setNumberOfTracks(int numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
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

    public int getNumberOfTracks(){
        return numberOfTracks;
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

    public void print() {
        System.out.println("name: \n\t" + this.getName());
        System.out.println("number of tracks: \n\t" + this.getNumberOfTracks());
        System.out.println("genre: \n\t " + this.getGenre());
        System.out.println("tracks:");
        for (Track track : tracks) {
            System.out.println("\t" + track.getName());
            System.out.println("\t" + track.getLocation());
            System.out.println("\t" + track.getDuration() + "\n");
        }
    }
}
