package com.epam.lab.library.dao;

import com.epam.lab.library.dao.interfaces.RoleDao;
import com.epam.lab.library.domain.Role;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class RoleDaoImplTest {

    private Role roleExpected = new Role();

    @Before
    public void initialize() {

        roleExpected.setId(666);
        roleExpected.setName("Satan");

    }

    @Test
    public void save(Role role) throws SQLException {
        RoleDao roleDao = new RoleDaoImpl();
        Integer id = roleDao.save(roleExpected);
        roleExpected.setId(id);

        assertEquals(roleExpected, roleDao.getById(id));
        assertNotEquals(roleExpected, 3);
    }

    @Test
    public void update(Role role) throws SQLException {
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
        RoleDao roleDao = new RoleDaoImpl();
        List<Role> roleList = roleDao.getAll();

        if (!roleList.isEmpty()) {
            roleDao.delete(roleList.get(0).getId());
            assertNull(roleDao.getById(roleList.get(0).getId()));
            assertNotEquals(roleDao.getById(roleList.get(0).getId()), roleList.get(0));
        }

    }
}
