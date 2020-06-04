package cz.upol.zp4jv.db;

import java.sql.*;
import java.util.LinkedList;

public class PlaylistManager implements AutoCloseable {

    private Connection con;


    private PreparedStatement getByNameStmt;
    private PreparedStatement insertStmt;
    private PreparedStatement getPlaylistIdByNameStmt;
    private PreparedStatement getTracklistByIdStmt;
    private PreparedStatement insertTrackStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteTracksByPlaylistIdStmt;

    public PlaylistManager(Connection con) {
        this.con = con;
        try {
            getByNameStmt                   = con.prepareStatement("SELECT * FROM playlist WHERE name = ?");
            getPlaylistIdByNameStmt         = con.prepareStatement("SELECT id FROM playlist WHERE name = ?");
            getTracklistByIdStmt            = con.prepareStatement("SELECT * FROM track WHERE playlist_id = ?");
            insertStmt                      = con.prepareStatement("INSERT INTO playlist (id, name, genre) VALUES (null, ?, ?)");
            insertTrackStmt                 = con.prepareStatement("INSERT INTO track (id, playlist_id, name, location, duration) VALUES (null, ?, ?, ?, ?)");
            updateStmt                      = con.prepareStatement("UPDATE playlist SET name = ?, genre = ? WHERE name = ?");
            deleteTracksByPlaylistIdStmt    = con.prepareStatement("DELETE FROM track WHERE playlist_id = ?");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     *  vrati playlist dle jmena
     */
    public Playlist getByName(String name) throws PlaylistDBException {
        Playlist playlist = null;
        try {
            getByNameStmt.setString(1, name);
            try (ResultSet results = getByNameStmt.executeQuery()) {
                if (results.next())
                    playlist = new Playlist(results.getString("name"),
                            results.getString("genre"),
                            null);
            }
        } catch (SQLException e) {
            throw new PlaylistDBException("Unable to find an playlist", e);
        }
        return playlist;
    }

    /**
     *  vrati tracklist dle jmena playlistu
     */
    public LinkedList<Track> getTracklistByPlaylistName(String name) throws PlaylistDBException {
        LinkedList<Track> tracklist = new LinkedList<>();
        int id = getPlaylistIdByName(name);
        try {
            getTracklistByIdStmt.setInt(1, id);
            try (ResultSet results = getTracklistByIdStmt.executeQuery()) {
                while (results.next())
                    tracklist.add( new Track(results.getString("duration"),
                            results.getString("location"),
                            results.getString("name")));
            }
        } catch (SQLException e) {
            throw new PlaylistDBException("Unable to find an tracklist", e);
        }
        return tracklist;
    }

    /**
     *  vypise trackslist dle jmena playlistu
     */
    public void printTracklist(String name) throws PlaylistDBException {
        LinkedList<Track> tracklist = getTracklistByPlaylistName(name);
        System.out.println("Tracks of playlist " + name + ":");
        for (Track t : tracklist) {
            System.out.println(t.getName());
        }
    }

    /**
     *  vrati id playlistu dle jmena
     */
    public int getPlaylistIdByName(String name) throws PlaylistDBException {
        try {
            getPlaylistIdByNameStmt.setString(1, name);
            try (ResultSet results =  getPlaylistIdByNameStmt.executeQuery()) {
                if (results.next())
                    return results.getInt("id");
            }
        } catch (SQLException e) {
            throw new PlaylistDBException("Unable to find an playlist", e);
        }
        return -1;
    }

    /**
     *  updatuje nazev a zanr playlistu dle puvodniho jmeno
     */
    public void updatePlaylist(String oldName, String newName, String newGenre) throws PlaylistDBException {
        try {
            updateStmt.setString(1, newName);
            updateStmt.setString(2, newGenre);
            updateStmt.setString(3, oldName);
            updateStmt.executeUpdate();
        } catch (SQLException e) {
            throw new PlaylistDBException("Unable to update an playlist", e);
        }
    }

    /**
     *  smaze vsechny tracky playlistu dle id
     */
    public void deleteTracksByPlaylistId(int id) throws PlaylistDBException {
        try {
            deleteTracksByPlaylistIdStmt.setInt(1, id);
            deleteTracksByPlaylistIdStmt.executeUpdate();
        } catch (SQLException e) {
            throw new PlaylistDBException("Unable to delete tracklist", e);
        }
    }

    /**
     *  vlozi track do DB
     */
    public void insertTrack(Track track, int playlist_id) throws PlaylistDBException {
        try {
            insertTrackStmt.setInt(1, playlist_id);
            insertTrackStmt.setString(2, track.getName());
            insertTrackStmt.setString(3, track.getLocation());
            insertTrackStmt.setString(4, track.getDuration());
            insertTrackStmt.executeUpdate();
        } catch (SQLException e) {
            throw new PlaylistDBException("Unable to insert track", e);
        }
    }

    /**
     *  vlozi playlist do DB
     */
    public void insertPlaylist(Playlist p)  throws PlaylistDBException {
            try {
                insertStmt.setString(1, p.getName());
                insertStmt.setString(2, p.getGenre());
                insertStmt.executeUpdate();
            } catch (SQLException e) {
                throw new PlaylistDBException("Unable to create new playlist", e);
            }

            int id = getPlaylistIdByName(p.getName());
            for (Track t : p.getTracks()) {
                insertTrack(t, id);
            }
        }



    public void disconnect() throws SQLException {
        con.close();
    }

    @Override
    public void close() {
        try {
            getByNameStmt.close();
            insertStmt.close();
            getPlaylistIdByNameStmt.close();
            getTracklistByIdStmt.close();
            insertTrackStmt.close();
            updateStmt.close();
            deleteTracksByPlaylistIdStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
