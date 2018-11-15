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
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GeneradorDOM {

    List<Libro> myData;
    Document dom;

    public GeneradorDOM() {
        //create a list to hold the data
        myData = new ArrayList<>();

        //initialize the list
        loadData();

        //Get a DOM object
        createDocument();
    }

    public void runExample() {
        System.out.println("Started .. ");
        createDOMTree();
        printToFile();
        System.out.println("Generated file successfully.");
    }

    /**
     * Add a list of books to the list In a production system you might populate
     * the list from a DB
     */
    private void loadData() {
        myData.add(new Libro("Head First Java", "Kathy Sierra .. etc", "Java 1.5"));
        myData.add(new Libro("Head First Design Patterns", "Kathy Sierra .. etc", "Java Architect"));
    }

    /**
     * Using JAXP in implementation independent manner create a document object
     * using which we create a xml tree in memory
     */
    private void createDocument() {

        //get an instance of factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            //get an instance of builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //create an instance of DOM
            dom = db.newDocument();

        } catch (ParserConfigurationException pce) {
            //dump it
            System.out.println("Error while trying to instantiate DocumentBuilder " + pce);
            System.exit(1);
        }

    }

    /**
     * The real workhorse which creates the XML structure
     */
    private void createDOMTree() {

        Element rootEle = dom.createElement("Libros");
        dom.appendChild(rootEle);

        for (Libro l : myData) {
            Element bookEle = createBookElement(l);
            rootEle.appendChild(bookEle);
        }

    }

    /**
     * Helper method which creates a XML element <Book>
     *
     * @param b The book for which we need to create an xml representation
     * @return XML element snippet representing a book
     */
    private Element createBookElement(Libro b) {

        Element bookEle = dom.createElement("libro");
        bookEle.setAttribute("tema", b.getTema());

        //create author element and author text node and attach it to bookElement
        Element authEle = dom.createElement("autor");
        Text authText = dom.createTextNode(b.getAutor());
        authEle.appendChild(authText);
        bookEle.appendChild(authEle);

        //create title element and title text node and attach it to bookElement
        Element titleEle = dom.createElement("titulo");
        Text titleText = dom.createTextNode(b.getTitulo());
        titleEle.appendChild(titleText);
        bookEle.appendChild(titleEle);

        return bookEle;

    }

    /**
     * This method uses Xerces specific classes prints the XML document to file.
     */
    private void printToFile() {

        // REF: Serializar XML: https://www.edureka.co/blog/serialization-of-java-objects-to-xml-using-xmlencoder-decoder/

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File("../libros.xml"));
            Source input = new DOMSource(dom);
            transformer.transform(input, output);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[]) {
        System.out.println("--- DOM (escritura) ---\n");
        new GeneradorDOM().runExample();
    }

}
