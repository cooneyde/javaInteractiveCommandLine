package dbService;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import propertiesLoader.PropertiesLoader;

import java.sql.*;
import java.util.Properties;


public class DBConnectionTest {

    Properties properties = new PropertiesLoader("src/main/resources/db.properties").getProperties();

    @InjectMocks private DBConnection dbConnection = new DBConnection(properties);

    @Test
    public void testCreateConnection() {


        Connection value = dbConnection.createConnection();
        Assert.assertTrue(value instanceof Connection);
        Assert.assertNotNull(value);
    }
}

