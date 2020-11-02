package com.jaureguialzo.stax;

import com.jaureguialzo.euskalmet.Euskalmet;
import com.jaureguialzo.euskalmet.Tendencia;
import com.jaureguialzo.euskalmet.TipoTendencia;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Ejemplo de lectura de datos XML usando un parser tipo StAX
 */
public class ParserStAX {

    // REF: Tutorial: https://www.tutorialspoint.com/java_xml/java_stax_parse_document.htm

    public static void main(String[] args) {

        System.out.println("--- StAX (lectura) ---\n");

        List<Tendencia> tendencias = new ArrayList<>();

        Tendencia temp = null;
        TipoTendencia tipo = null;
        StringBuilder builder = new StringBuilder();

        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();

            XMLEventReader eventReader = factory.createXMLEventReader(new StringReader(Euskalmet.tendenciaSeisDias()));

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                switch (event.getEventType()) {

                    case XMLStreamConstants.START_ELEMENT:

                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();

                        switch (qName) {
                            case "tendecyDay":
                                temp = new Tendencia();

                                Attribute fecha = startElement.getAttributeByName(QName.valueOf("date"));
                                temp.setFecha(fecha.getValue());
                                break;
                            case "windIcon":
                                tipo = TipoTendencia.VIENTO;
                                break;
                            case "weatherIcon":
                                tipo = TipoTendencia.TIEMPO;
                                break;
                            case "tempIcon":
                                tipo = TipoTendencia.TEMPERATURA;
                                break;
                        }

                        builder.setLength(0);

                        break;
                    case XMLStreamConstants.CHARACTERS:

                        Characters characters = event.asCharacters();
                        builder.append(characters.getData());

                        break;
                    case XMLStreamConstants.END_ELEMENT:

                        EndElement endElement = event.asEndElement();
                        qName = endElement.getName().getLocalPart();

                        String texto = builder.toString();

                        if (qName.equals("tendecyDay")) {
                            tendencias.add(temp);
                        } else if (qName.equals("es") && tipo != null && temp != null) {
                            switch (tipo) {
                                case VIENTO:
                                    temp.setViento(texto);
                                    break;
                                case TIEMPO:
                                    temp.setTiempo(texto);
                                    break;
                                case TEMPERATURA:
                                    temp.setTemperatura(texto);
                                    break;
                            }
                        }

                        break;
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        for (Tendencia t : tendencias)
            System.out.println(t);
    }
}
