package com.epam.lab.library.dao;

import com.epam.lab.library.util.connectionpool.ConnectionPool;
import com.epam.lab.library.domain.Status;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class StatusesDaoImplTest {

    private static ConnectionPool pool = ConnectionPool.getInstance();
    private static Connection conn = null;

    private static Status status1 = null;
    private static Status status2 = null;
    private static Status status3 = null;
    private static List<Status> statusListExpected = new ArrayList<>();
    private List<Status> emptyStatusList = new ArrayList<>();

    @BeforeClass
    public static void initialize() throws Exception {
        conn = pool.getConnection();

        status1 = new Status();
        status1.setId(1);
        status1.setName("taken");
        status2 = new Status();
        status2.setId(2);
        status2.setName("not taken");
        status3 = new Status();
        status3.setId(3);
        status3.setName("reading room");

        statusListExpected.add(status1);
        statusListExpected.add(status2);
        statusListExpected.add(status3);

        String sql = "INSERT INTO library.statuses (name) " +
                "VALUES " +
                "('taken'), " +
                "('not taken'), " +
                "('reading room')";



        Statement st = conn.createStatement();

        st.execute("DROP TABLE IF EXISTS library.statuses CASCADE ");
        st.execute("CREATE TABLE library.statuses(" +
                "    id SERIAL," +
                "    name VARCHAR(50) NOT NULL," +
                "    CONSTRAINT statuses_pkey PRIMARY KEY(id)," +
                "    CONSTRAINT name_statuses_key UNIQUE(name)" +
                ")");
        st.execute(sql);

    }

    @After
    public void release() {
        pool.releaseConnection(conn);
    }

    @Test
    public void getAll() throws Exception {


        List<Status> statusListActual = new ArrayList<>();
        Statement statement = conn.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM library.statuses");
        while (rs.next()) {
            Status status = new Status();
            status.setId(rs.getInt("id"));
            status.setName(rs.getString("name"));
            statusListActual.add(status);
        }

        assertEquals(statusListExpected, statusListActual);
        assertNotEquals(emptyStatusList, statusListActual);
    }

    @Test
    public void getById() throws Exception {

        PreparedStatement statement = null;
        Status status = new Status();

        statement = conn.prepareStatement("SELECT * FROM library.statuses WHERE id = ?");
        statement.setInt(1, 1);

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            status.setId(rs.getInt("id"));
            status.setName(rs.getString("name"));
        }

        assertEquals(this.status1, status);
        assertNotEquals(this.status2, status);
        assertNotEquals(this.status3, status);
    }
}