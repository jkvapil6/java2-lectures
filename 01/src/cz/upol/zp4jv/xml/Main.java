package cz.upol.zp4jv.xml;


import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Main {

    public static void main(String[] args) throws Exception {

        InputStream input = new ByteArrayInputStream("<?xml version=\"1.0\" encoding=\"UTF-8\"?><playlist tracks=\"6\"><name>Red Dwarf: Season 1</name><genre>comedy</genre><trackList><track location=\"s01e01.avi\" duration=\"28:58\">The End</track><track location=\"s01e02.avi\" duration=\"27:40\">Future Echoes</track><track location=\"s01e03.avi\" duration=\"26:30\">Balance of Power</track><track location=\"red04.mpg\"  duration=\"25:40\">Waiting for God</track><track location=\"red05.mpeg\" duration=\"24:15\">Confidence and Paranoia</track><track location=\"red06.avi\"  duration=\"26:50\">Me^2</track></trackList></playlist>".getBytes());

        Playlist playlist;
        OutputStream output = new FileOutputStream("out.xml");


        // SAX - read
        /*
        SAXPlaylistReaderWriter sax = new SAXPlaylistReaderWriter();
        playlist = sax.loadPlaylist(input);
        playlist.print();
        //sax.storePlaylist(output, playlist); // musi vyhazovat vyjimku
        */

        // StAX - read, write
        /*
        StAXPlaylistReaderWriter stax = new StAXPlaylistReaderWriter();
        playlist = stax.loadPlaylist(input);
        playlist.print();
        stax.storePlaylist(output, playlist);
        */

        // DOM - read, write
        /*  */
        DOMPlaylistReaderWriter dom = new DOMPlaylistReaderWriter();
        playlist = dom.loadPlaylist(input);
        playlist.print();
        dom.storePlaylist(output, playlist);



    }
}
