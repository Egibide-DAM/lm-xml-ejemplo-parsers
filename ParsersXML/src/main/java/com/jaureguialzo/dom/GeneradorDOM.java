package com.jaureguialzo.dom;

import com.jaureguialzo.libros.Libro;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GeneradorDOM {

    // REF: Ejemplo original (obsoleto): https://www.java-samples.com/showtutorial.php?tutorialid=152

    private List<Libro> libros;
    private Document dom;

    public GeneradorDOM() {

        // Lista para almacenar los objetos
        libros = new ArrayList<>();

        // Rellenar la lista con datos de prueba
        cargarDatos();

        // Construir un documento DOM vacío
        crearDocumento();
    }

    public void run() {
        System.out.println("Iniciando...");
        crearArbolDOM();
        exportarFichero();
        System.out.println("Fichero generado.");
    }

    private void cargarDatos() {
        libros.add(new Libro("Head First Java", "Kathy Sierra .. etc", "programacion"));
        libros.add(new Libro("Head First Design Patterns", "Kathy Sierra .. etc", "ingenieria_software"));
    }

    private void crearDocumento() {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.newDocument();

            // REF: No hay esquema o DTD: https://stackoverflow.com/a/8438236
            dom.setXmlStandalone(true);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

    private void crearArbolDOM() {

        Element raiz = dom.createElement("libros");
        dom.appendChild(raiz);

        for (Libro libro : libros) {
            Element elemento = crearElementoLibro(libro);
            raiz.appendChild(elemento);
        }

    }

    private Element crearElementoLibro(Libro libro) {

        // <libro tema="">
        Element elementoLibro = dom.createElement("libro");
        elementoLibro.setAttribute("tema", libro.getTema());

        // <autor>
        Element elementoAutor = dom.createElement("autor");

        // #PCDATA
        Text textoAutor = dom.createTextNode(libro.getAutor());
        elementoAutor.appendChild(textoAutor);

        // </libro>
        elementoLibro.appendChild(elementoAutor);

        // <titulo>
        Element elementoTitulo = dom.createElement("titulo");

        // #PCDATA
        elementoTitulo.appendChild(dom.createTextNode(libro.getTitulo()));

        // </titulo>
        elementoLibro.appendChild(elementoTitulo);

        // </libro>
        return elementoLibro;

    }

    private void exportarFichero() {

        // REF: Serializar XML: https://www.edureka.co/blog/serialization-of-java-objects-to-xml-using-xmlencoder-decoder/

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();

            // REF: Indentar la salida: https://stackoverflow.com/a/1264872
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

            Result output = new StreamResult(new File("../libros.xml"));
            Source input = new DOMSource(dom);

            transformer.transform(input, output);

        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[]) {
        System.out.println("--- DOM (escritura) ---\n");
        new GeneradorDOM().run();
    }

}
