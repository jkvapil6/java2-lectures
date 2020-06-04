package cz.upol.zp4jv.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Trida implementuje rozhrani pro nacitani XML dokumentu playlist pomoci SAX parseru
 *
 * @author Jan Kvapil
 * @version 1.0
 * @since 2019-02-17
 */
public class SAXPlaylistReaderWriter implements PlaylistReaderWriter {

    @Override
    public Playlist loadPlaylist(InputStream input) throws Exception {

        Playlist playlist = new Playlist();
        List<Track> tracklist = new LinkedList();
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();

        parser.parse(input, new DefaultHandler() {

            private String currentElement = "none";
            private Track track;

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes)  {

                switch (qName) {
                    case "playlist":
                        playlist.setNumberOfTracks(Integer.parseInt(attributes.getValue("tracks")));
                        break;
                    case "track":
                        track = new Track();
                        track.setLocation(attributes.getValue("location"));
                        track.setDuration(attributes.getValue("duration"));
                        break;
                }
                currentElement = qName;
            }

            @Override
            public void characters(char[] ch, int start, int length) {

                switch (currentElement) {
                    case "name":
                        playlist.setName(new String(ch, start, length));
                        break;
                    case "genre":
                        playlist.setGenre(new String(ch, start, length));
                        break;
                    case "track":
                        track.setName(new String(ch, start, length));
                        tracklist.add(track);
                        break;
                }
            }
        });

        playlist.setTracks(tracklist);
        return playlist;
    }

    @Override
    public void storePlaylist(OutputStream output, Playlist playlist) throws Exception {
        throw new UnsupportedOperationException();
    }
}
