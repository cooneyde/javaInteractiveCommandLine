package handlers;

import dbService.DBService;
import fileReader.FileReader;
import model.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class WriteServerHandler {


    /**
     * Add a new server by manual input or by xml file (xml file supports adding 1 or many servers)
     * @param dbService Contains the DBService Object
     * @param reader    Contains the reader object which is consuming input from the console
     */
    public static void addNewServer(DBService dbService, BufferedReader reader) {

        try {
            System.out.println("Are you providing an xml file to add a server? (Y/N)");
            String option = reader.readLine();

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
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Edits an existing server in the database with a provided manual input
     * @param dbService Contains the DBService Object
     * @param reader    Contains the reader object which is consuming input from the console
     */
    public static void editServer(DBService dbService, BufferedReader reader) {

        try {
            System.out.println("Please provide the id of the server you want to edit and values you want to update (empty values will be ignored)");
            Server server = gatherInputData(reader);
            dbService.updateServer(server);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Gathers data for manual input of server data
     * @param reader    Contains the reader object which is consuming input from the console
     * @return  {Server}    Server Object containing id, name and description of a server
     * @throws IOException
     */
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



