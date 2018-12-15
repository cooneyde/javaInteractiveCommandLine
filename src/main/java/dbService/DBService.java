package dbService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Server;

public class DBService {


    /**
     * Retrieves a list of servers from the database
     *
     * @throws SQLException
     */
    public List<Server> getServerList() throws SQLException {

        Statement statement = null;
        List<Server> servers = new ArrayList<Server>();

        DBConnection dbConnection = new DBConnection();

        Connection conn = dbConnection.createConnection();
        String selectTableSQL = "SELECT * FROM test";

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

            if (dbConnection != null) {
                conn.close();
            }
            return servers;
        }
    }


    /**
     * Retrieves a count of the number of rows in the server table
     *
     * @return {int}    A count of servers
     * @throws SQLException
     */
    public int getServerCount() throws SQLException {
        Statement statement = null;
        int count = -1;

        DBConnection dbConnection = new DBConnection();

        Connection conn = dbConnection.createConnection();
        String selectTableSQL = "SELECT COUNT(1) FROM test";


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

            if (dbConnection != null) {
                conn.close();
            }
            return count;
        }

    }


    public long insertServer(Server server) throws SQLException {
        String SQL = "INSERT INTO test(ID,NAME,DESCRIPTION) "
                + "VALUES(?,?,?)";

        long id = 0;

        DBConnection dbConnection = new DBConnection();

        Connection conn = dbConnection.createConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, server.id);
            pstmt.setString(2, server.name);
            pstmt.setString(3, server.description);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {


            if (dbConnection != null) {
                conn.close();
            }
            return id;
        }
    }


    /**
     * insert multiple Servers
     */
    public void insertServers(List<Server> list) {
        String SQL = "INSERT INTO test(ID,NAME,DESCRIPTION) "
                + "VALUES(?,?, ?)";

        DBConnection dbConnection = new DBConnection();

        Connection conn = dbConnection.createConnection();
        PreparedStatement statement = null;

        try {

            statement = conn.prepareStatement(SQL);
            int count = 0;

            for (Server server : list) {
                statement.setInt(1, server.id);
                statement.setString(2, server.name);
                statement.setString(3, server.description);


                statement.addBatch();
                count++;
                // execute every 100 rows or less
                if (count % 100 == 0 || count == list.size()) {
                    statement.executeBatch();
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}


