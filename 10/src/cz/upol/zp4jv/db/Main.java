package cz.upol.zp4jv.db;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, PlaylistDBException, java.sql.SQLException {

        PlaylistManager playlistManager = new PlaylistManager(Connector.getConnection("jdbc:mysql://localhost:3306/zp4jv?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"));

        String name = "Red Dwarf: Season 1";

        // vlozi konkretni track do DB
        // Track t = new Track("s01e02.avi", "27:40", "Future Echoes" );
        // playlistManager.insertTrack(t, 666);

        ///////////// TEST 1 - INSERT /////////////

        // nazev je unikatni.. nelze vkladat vice stejnych playlistu

        /* */
        Track t1 = new Track("s01e01.avi", "27:40", "Random ep1" );
        Track t2 = new Track("s01e02.avi", "21:40", "Random ep2" );
        Track t3 = new Track("s01e03.avi", "26:40", "Random ep3" );

        LinkedList<Track> tracks = new LinkedList<>();
        tracks.add(t1); tracks.add(t2); tracks.add(t3);

        // vlozi playlist do DB
        Playlist p = new Playlist(name, "comedy", tracks);
        playlistManager.insertPlaylist(p);


        ///////////// TEST 2 - SELECT /////////////

        /*
        // vrati playlist z DB dle nazvu
        System.out.println(playlistManager.getByName(name).toString());

        // vypise tracklist dle jmena playlistu
        playlistManager.printTracklist(name);
        */

        ///////// TEST 3 - UPDATE, DELETE /////////

        /*
        // vrati id playlistu dle jmena playlistu
        int id = playlistManager.getPlaylistIdByName(name);

        // smaze vsechny tracky konkretniho playlistu
        System.out.println("mazu tracklist playlistu id: " + id);
        playlistManager.deleteTracksByPlaylistId(id);

        // updatuje nazev
        playlistManager.updatePlaylist(name, "Game of Thrones", "fantasy");

        */


        playlistManager.disconnect();
    }
}


