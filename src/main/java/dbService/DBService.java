package dbService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import model.Server;

public class DBService {

    private DBConnection dbConnection;

    public DBService(Properties props) {

        dbConnection = new DBConnection(props);
    }

    /**
     * Retrieves a list of servers from the database
     *
     * @throws SQLException SQL exception in the event that connection cannot be closed
     */
    public List<Server> getServerList() throws SQLException {

        Statement statement = null;
        List<Server> servers = new ArrayList<Server>();

        Connection conn = dbConnection.createConnection();
        String selectTableSQL = "SELECT * FROM test";

        if (conn != null) {
            try {
                statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(selectTableSQL);

                while (rs.next()) {

                    int id = rs.getInt("ID");
                    String serverName = rs.getString("NAME");
                    String description = rs.getString("DESCRIPTION");
                    Server server = new Server(id, serverName, description);

                    servers.add(server);
                }
                rs.close();

            } catch (SQLException e) {

                System.out.println(e.getMessage());

            } finally {

                if (statement != null) {
                    statement.close();
                }
                conn.close();
            }
        }
        return servers;
    }


    /**
     * Retrieves a count of the number of rows in the server table
     *
     * @return {int}    A count of servers
     * @throws SQLException SQL exception in the event that connection cannot be closed
     */
    public int getServerCount() throws SQLException {
        Statement statement = null;
        int count = -1;

        Connection conn = dbConnection.createConnection();
        String selectTableSQL = "SELECT COUNT(1) FROM test";

        if (conn != null) {
            try {
                statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(selectTableSQL);

                count = rs.last() ? rs.getInt(1) : 0;
                rs.close();

            } catch (SQLException e) {

                System.out.println(e.getMessage());

            } finally {

                if (statement != null) {
                    statement.close();
                }
                conn.close();
            }
        }

        return count;
    }


    /**
     * Inserts a new Server into the server table
     *
     * @param server {Server} Server object containing ID and either or both name and description
     * @throws SQLException Thrown when a connection cannot be closed
     */
    public void insertServer(Server server) throws SQLException {
        String SQL = "INSERT INTO test(ID,NAME,DESCRIPTION) VALUES(?,?,?)";
        Connection conn = dbConnection.createConnection();
        PreparedStatement statement;
        int result = 0;

        if (conn != null) {
            try {
                statement = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, server.id);
                statement.setString(2, server.name);
                statement.setString(3, server.description);

                result = statement.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());

            } finally {

                if (result > 0) {
                    System.out.println("Insert successful");
                } else {
                    System.out.println("Insert failed");
                }

                conn.close();

            }
        }

    }


    /**
     * insert multiple Servers
     *
     * @param list {List<Server>}    A list of Server objects
     * @throws SQLException Thrown when a connection cannot be closed
     */
    public void insertServers(List<Server> list) throws SQLException {
        String SQL = "INSERT INTO test(ID,NAME,DESCRIPTION) VALUES(?,?, ?)";

        Connection conn = dbConnection.createConnection();
        PreparedStatement statement;

        int count = 0;
        int[] result = new int[0];

        if (conn != null) {
            try {

                statement = conn.prepareStatement(SQL);

                for (Server server : list) {
                    statement.setInt(1, server.id);
                    statement.setString(2, server.name);
                    statement.setString(3, server.description);

                    statement.addBatch();
                    count++;
                }
                result = statement.executeBatch();

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {

                if (result.length == count && count != 0) {
                    System.out.println("All insertions successful");
                } else {
                    System.out.println("Some Insertions failed");
                }

                conn.close();
            }
        }

    }


    /**
     * Deletes a server given an ID
     *
     * @param id {String}   Contains the id of the server to delete
     */
    public void deleteServer(String id) throws SQLException {

        String sql = "DELETE FROM test WHERE id = ?";
        PreparedStatement statement;
        Connection conn = dbConnection.createConnection();
        int result = 0;

        if (conn != null) {
            try {

                statement = conn.prepareStatement(sql);
                statement.setString(1, id);
                result = statement.executeUpdate();


            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                if (result > 0) {
                    System.out.println("Deletion Successful");

                } else {
                    System.out.println("Deletion failed");
                }
                conn.close();
            }
        }

    }


    /**
     * Updates an existing server row with new data
     *
     * @param server {Server} Object containing id of the server, and name/description (or both)
     * @throws SQLException
     */
    public void updateServer(Server server) throws SQLException {

        Connection conn = dbConnection.createConnection();
        Statement statement;
        int count = 0;
        int[] result = new int[0];

        if (conn != null) {
            try {

                statement = conn.createStatement();
                if (server.name.trim().length() != 0) {
                    String sql = "UPDATE test SET NAME='" + server.name + "' WHERE ID=" + server.id;

                    statement.addBatch(sql);
                    count++;

                }
                if (server.description.trim().length() != 0) {
                    String sql = "UPDATE test SET DESCRIPTION='" + server.description + "' WHERE ID=" + server.id;
                    statement.addBatch(sql);
                    count++;

                }
                result = statement.executeBatch();


            } catch (SQLException e) {
                System.out.println(e);
            } finally {

                if (result.length == count) {
                    System.out.println("Update successful");
                } else {
                    System.out.println(count - result.length + " updates failed");

                }
                conn.close();
            }
        }

    }
}


