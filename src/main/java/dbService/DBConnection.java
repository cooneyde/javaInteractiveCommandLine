package dbService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private final String url;
    private final String dbName;
    private final String driver;
    private final String userName;
    private final String password;

    public DBConnection() {
        this.url = "jdbc:mysql://localhost:3306/mysql?useSSL=false";
        this.dbName = "dermotTest";
        this.driver = "com.mysql.cj.jdbc.Driver";
        this.userName = "root";
        this.password = "password";
    }


    /**
     * Creates a new database connection
     * @return
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
