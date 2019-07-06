package com.epam.lab.library.connectionpool;

import org.junit.Test;

import java.sql.Connection;

public class ConnectionPoolTest {

    ConnectionPool cp = ConnectionPool.getInstance();

    @Test
    public void getConnection() throws Exception {

        assert(cp.getConnection() != null);

    }

    @Test
    public void releaseConnection() throws Exception {

    }

    @Test
    public void getSize() throws Exception {

        int cpSize = cp.getSize();
        Connection connection = cp.getConnection();
        assert(cp.getSize() == cpSize - 1);
        cp.releaseConnection(connection);
        assert(cp.getSize() == cpSize);

    }

}