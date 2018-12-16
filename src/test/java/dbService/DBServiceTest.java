package dbService;

import fileReader.FileReader;
import model.Server;
import org.junit.*;
import propertiesLoader.PropertiesLoader;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;


public class DBServiceTest {

    private Properties props;
    private DBService dbService;

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

        System.setOut(originalOut);
        System.setErr(originalErr);

    }


    /**
     * Create a connection
     *
     * @return connection object
     * @throws SQLException Throws an exception if connection to db fails
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(props.getProperty("url"), props.getProperty("userName"), props.getProperty("password"));
    }


    @Test
    public void getServerList() {


        try {
            List<Server> result = dbService.getServerList();
            Assert.assertEquals(3, result.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testInsertServer() {
        Server server = new Server(6, "a new name", "we should describe the server");
        try {
            dbService.insertServer(server);
            Assert.assertTrue( outContent.toString().contains("Insert successful"));


            Server server1 = getServerItem(server.id);
            Assert.assertEquals(server1.id, server.id);
            Assert.assertEquals(server1.description, server.description);
            Assert.assertEquals(server1.name, server.name);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testFailedInsertServerDuplicateID() {
        Server server = new Server(1, "a new name", "we should describe the server");
        try {
            dbService.insertServer(server);

            Assert.assertTrue( outContent.toString().contains("Insert failed"));

            Server server1 = getServerItem(server.id);
            Assert.assertNotEquals(server1.description, server.description);
            Assert.assertNotEquals(server1.name, server.name);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testInsertServers() throws SQLException {
        FileReader fileReader = new FileReader();
        List<Server> serverList = fileReader.readServerFile("src/test/data/servers.xml");
        dbService.insertServers(serverList);

        Server server1 = new Server(200, "MyServerName2", "This is a few words about the server2");
        Server server2 = new Server(500, "MyServerName", "This is a few words about the server");

        Server server1Inserted = getServerItem(server1.id);
        Assert.assertEquals(server1Inserted.id, server1.id);
        Assert.assertEquals(server1Inserted.description, server1.description);
        Assert.assertEquals(server1Inserted.name, server1.name);

        Server server2Inserted = getServerItem(server2.id);
        Assert.assertEquals(server2Inserted.id, server2.id);
        Assert.assertEquals(server2Inserted.description, server2.description);
        Assert.assertEquals(server2Inserted.name, server2.name);
    }


    @Test
    public void testExpectedFailInsertServersDuplicateIDs() throws SQLException {
        FileReader fileReader = new FileReader();
        List<Server> serverList = fileReader.readServerFile("src/test/data/servers_duplicate_ids.xml");
        dbService.insertServers(serverList);

        Server server1 = new Server(1, "duplicate myservername2", "duplicate This is a few words about the server2");
        Server server2 = new Server(2, "duplicate myservername", "duplicate This is a few words about the server");

        Server server1Inserted = getServerItem(server1.id);
        Assert.assertNotEquals(server1Inserted.description, server1.description);
        Assert.assertNotEquals(server1Inserted.name, server1.name);

        Server server2Inserted = getServerItem(server2.id);
        Assert.assertNotEquals(server2Inserted.description, server2.description);
        Assert.assertNotEquals(server2Inserted.name, server2.name);
    }


    @Test
    public void testDeleteServer() throws SQLException {
        dbService.deleteServer("1");
        Server server = new Server(1, "server1", "this is a server");

        Server deletedServer = getServerItem(server.id);
        Assert.assertEquals(0, deletedServer.id);
        Assert.assertNull(deletedServer.name);
        Assert.assertNull(deletedServer.description);

    }


    @Test
    public void testExpectedFailDeleteServerInvalidID() throws SQLException {
        dbService.deleteServer("15665");

        Assert.assertTrue( outContent.toString().contains("Deletion failed"));
    }


    @Test
    public void testUpdateServerSingleParameter() throws SQLException {
        Server server = new Server(2, "server 2 with a new title", "more of this");
        dbService.updateServer(server);

        Server serverUpdated = getServerItem(server.id);
        Assert.assertEquals(serverUpdated.id, server.id);
        Assert.assertEquals(serverUpdated.description, server.description);
        Assert.assertEquals(serverUpdated.name, server.name);
    }


    @Test
    public void testUpdateServerAllParams() throws SQLException {
        Server server = new Server(2, "server 2 with a new title", "more of this with new description");
        dbService.updateServer(server);

        Server serverUpdated = getServerItem(server.id);
        Assert.assertEquals(serverUpdated.id, server.id);
        Assert.assertEquals(serverUpdated.description, server.description);
        Assert.assertEquals(serverUpdated.name, server.name);
    }


    /**
     * Get total records in table
     *
     * @return total number of records. In case of exception 0 is returned
     */
    private Server getServerItem(long id) {
        Server server = new Server();

        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM test WHERE ID=" + id);
            while (result.next()) {
                server.id = result.getInt("ID");
                server.description = result.getString("DESCRIPTION");
                server.name = result.getString("NAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return server;
    }


}