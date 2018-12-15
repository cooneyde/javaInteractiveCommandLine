package fileReader;

import model.Server;
import org.junit.Test;

import java.io.*;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import static org.junit.Assert.*;

public class FileReaderTest {

    @Test
    public void readServerFile() {
        FileReader fileReader = new FileReader();

        List<Server> serverList = fileReader.readServerFile("server_1.xml");
        assertEquals( 2, serverList.size());
    }

    @Test
    public void readInvalidServerFile() {
        FileReader fileReader = new FileReader();

        List<Server> serverList = fileReader.readServerFile("src/test/data/server_bad_data.xml");
        assertEquals( 0, serverList.size());
    }


    @Test
    public void readInvalidPathServerFile() {
        FileReader fileReader = new FileReader();

        List<Server> serverList = fileReader.readServerFile("src/test/data/not_a_file.xml");
        assertEquals( 0, serverList.size());
    }


    @Test (expected = SAXParseException.class)
    public void testValidationXmlFile() throws IOException, SAXException {

        FileReader fileReader = new FileReader();
        File xmlFile = new File("src/test/data/server_bad_data.xml");
        InputStream targetStream = new FileInputStream(new File("server.xsd"));
        fileReader.validate(xmlFile, targetStream);
    }
}