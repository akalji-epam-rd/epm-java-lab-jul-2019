package com.epam.lab.library.util.connectionpool;

import org.junit.Test;

public class ConnectionPoolTest {

    ConnectionPool cp = ConnectionPool.getInstance();

    @Test
    public void getConnection() throws Exception {


        assert (cp.getConnection() != null);


    }

}