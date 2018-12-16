package handlers;

import dbService.DBService;

import java.sql.SQLException;

public class DeleteServerHandler {

    public static void deleteServer(DBService dbService, String option) {

        try {
            String[] serverID = option.split(" ");  //split string by single space to obtain ID arg
            if (!serverID[1].trim().isEmpty()) {
                dbService.deleteServer(serverID[1]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
