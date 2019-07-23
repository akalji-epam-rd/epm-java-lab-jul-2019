package com.epam.lab.library.util.connectionpool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Connection pool class
 */
public class ConnectionPool {

    private static final int CAPACITY = 10;

    private static List<Connection> availableConnections;
    private static String url = null;
    private static String user = null;
    private static String password = null;
    private static ConnectionPool instance;

    /**
     * Returns instance of connection pool
     *
     * @return Connection pool instance
     */
    public static ConnectionPool getInstance() {

        ConnectionPool localInstance = instance;
        if (localInstance == null) {

            try {
                Class.forName("org.postgresql.Driver");
                Properties property = new Properties();
                property.load(ConnectionPool.class.getClassLoader().getResourceAsStream("db.properties"));
                url = property.getProperty("db.url");
                user = property.getProperty("db.user");
                password = property.getProperty("db.password");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            availableConnections = new ArrayList<>();
            for (int i = 0; i < CAPACITY; i++) {
                try {
                    availableConnections.add(createConnection(url, user, password));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            localInstance = instance;
            if (localInstance == null) {
                instance = localInstance = new ConnectionPool();
            }
        }
        return localInstance;
    }

    private ConnectionPool() {
    }

    private static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(
                url,
                user,
                password);
    }

    /**
     * retrieve Connection from availableConnections
     * if there no available connection create new one
     *
     * @return Connection
     */
    public synchronized Connection getConnection() throws SQLException {

        if (availableConnections.isEmpty()) {
            return createConnection(url, user, password);
        }

        Connection connection = availableConnections.get(0);
        availableConnections.remove(connection);

        return connection;
    }

    /**
     * return connection in pool if pool isn't fill up
     *
     * @param connection
     */
    public synchronized void releaseConnection(Connection connection) {

        if (connection == null) {
            return;
        }

        try {
            if (availableConnections.size() < CAPACITY & !connection.isClosed()) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                availableConnections.add(connection);

            } else {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
