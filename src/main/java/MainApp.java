import dbService.DBService;
import handlers.ListServerHandler;
import handlers.ServerCountHandler;
import handlers.WriteServerHandler;
import propertiesLoader.PropertiesLoader;

import java.lang.*;
import java.sql.*;
import java.io.*;
import java.util.Properties;

class MainApp {

    public static void main(String[] args) throws SQLException, IOException {
        boolean running = true;

        showHelp();
        Properties properties = new PropertiesLoader("src/main/resources/db.properties").getProperties();
        DBService dbService = new DBService(properties);


        while (running) {

            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String option = reader.readLine();

            if (option.equals("help")) {
                showHelp();

            } else if (option.equals("quit")) {

                running = false;
            } else if (option.equalsIgnoreCase("countServers")) {

                ServerCountHandler.countServers(dbService);

            } else if (option.equalsIgnoreCase("addServer")) {
                WriteServerHandler.addNewServer(dbService, reader);

            } else if (option.toLowerCase().contains("deleteserver")) {

                String[] serverID = option.split(" ");  //split string by single space to obtain ID arg
                dbService.deleteServer(serverID[1]);

            } else if (option.equalsIgnoreCase("editServer")) {
                WriteServerHandler.editServer(dbService, reader);

            } else if (option.equalsIgnoreCase("listServers")) {

                ListServerHandler.listServers(dbService);

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
