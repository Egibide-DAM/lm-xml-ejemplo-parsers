package com.jaureguialzo.sax;

import com.jaureguialzo.euskalmet.Tendencia;
import com.jaureguialzo.euskalmet.TipoTendencia;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Manejador de los eventos del parser SAX
 */
public class GestorEventos extends DefaultHandler {

    private List<Tendencia> tendencias = new ArrayList<>();

    private Tendencia temp = null;

    private TipoTendencia tipo = null;

    private StringBuilder builder = new StringBuilder();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        switch (qName) {
            case "tendecyDay":
                temp = new Tendencia();
                temp.setFecha(attributes.getValue("date"));
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
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        builder.append(new String(ch, start, length));
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        String texto = builder.toString();

        if (qName.equals("tendecyDay")) {
            tendencias.add(temp);
        } else if (qName.equals("es")) {
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

    }

    public List<Tendencia> getTendencias() {
        return tendencias;
    }

}
