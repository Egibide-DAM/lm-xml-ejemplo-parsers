package com.jaureguialzo.dom;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.jaureguialzo.euskalmet.Euskalmet;
import com.jaureguialzo.euskalmet.Tendencia;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ParserDOM {

    // REF: Tutorial: https://www.journaldev.com/898/read-xml-file-java-dom-parser

    public static void main(String[] args) {

        System.out.println("--- DOM (lectura) ---\n");

        String filePath = "employee.xml";
        File xmlFile = new File(filePath);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();

            InputSource datos = new InputSource(new StringReader(Euskalmet.tendenciaSeisDias()));

            Document doc = dBuilder.parse(datos);
            doc.getDocumentElement().normalize();

            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList nodeList = doc.getElementsByTagName("tendecyDay");

            //now XML is loaded as Document in memory, lets convert it to Object List
            List<Tendencia> tendencias = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                tendencias.add(getTendencia(nodeList.item(i)));
            }

            //lets print Employee list information
            for (Tendencia t : tendencias)
                System.out.println(t);

        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }

    }

    private static Tendencia getTendencia(Node node) {

        Tendencia temp = new Tendencia();

        if (node.getNodeType() == Node.ELEMENT_NODE) {

            Element element = (Element) node;

            temp.setFecha(element.getAttribute("date"));

            temp.setTemperatura(obtenerValor("es", obtenerSubelemento("tempIcon", element)));
            temp.setTiempo(obtenerValor("es", obtenerSubelemento("weatherIcon", element)));
            temp.setViento(obtenerValorXPath("windIcon", element));

        }

        return temp;
    }

    private static String obtenerValor(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

    private static Element obtenerSubelemento(String tag, Element element) {
        return (Element) element.getElementsByTagName(tag).item(0);
    }

    private static String obtenerValorXPath(String tag, Element element) {

        // REF: Obtener un valor XPath: https://stackoverflow.com/a/6539024

        XPath xPath = XPathFactory.newInstance().newXPath();

        NodeList nodes = null;
        try {
            nodes = (NodeList) xPath.evaluate(tag + "/descriptions/es", element, XPathConstants.NODESET);
            Node node = (Node) nodes.item(0).getChildNodes().item(0);
            return node.getNodeValue();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return null;
    }

}

