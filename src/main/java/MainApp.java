import dbService.DBService;
import model.Server;

import java.lang.*;
import java.sql.*;
import java.io.*;
import java.util.List;

public class MainApp {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        boolean running = true;

        showHelp();

        while (running) {


            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String option = reader.readLine();

            DBService dbService = new DBService();
            if (option.equals("help")) {
                showHelp();

            } else if (option.equals("quit")) {
                running = false;
            } else if (option.equalsIgnoreCase("countServers")) {

                System.out.println("Count of Servers: " + dbService.getServerCount());

            } else if (option.equalsIgnoreCase("addServer")) {

                // TODO implement...
            } else if (option.equalsIgnoreCase("deleteServer")) {
                // TODO implement...
            } else if (option.equalsIgnoreCase("editServer")) {
                // TODO implement...
            } else if (option.equalsIgnoreCase("listServers")) {

                List<Server> servers = dbService.getServerList();
                for (Server item: servers) {
                    System.out.println("________________________________");
                    System.out.println("ID: " + item.id);
                    System.out.println("Server Name: " + item.name);
                }

            } else {
                System.out.printf("Your input of '%s' is not a valid input. Please enter one of the following: \n" +
                        " help \n" +
                        " quit \n" +
                        " countServers \n" +
                        " addServer \n" +
                        " deleteServer \n" +
                        " editServer \n" +
                        " listServers \n" +
                        "%n", option);


            }
        }
    }

    private static void showHelp() {
        System.out.println("help to display this message");
        System.out.println("countServers: Display the current number of servers present");
        System.out.println("addServer: Add a New Server to the database");
        System.out.println("editServer: to change the name of a server identified by id (takes 2 additional args - the id and the new name)");
        System.out.println("deleteServer: to delete a server (takes one more arg - the id of the server to delete, e.g. deleteServer 2)");
        System.out.println("listServers:  Display details of all servers");
    }
}
