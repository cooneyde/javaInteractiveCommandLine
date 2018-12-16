package handlers;

import dbService.DBService;
import model.Server;

import java.sql.SQLException;
import java.util.List;


public class ListServerHandler {

    public static void listServers(DBService dbService) {
        List<Server> servers;
        try {
            servers = dbService.getServerList();
            if (servers.size() > 0) {
                for (Server item : servers) {
                    System.out.println("________________________________");
                    System.out.println("ID: " + item.id);
                    System.out.println("Server Name: " + item.name);
                    if (item.description != null && item.description.trim().length() != 0) {
                        System.out.println("Description: " + item.description);
                    }
                }
            } else {
                System.out.println("List of servers is empty");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
