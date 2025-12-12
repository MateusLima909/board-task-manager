package br.com.board.persistence.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionConfig {

    private static final String CONNECTION_URL = "jdbc:mysql://db:3306/task_board";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private ConnectionConfig() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
    }    
}