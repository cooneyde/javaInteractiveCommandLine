package dbService;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.sql.*;


public class DBConnectionTest {


    @InjectMocks private DBConnection dbConnection = new DBConnection();

    @Test
    public void testCreateConnection() {


        Connection value = dbConnection.createConnection();
        Assert.assertTrue(value instanceof Connection);
        Assert.assertNotNull(value);
    }
}

