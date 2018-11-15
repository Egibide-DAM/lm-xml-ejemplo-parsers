package com.jaureguialzo.sax;

import com.jaureguialzo.euskalmet.Euskalmet;
import com.jaureguialzo.euskalmet.Tendencia;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class ParserSAX {

    // REF: Tutorial: https://www.journaldev.com/1198/java-sax-parser-example

    public static void main(String[] args) {

        System.out.println("--- SAX (lectura) ---\n");

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();

            GestorEventos handler = new GestorEventos();
            InputSource datos = new InputSource(new StringReader(Euskalmet.tendenciaSeisDias()));

            saxParser.parse(datos, handler);

            List<Tendencia> tendencias = handler.getTendencias();

            for (Tendencia t : tendencias)
                System.out.println(t);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }
}
