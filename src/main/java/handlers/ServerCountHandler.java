package handlers;

import dbService.DBService;

import java.sql.SQLException;

public class ServerCountHandler {

    public static void countServers(DBService dbService) {
        int count;
        try {
            count = dbService.getServerCount();

            if (count > -1) {
                System.out.println("Count of Servers: " + dbService.getServerCount());

            } else {
                System.err.println("An error has occurred, a count of servers cannot be retrieved");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
