import dbService.DBService;
import fileReader.FileReader;
import handlers.ListServerHandler;
import model.Server;
import propertiesLoader.PropertiesLoader;

import java.lang.*;
import java.sql.*;
import java.io.*;
import java.util.List;
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

                int count = dbService.getServerCount();
                if (count > -1) {
                    System.out.println("Count of Servers: " + dbService.getServerCount());

                } else {
                    System.err.println("An error has occurred, a count of servers cannot be retrieved");

                }

            } else if (option.equalsIgnoreCase("addServer")) {

                System.out.println("Are you providing an xml file to add a server? (Y/N)");
                option = reader.readLine();

                if (option.equalsIgnoreCase("n")) {
                    Server server = gatherInputData(reader);
                    dbService.insertServer(server);

                } else if (option.equalsIgnoreCase("y")) {

                    System.out.println("Provide the file path to the xml file (ensure keys in the xml are named id, name and description");
                    String path = reader.readLine();
                    FileReader fileReader = new FileReader();
                    List<Server> serverList = fileReader.readServerFile(path);

                    if (serverList.size() > 0) {
                        dbService.insertServers(serverList);

                    }

                }
            } else if (option.toLowerCase().contains("deleteserver")) {

                String[] serverID = option.split(" ");  //split string by single space to obtain ID arg
                dbService.deleteServer(serverID[1]);

            } else if (option.equalsIgnoreCase("editServer")) {

                System.out.println("Please provide the id of the server you want to edit and values you want to update (empty values will be ignored)");
                Server server = gatherInputData(reader);
                dbService.updateServer(server);

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


    private static Server gatherInputData(BufferedReader reader) throws IOException {
        Server server = new Server();

        System.out.println("Enter an ID");
        server.id = Integer.parseInt(reader.readLine());

        System.out.println("Enter a name");
        server.name = reader.readLine();

        System.out.println("Enter a description");
        server.description = reader.readLine();

        return server;
    }
}
