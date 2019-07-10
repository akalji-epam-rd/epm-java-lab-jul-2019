package com.epam.lab.library.dao;

import com.epam.lab.library.connectionpool.ConnectionPool;
import com.epam.lab.library.dao.interfaces.RoleDao;
import com.epam.lab.library.domain.Role;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class RoleDaoImplTest {

    private ConnectionPool pool = ConnectionPool.getInstance();
    private Connection conn = null;
    private RoleDao roleExpected = new RoleDaoImpl();
    private Role newRole;
    private Role updatedRole;

    @Before
    public void initialize() throws SQLException {
        Statement st = null;
        try {
            conn = pool.getConnection();
            if (conn.isClosed()) {
                conn = pool.getConnection();
            }
            st = conn.createStatement();

        } catch (PSQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void save() throws SQLException {
        RoleDao roleDao = new RoleDaoImpl();
        Integer id = roleDao.save(roleExpected);
        roleExpected.setId(id);

        assertEquals(roleExpected, roleDao.getById(id));
        assertNotEquals(roleExpected, 3);
    }

    @Test
    public void update() throws SQLException {
        RoleDao roleDao = new RoleDaoImpl();
        roleExpected.setId(666);
        roleDao.update(roleExpected);

        assertEquals(roleExpected, roleDao.getById(666));
        roleExpected.setName("TESTROLE");
        assertNotEquals(roleExpected, roleDao.getById(666));

    }

    @Test
    public void getById() throws Exception {
        RoleDao roleDao = new RoleDaoImpl();
        roleExpected.setId(1);

        assertEquals(roleExpected, roleDao.getById(1));
        assertNotEquals(roleExpected, roleDao.getById(666));
    }

    @Test
    public void delete() throws Exception {

        roleDao.update(roleExpected);
        boolean deleted = roleDao.delete(roleExpected.getId());

        assertEquals(true, deleted);
        assertNotEquals(null, roleDao.getById(roleExpected.getId()));

    }


}
