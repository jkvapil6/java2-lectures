package cz.upol.zp4jv.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Trida implementuje rozhrani pro nacitani a ukladani XML dokumentu playlist pomoci DOM parseru
 *
 * @author Jan Kvapil
 * @version 1.0
 * @since 2019-02-18
 */
public class DOMPlaylistReaderWriter implements PlaylistReaderWriter {

    @Override
    public Playlist loadPlaylist(InputStream input) throws Exception {

        Playlist playlist = new Playlist();
        List<Track> tracklist = new LinkedList();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document doc = documentBuilder.parse(input);
        Element root = doc.getDocumentElement();

        if (root.hasAttributes()) {
            playlist.setNumberOfTracks(Integer.parseInt((root.getAttribute("tracks"))));
        }

        for (int i = 0; i < root.getChildNodes().getLength(); i++) {
            Node node = root.getChildNodes().item(i);

            switch (node.getNodeName()) {
                case "name":
                    playlist.setName(node.getTextContent());
                    break;
                case "genre":
                    playlist.setGenre(node.getTextContent());
                    break;
                case "trackList":
                    for (int j = 0; j < node.getChildNodes().getLength(); j++) {
                        Node trackNode = node.getChildNodes().item(j);
                        tracklist.add(new Track(
                                trackNode.getAttributes().getNamedItem("location").getTextContent(),
                                trackNode.getAttributes().getNamedItem("duration").getTextContent(),
                                trackNode.getTextContent())
                        );
                    }
                    break;
            }
        }

        playlist.setTracks(tracklist);
        return playlist;
    }

    @Override
    public void storePlaylist(OutputStream output, Playlist playlist) throws Exception {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document doc = documentBuilder.newDocument();

        Element rootElement = doc.createElement("playlist");
        rootElement.setAttribute("tracks", String.valueOf(playlist.getNumberOfTracks()));
        doc.appendChild(rootElement);

        Element childNode1 = doc.createElement("name");
        childNode1.appendChild(doc.createTextNode(playlist.getName()));
        rootElement.appendChild(childNode1);

        Element childNode2 = doc.createElement("genre");
        childNode2.appendChild(doc.createTextNode(playlist.getGenre()));
        rootElement.appendChild(childNode2);

        Element childNode3 = doc.createElement("trackList");
        for (Track track : playlist.getTracks()) {
            Element trackNode = doc.createElement("track");
            trackNode.setAttribute("location", track.getLocation());
            trackNode.setAttribute("duration", track.getDuration());
            trackNode.appendChild(doc.createTextNode(track.getName()));
            childNode3.appendChild(trackNode);
        }
        rootElement.appendChild(childNode3);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);
        transformer.transform(source, result);
    }
}
