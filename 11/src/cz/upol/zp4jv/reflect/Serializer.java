package cz.upol.zp4jv.reflect;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


/***  Třída Serializer mající metodu void serialize(Object obj), která využije anotací a vygeneruje XML soubor např. ve tvaru:
   *      <emp>
   *        <name>Tomas Marny</name>
   *        <income>58000</income>
   *        <age>42</age>
   *      </emp>
 */

public class Serializer {

    public static void serialize(Object obj, String filename) throws Exception {
        for (Annotation a : obj.getClass().getAnnotations()) {
            if (a instanceof Serialize) {

                StringWriter buf = new StringWriter();
                OutputStream output = new FileOutputStream(filename);

                XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
                XMLStreamWriter xmlWriter = xmlOutputFactory.createXMLStreamWriter(buf);

                // XML metadata
                xmlWriter.writeStartDocument();

                // koren XML
                xmlWriter.writeStartElement(((Serialize) a).as());

                // Ziska metody tridy, ke kterym zjisti anotace, vytvori XML element, vyvola
                // metodu a navratovou hodnotu prevede do Stringu, ktery bude telem elementu
                for (Method m : obj.getClass().getDeclaredMethods()) {
                    for (Annotation am : m.getDeclaredAnnotations()) {
                        if (am instanceof Serialize) {
                            xmlWriter.writeStartElement(((Serialize) am).as());
                            xmlWriter.writeCharacters(m.invoke(obj).toString());
                            xmlWriter.writeEndElement();
                        }
                    }
                }
                xmlWriter.writeEndElement();
                xmlWriter.writeEndDocument();

                output.write(buf.toString().getBytes());
                System.out.println("End of serialization to file: " + filename);
            }
        }
    }
}
