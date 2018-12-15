package fileReader;

import model.Server;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FileReader {


    /**
     * Loads, validates and parses a server xml file into a List of Server Objects
     * @param filePath  {String}    Absolute path to the server file on the file system
     * @return          {List<Server>} A list of server objects of length 1:n
     */
    public List<Server> readServerFile(String filePath) {

        List<Server> serverList = new ArrayList<Server>();
        try {
            File xmlFile = new File(filePath);
            InputStream targetStream = new FileInputStream(new File("server.xsd"));
            validate(xmlFile, targetStream);

            Document parsedXml = documentParser(xmlFile);

            NodeList nList = parsedXml.getElementsByTagName("server");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Server server = new Server();
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    server.id = Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent());
                    server.name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    server.description = eElement.getElementsByTagName("description").item(0).getTextContent();

                    serverList.add(server);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverList;
    }


    /**
     * Using a file object, a document is then parsed as an instance of Document
     * @param file  {File}  Input File Object
     * @return      {Document}
     */
    private Document documentParser(File file) {
        Document document = null;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(file);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }


    /**
     * Validates a server xml file against the corresponding XSD (LIMITATION: Finds one error at a time)
     * @param xml   Input xml file containing server objects numbering 1:N
     * @param xsd   Validation file for the xml
     */
    private static void validate(File xml, InputStream xsd) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsd));
            Validator validator = schema.newValidator();
            StreamSource xmlFile = new StreamSource(xml);
            validator.validate(xmlFile);

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
