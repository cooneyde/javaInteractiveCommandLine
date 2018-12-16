package handlers;

import dbService.DBService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import propertiesLoader.PropertiesLoader;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static org.junit.Assert.*;

public class DeleteServerHandlerTest {

    Properties props;
    DBService dbService;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUp() throws Exception {

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        props = new PropertiesLoader("src/main/resources/testDB.properties").getProperties();
        dbService = new DBService(props);
        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        statement.execute("CREATE TABLE test (id INT NOT NULL, name VARCHAR(50) NOT NULL,description VARCHAR(50) NOT NULL, PRIMARY KEY (id))");
        connection.commit();
        statement.executeUpdate("INSERT INTO test VALUES (1,'server1', 'this is a server')");
        connection.commit();
    }

    @After
    public void tearDown() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("DROP TABLE test");
        connection.commit();

        System.setOut(originalOut);
        System.setErr(originalErr);

    }


    /**
     * Create a connection
     *
     * @return connection object
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(props.getProperty("url"), props.getProperty("userName"), props.getProperty("password"));
    }


    @Test
    public void deleteServer() {
        String option = "deleteServer 1";
        DeleteServerHandler.deleteServer(dbService, option);
        Assert.assertTrue(outContent.toString().contains("Deletion Successful"));
    }

    @Test
    public void deleteServerFailedWrongID() {
        String option = "deleteServer 100";
        DeleteServerHandler.deleteServer(dbService, option);
        Assert.assertTrue(outContent.toString().contains("Deletion failed, unable to locate ID"));
    }
}