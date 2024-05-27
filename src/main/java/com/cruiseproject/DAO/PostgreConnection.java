package com.cruiseproject.DAO;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Клас зв'язку програми з БД
@Component
public class PostgreConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/JavaProject";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static Connection connection;
    static { // Спроба підключитися до БД
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //Метод отримання зв'язку до БДі
    public static Connection getConnection() {
        return connection;
    }
}
