package cz.upol.zp4jv.xml;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Trida implementuje rozhrani pro nacitani a ukladani XML dokumentu playlist pomoci StAX parseru
 *
 * @author Jan Kvapil
 * @version 1.0
 * @since 2019-02-17
 */
public class StAXPlaylistReaderWriter implements PlaylistReaderWriter {

    @Override
    public Playlist loadPlaylist(InputStream input) throws Exception {

        Playlist playlist = new Playlist();
        List<Track> tracklist = new LinkedList();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(input);

        QName currentElement = new QName("none");
        Track track = new Track();

        while (reader.hasNext()) {

            switch (reader.next()) {
                case XMLStreamReader.START_ELEMENT:
                    currentElement = reader.getName();

                    switch (currentElement.toString()) {
                        case "playlist":
                            playlist.setNumberOfTracks(Integer.parseInt(reader.getAttributeValue(0)));
                            break;

                        case "track":
                            track = new Track();
                            track.setLocation(reader.getAttributeValue(0));
                            track.setDuration(reader.getAttributeValue(1));
                            break;
                    }
                    break;

                case XMLStreamReader.CHARACTERS:

                    switch (currentElement.toString()) {
                        case "name":
                            playlist.setName(reader.getText());
                            break;
                        case "genre":
                            playlist.setGenre(reader.getText());
                            break;
                        case "track":
                            track.setName(reader.getText());
                            tracklist.add(track);
                            break;
                    }
                    break;

                default:
                    break;
            }
        }

        playlist.setTracks(tracklist);
        return playlist;
    }



    @Override
    public void storePlaylist(OutputStream output, Playlist playlist) throws Exception {

        StringWriter buf = new StringWriter();

        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter xmlWriter = xmlOutputFactory.createXMLStreamWriter(buf);

        xmlWriter.writeStartDocument();

        xmlWriter.writeStartElement("playlist");
        xmlWriter.writeAttribute("tracks", String.valueOf(playlist.getNumberOfTracks()));

        xmlWriter.writeStartElement("name");
        xmlWriter.writeCharacters(playlist.getName());
        xmlWriter.writeEndElement();

        xmlWriter.writeStartElement("genre");
        xmlWriter.writeCharacters(playlist.getGenre());
        xmlWriter.writeEndElement();

        xmlWriter.writeStartElement("trackList");
        for (Track track : playlist.getTracks()) {
            xmlWriter.writeStartElement("track");
            xmlWriter.writeAttribute("location", track.getLocation());
            xmlWriter.writeAttribute("duration", track.getDuration());
            xmlWriter.writeCharacters(track.getName());
            xmlWriter.writeEndElement();
        }
        xmlWriter.writeEndElement();
        xmlWriter.writeEndElement();

        xmlWriter.writeEndDocument();
        output.write(buf.toString().getBytes());
    }
}
