package com.jaureguialzo.dom;

import com.jaureguialzo.euskalmet.Euskalmet;
import com.jaureguialzo.euskalmet.Tendencia;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Ejemplo de lectura de datos XML usando un parser tipo DOM
 */
public class ParserDOM {

    // REF: Tutorial: https://www.journaldev.com/898/read-xml-file-java-dom-parser

    public static void main(String[] args) {

        System.out.println("--- DOM (lectura) ---\n");

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            InputSource datos = new InputSource(new StringReader(Euskalmet.tendenciaSeisDias()));

            Document doc = dBuilder.parse(datos);
            doc.getDocumentElement().normalize();

            System.out.println("Elemento raíz: " + doc.getDocumentElement().getNodeName());

            NodeList nodos = doc.getElementsByTagName("tendecyDay");

            List<Tendencia> tendencias = new ArrayList<>();
            for (int i = 0; i < nodos.getLength(); i++) {
                tendencias.add(getTendencia(nodos.item(i)));
            }

            for (Tendencia t : tendencias)
                System.out.println(t);

        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }

    }

    private static Tendencia getTendencia(Node nodo) {

        Tendencia temp = new Tendencia();

        if (nodo.getNodeType() == Node.ELEMENT_NODE) {

            Element element = (Element) nodo;

            temp.setFecha(element.getAttribute("date"));

            temp.setTemperatura(obtenerValor("es", obtenerSubelemento("tempIcon", element)));
            temp.setTiempo(obtenerValorXPath("weatherIcon/descriptions/es", element));
            temp.setViento(obtenerValor("es", obtenerSubelemento("windIcon", element)));

        }

        return temp;
    }

    /**
     * Obtener el valor (#PCDATA) de un elemento del árbol XML
     *
     * @param tag     La etiqueta del elemento
     * @param element Nodo de partida
     * @return Texto recuperado
     */
    private static String obtenerValor(String tag, Element element) {
        NodeList nodos = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node nodo = nodos.item(0);
        return nodo.getNodeValue();
    }

    /**
     * Obtener un subelemento anidado a partir de otro dado
     *
     * @param tag     La etiqueta del elemento
     * @param element Nodo de partida
     * @return Subelemento recuperado
     */
    private static Element obtenerSubelemento(String tag, Element element) {
        return (Element) element.getElementsByTagName(tag).item(0);
    }

    /**
     * Obtener el valor (#PCDATA) de un elemento del árbol XML usando una expresión XPath
     *
     * @param path    La ruta relativa del elemento
     * @param element Nodo de partida
     * @return Texto recuperado
     */
    private static String obtenerValorXPath(String path, Element element) {

        // REF: Obtener un valor XPath: https://stackoverflow.com/a/6539024

        XPath xPath = XPathFactory.newInstance().newXPath();

        String valor = null;

        try {
            NodeList nodes = (NodeList) xPath.evaluate(path, element, XPathConstants.NODESET);
            if (nodes.getLength() > 0) {
                Node node = nodes.item(0).getChildNodes().item(0);
                valor = node.getNodeValue();
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return valor;
    }

}
