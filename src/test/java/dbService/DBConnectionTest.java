package dbService;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import propertiesLoader.PropertiesLoader;

import java.sql.*;
import java.util.Properties;


public class DBConnectionTest {

    DBConnection dbConnection;
    Properties props;


    @Before
    public void setUp() throws Exception {
        props = new PropertiesLoader("src/main/resources/testDB.properties").getProperties();
        dbConnection = new DBConnection(props);
        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        statement.execute("CREATE TABLE test (id INT NOT NULL, name VARCHAR(50) NOT NULL,description VARCHAR(50) NOT NULL, PRIMARY KEY (id))");
        connection.commit();
        statement.executeUpdate("INSERT INTO test VALUES (1,'server1', 'this is a server')");
        statement.executeUpdate("INSERT INTO test VALUES (2,'server 2', 'more of this')");
        statement.executeUpdate("INSERT INTO test VALUES (3,'server 3', 'one more time')");
        connection.commit();
    }

    @After
    public void tearDown() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("DROP TABLE test");
        connection.commit();

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
    public void testCreateConnection() {

        Connection value = dbConnection.createConnection();
        Assert.assertTrue(value instanceof Connection);
        Assert.assertNotNull(value);
    }

    @Test
    public void testExpectedFailCreateConnectionBadDriver() {
        props.setProperty("driver", "not.a.driver");
        dbConnection = new DBConnection(props);

        Connection value = dbConnection.createConnection();
        Assert.assertNull(value);

        //reset properties file and db connection
        props = new PropertiesLoader("src/main/resources/testDB.properties").getProperties();
        dbConnection = new DBConnection(props);
    }

   /* @Test
    public void testExpectedFailCreateConnectionBadCredentials() {
        props.setProperty("password", "notAPassword");
        dbConnection = new DBConnection(props);

        Connection value = dbConnection.createConnection();
        Assert.assertNull(value);
    }*/
}

