package cz.upol.zp4jv.reflect;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/***
 *  Třída Deserializer mající metodu Object deserialize(), která z XML souboru vytvoří daný objekt.
 */

public class Deserializer {

    public static Object deserialize(Class<?> clazz, InputStream input) throws Exception {

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(input);

        QName currentElement = null;

        // vytvori novou instanci prislusne tridy
        Object obj = clazz.getDeclaredConstructor().newInstance();

        while (reader.hasNext()) {
            switch (reader.next()) {
                case XMLStreamReader.START_ELEMENT:
                    currentElement = reader.getName();
                    break;

                case XMLStreamReader.CHARACTERS:
                    if (currentElement != null) {
                        tryInvoke(obj, currentElement.toString(), reader.getText());
                    }
                    break;

                default:
                    break;
            }
        }
        return obj;
    }


    /**
     * Metoda projde objekt a pokud najde anotaci metody shodnou s prectenym elementem, tak ji vyvola, argumentem bude text elementu
     */
    private static void tryInvoke(Object obj, String elem, String text) throws InvocationTargetException, IllegalAccessException {

        for (Method m : obj.getClass().getDeclaredMethods()) {
            for (Annotation a : m.getDeclaredAnnotations()) {
                if (a instanceof Deserialize && ((Deserialize) a).as().equals(elem)) {

                    // ziska tridu vstupniho parametru
                    Class<?> clazz = m.getParameterTypes()[0];

                    // proc nejde pouzit switch pro porovnani trid? "switch (clazz) { case int.class: .. "
                    if (clazz == int.class) {
                        try {
                            m.invoke(obj, Integer.parseInt(text));
                        } catch (NumberFormatException ex){
                            System.out.println(ex);
                        }
                    }

                    if (clazz == double.class) {
                        try {
                            m.invoke(obj, Double.parseDouble(text));
                        } catch (NumberFormatException ex){
                            System.out.println(ex);
                        }
                    }

                    if (clazz == String.class) {
                       m.invoke(obj, text);
                    }
                }
            }
        }
    }
}
