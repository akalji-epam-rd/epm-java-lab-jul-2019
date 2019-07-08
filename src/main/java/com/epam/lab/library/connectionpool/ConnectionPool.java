package com.epam.lab.library.connectionpool;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConnectionPool {

    private static final int CAPACITY = 10;

    private ConcurrentLinkedQueue<Connection> availableConnections;
    private Vector<Connection> usedConnections;
    private String url = null;
    private String user = null;
    private String password = null;

    private static volatile ConnectionPool instance;

    public static ConnectionPool getInstance() {
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionPool();
                }
            }
        }
        return localInstance;
    }

    private ConnectionPool() {
    }

    {
        try (FileInputStream fis = new FileInputStream("src/main/resources/db.properties")) {
            Properties property = new Properties();
            property.load(fis);

            url = property.getProperty("db.url");
            user = property.getProperty("db.user");
            password = property.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
        }

        usedConnections = new Vector<>();
        availableConnections = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < CAPACITY; i++) {
            try {
                availableConnections.add(createConnection(url, user, password));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Connection createConnection(String url, String user, String password) throws SQLException {

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
    public Connection getConnection() throws SQLException {

        if (availableConnections.isEmpty()) {
            return createConnection(url, user, password);
        }

        Connection connection = availableConnections.poll();
        usedConnections.add(connection);
        return connection;
    }

    /**
     * return connection in pool if pool isn't fill up
     * @param connection
     */
    public void releaseConnection(Connection connection) {

        usedConnections.remove(connection);

        if (availableConnections.size() < CAPACITY) {
            availableConnections.add(connection);
        } else {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int getSize() {
        return availableConnections.size() + usedConnections.size();
    }


}
