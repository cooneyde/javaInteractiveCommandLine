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

                Server server = new Server(id, serverName);

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


    public void verifyConnection(Connection connection) {
        ResultSet resultSet = null;
        try {
            resultSet = connection.getMetaData().getCatalogs();

            //iterate each catalog in the ResultSet
            while (resultSet.next()) {
                // Get the database name, which is at position 1
                String databaseName = resultSet.getString(1);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}


