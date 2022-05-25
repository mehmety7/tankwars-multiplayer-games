package server.dao;


import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

@Getter
public class DataPersistence {

    private static final String dbURL = "jdbc:mysql://localhost:3306/tankwars";
    private static final String username = "root";
    private static final String password = "";
    private static final String tableName = "player";


    private static DataPersistence dataPersistence = null;
    private Connection connection = null;

    private DataPersistence() {

        try {

            connection = DriverManager.getConnection(dbURL, username, password);

            if (connection != null) {
                createTablesIfNotExist();
                System.out.println("Connected to DB");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static DataPersistence getInstance(){
        if (Objects.isNull(dataPersistence)) {
            dataPersistence = new DataPersistence();
        }
        return dataPersistence;
    }

    private void createTablesIfNotExist() {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS " + tableName
                + "  (id           INTEGER not null AUTO_INCREMENT,"
                + "   username            VARCHAR(100),"
                + "   password          VARCHAR(255),"
                + "   isActive           BOOLEAN default TRUE,"
                + "   PRIMARY KEY           (id));";

        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.execute(sqlCreate);
        } catch (SQLException throwables) {
            System.out.println("Create Table operation failed from DataPersistence.java");
            throwables.printStackTrace();
        }
    }
}
