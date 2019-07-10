package com.epam.lab.library.dao;


import com.epam.lab.library.dao.interfaces.RoleDao;
import com.epam.lab.library.domain.Role;
import com.epam.lab.library.domain.User;
import com.epam.lab.library.util.connectionpool.ConnectionPool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class RoleDaoImplTest {

    private ConnectionPool pool = ConnectionPool.getInstance();
    private Connection conn = null;
    private RoleDao roleDao = new RoleDaoImpl();
    private Role newRole;
    private Role updatedRole;

    private String initializeRoleDataSql = "INSERT INTO library.roles (name) VALUES ('Student'), ('Admin'),('Librarian')";
    private String initializeUserDataSql = "INSERT INTO library.users (email, password, name, lastname) " +
                    "VALUES " +
                    "  ('ya@ya.ru', '12345', 'Vasya', 'Vasilev'), " +
                    "  ('go@google.com', '54789', 'Ivan', 'Ivanov'), " +
                    "  ('vn@yandex.net', 'qwerty', 'Petya', 'Petrovich');";

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

        st.execute("DROP TABLE IF EXISTS library.users CASCADE ");
        st.execute("DROP TABLE IF EXISTS library.users_roles CASCADE ");
        st.execute("DROP TABLE IF EXISTS library.roles CASCADE ");

        st.execute("CREATE TABLE library.users (  " +
                " id SERIAL,  " +
                "    email VARCHAR(100) NOT NULL,  " +
                "    password VARCHAR(100) NOT NULL,  " +
                "    name VARCHAR(50),  " +
                "    lastname VARCHAR(50),  " +
                " CONSTRAINT users_pkey PRIMARY KEY (id),  " +
                "    CONSTRAINT email_key UNIQUE (email)  " +
                ")");
        st.execute("CREATE TABLE library.roles (" +
                "id SERIAL," +
                "    name VARCHAR(50) NOT NULL," +
                "CONSTRAINT roles_pkey PRIMARY KEY (id)," +
                "    CONSTRAINT name_roles_key UNIQUE (name)" +
                ")");
        st.execute("CREATE TABLE library.users_roles(" +
                "user_id INTEGER NOT NULL," +
                "    role_id INTEGER NOT NULL," +
                "    CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id)," +
                "    CONSTRAINT users_fkey FOREIGN KEY (user_id)" +
                "    REFERENCES library.users(id)" +
                "        ON DELETE RESTRICT" +
                "    ON UPDATE RESTRICT" +
                "    NOT DEFERRABLE," +
                "    CONSTRAINT roles_fkey FOREIGN KEY (role_id)" +
                "    REFERENCES library.roles(id)" +
                "        ON DELETE RESTRICT" +
                "    ON UPDATE RESTRICT" +
                "    NOT DEFERRABLE" +
                ")");


        st.execute(initializeRoleDataSql);
        st.execute(initializeUserDataSql);

        //Users
        User user1 = new User(1);
        user1.setEmail("ya@ya.ru");
        user1.setPassword("12345");
        user1.setName("Vasya");
        user1.setLastName("Vasilev");
        User user2 = new User(2);
        user2.setEmail("go@google.com");
        user2.setPassword("54789");
        user2.setName("Ivan");
        user2.setLastName("Ivanov");
        User user3 = new User(3);
        user3.setEmail("vn@yandex.net");
        user3.setPassword("qwerty");
        user3.setName("Petya");
        user3.setLastName("Petrovich");

    }

    @After
    public void deleteDbData() throws SQLException {
        newRole = null;
        updatedRole= null;
        pool.releaseConnection(conn);
    }

    @Test
    public void save() throws SQLException {
        newRole = new Role();
        newRole.setName("TestS");

        Integer id = roleDao.save(newRole);

        assertEquals(roleDao.getById(id), newRole);
        assertNotEquals(roleDao.getById(2), newRole);
    }

    @Test
    public void update() throws SQLException {
        updatedRole = roleDao.getById(1);
        Role role = new Role();
        role.setName("Tester");
        updatedRole.setName("Tester");

        Integer id = roleDao.update(updatedRole);

        assertEquals(updatedRole, roleDao.getById(id));
    }

    @Test
    public void getById() throws Exception {
        Role role = roleDao.getById(1);

        assertNotEquals(role, roleDao.getById(2));
    }

    @Test
    public void delete() throws Exception {

        Role role = new Role();
        role.setName("TesterD");

        boolean deleted = roleDao.delete(role.getId());

        assertEquals(true, deleted);
        assertEquals(null, roleDao.getById(role.getId()));
    }


}
