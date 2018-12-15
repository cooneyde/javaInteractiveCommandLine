package dbService;

import model.Server;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;


import org.junit.Test;
import propertiesLoader.PropertiesLoader;

import java.io.FileInputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;


public class DBServiceTest extends DBTestCase {

    DBService dbService;

    public DBServiceTest() {

        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.hsqldb.jdbcDriver" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:hsqldb:mysql" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "password" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "" );

        Properties props = new PropertiesLoader("src/main/resources/testDB.properties").getProperties();
        dbService = new DBService(props);
    }

    @Test
    public void getServerList() {

        try {
            List<Server> result = dbService.getServerList();
            assertTrue(result instanceof List);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void getServerCount() {

        try {
            int result = dbService.getServerCount();
            //assertEquals(result, 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/data/db/db.xml"));
    }

    protected DatabaseOperation getSetUpOperation() throws Exception
    {
        return DatabaseOperation.REFRESH;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception
    {
        return DatabaseOperation.NONE;
    }
}