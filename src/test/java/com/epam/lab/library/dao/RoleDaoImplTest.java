package com.epam.lab.library.dao;

import com.epam.lab.library.domain.Role;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class RoleDaoImplTest {

    private Role roleEpected = new Role();

    @Before
    public void initialize() throws Exception {

        roleEpected.setId(666);
        roleEpected.setName("Satan");

    }

    @Test
    public void save(Role role) throws SQLException {
        RoleDaoImpl roleDao = new RoleDaoImpl();

        Integer id = roleDao.save(roleEpected);
        roleEpected.setId(id);

        assertEquals(roleEpected, roleDao.getById(id));
        assertNotEquals(roleEpected, 3);
    }

    @Test
    public void update(Role role) {
        RoleDaoImpl roleDao = new RoleDaoImpl();

        roleEpected.setId(3);

    }


}
