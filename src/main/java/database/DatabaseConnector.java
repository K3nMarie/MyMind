package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* MANEJO DE CONEXION A BASE DE DATOS */
public class DatabaseConnector {

    private static final String URL = "jdbc:mysql://localhost:3306/MyMind?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}