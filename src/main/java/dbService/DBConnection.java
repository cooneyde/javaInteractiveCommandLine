package dbService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private String url;
    private String driver;
    private String userName;
    private String password;


    public DBConnection(Properties prop) {

        this.url = prop.getProperty("url");
        this.driver = prop.getProperty("driver");
        this.userName = prop.getProperty("userName");
        this.password = prop.getProperty("password");
    }


    /**
     * Creates a new database connection
     * @return  Returns a connection object containing the connection to an SQL database instance
     */
    public Connection createConnection() {

        Connection conn = null;
        try {
            Class.forName(this.driver).newInstance();
            conn = DriverManager.getConnection(this.url, this.userName, this.password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
