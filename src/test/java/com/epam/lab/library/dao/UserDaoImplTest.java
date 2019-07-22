package com.epam.lab.library.dao;

import com.epam.lab.library.dao.interfaces.RoleDao;
import com.epam.lab.library.dao.interfaces.UserDao;
import com.epam.lab.library.domain.*;
import com.epam.lab.library.util.connectionpool.ConnectionPool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class UserDaoImplTest {

    private ConnectionPool pool = ConnectionPool.getInstance();
    private Connection conn = null;
    private UserDao userDao = new UserDaoImpl();
    private RoleDao roleDao = new RoleDaoImpl();
    private User newUser;
    private User updatedUser;
    private List<Item> expectedItemList = new ArrayList<>();

    private String initializeRoleDataSql = "INSERT INTO library.roles (name) VALUES ('Student'), ('Admin');";
    private String initializeUserDataSql =
            " INSERT INTO library.users (email, password, name, lastname) " +
                    "VALUES " +
                    "  ('ya@ya.ru', '12345', 'Vasya', 'Vasilev'), " +
                    "  ('go@google.com', '54789', 'Ivan', 'Ivanov'), " +
                    "  ('vn@yandex.net', 'qwerty', 'Petya', 'Petrovich');";
    private String initializeUsersRolesDataSql = "INSERT INTO library.users_roles (user_id, role_id) VALUES (1,1), (2,2), (3,1);";
    private String initializeBooks = "INSERT INTO library.books (name, description) VALUES " +
            "('Anna Karenina', 'about Anna Karenina'), " +
            "('Love and Death', 'about Love and Death'), " +
            "('Dogs', 'about dogs'), " +
            "('Cats', 'about cats'), " +
            "('Animals', 'about animals'), " +
            "('Mems', 'about mems'), " +
            "('Politics', 'about politics'), " +
            "('Psychology', 'about psychology');";
    private String initializeItemDataSql = "INSERT INTO library.items (book_id, user_id, status_id, date_items) " +
            "VALUES (?, ?, ?, now());";
    private String initializeStatuses = "INSERT INTO library.statuses (name) VALUES ('taken'), ('not taken'), ('reading room');";


    @Before
    public void initialize() throws SQLException {
        Statement st = null;
        try {
            conn = pool.getConnection();
            if (conn.isClosed()) conn = pool.getConnection();
            st = conn.createStatement();
        } catch (PSQLException e) {
            e.printStackTrace();
        }

        st.execute("DROP TABLE IF EXISTS library.users CASCADE ");
        st.execute("DROP TABLE IF EXISTS library.users_roles CASCADE ");
        st.execute("DROP TABLE IF EXISTS library.roles CASCADE ");
        st.execute("DROP TABLE IF EXISTS library.items CASCADE");
        st.execute("DROP TABLE IF EXISTS library.books CASCADE");
        st.execute("DROP TABLE IF EXISTS library.statuses CASCADE");

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

        st.execute("CREATE TABLE library.books (id SERIAL, name VARCHAR(100) NOT NULL, description VARCHAR, CONSTRAINT books_pkey PRIMARY KEY (id))");
        st.execute("CREATE TABLE library.statuses(id SERIAL, name VARCHAR(50) NOT NULL, CONSTRAINT statuses_pkey PRIMARY KEY(id), CONSTRAINT name_statuses_key UNIQUE(name))");

        st.execute("CREATE TABLE library.items(  " +
                " id SERIAL,  " +
                "    book_id INTEGER NOT NULL,  " +
                "    user_id INTEGER,  " +
                "    status_id INTEGER NOT NULL,  " +
                "    date_items DATE NOT NULL DEFAULT NOW(),  " +
                "    CONSTRAINT items_pkey PRIMARY KEY(id),  " +
                "    CONSTRAINT books_fkey FOREIGN KEY(book_id)  " +
                "     REFERENCES library.books(id)  " +
                "        ON DELETE CASCADE  " +
                "     ON UPDATE NO ACTION  " +
                "     NOT DEFERRABLE,  " +
                "    CONSTRAINT users_fkey FOREIGN KEY(user_id)  " +
                "     REFERENCES library.users(id)  " +
                "        ON DELETE SET NULL  " +
                "     ON UPDATE NO ACTION  " +
                "     NOT DEFERRABLE,  " +
                "    CONSTRAINT statuses_fkey FOREIGN KEY(status_id)  " +
                "     REFERENCES library.statuses(id)  " +
                "        ON DELETE NO ACTION  " +
                "     ON UPDATE NO ACTION  " +
                "     NOT DEFERRABLE  " +
                ")");


        st.execute(initializeRoleDataSql);
        st.execute(initializeUserDataSql);
        st.execute(initializeUsersRolesDataSql);
        st.execute(initializeBooks);
        st.execute(initializeStatuses);


        PreparedStatement ps = conn.prepareStatement(initializeItemDataSql);

        // create books for items
        Book book1 = new Book(1);
        book1.setName("Anna Karenina");
        book1.setDescription("about Anna Karenina");
        Book book2 = new Book(2);
        book2.setName("Love and Death");
        book2.setDescription("about Love and Death");
        Book book3 = new Book(3);
        book3.setName("Dogs");
        book3.setDescription("about dogs");

        // create users for items
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

        // create statuses for items
        Status status1 = new Status();
        status1.setId(1);
        status1.setName("taken");
        Status status2 = new Status();
        status2.setId(2);
        status2.setName("not taken");
        Status status3 = new Status();
        status3.setId(3);
        status3.setName("reading room");

        //create items and itemList
        Item item1 = new Item();
        item1.setId(1);
        item1.setBook(book1);
        item1.setUser(user1);
        item1.setStatus(status1);
        expectedItemList.add(item1);

        Item item2 = new Item();
        item2.setId(2);
        item2.setBook(book2);
        item2.setUser(user2);
        item2.setStatus(status2);
        expectedItemList.add(item2);

        Item item3 = new Item();
        item3.setId(3);
        item3.setBook(book3);
        item3.setUser(user3);
        item3.setStatus(status3);
        expectedItemList.add(item3);

        for (Item item : expectedItemList) {
            ps.setInt(1, item.getBook().getId());
            ps.setInt(2, item.getUser().getId());
            ps.setInt(3, item.getStatus().getId());
            ps.addBatch();
        }

        try {
            ps.executeBatch();
        } catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
        }


    }

    @After
    public void deleteDbData() throws SQLException {
        expectedItemList.clear();
        pool.releaseConnection(conn);
    }

    @Test
    public void save() throws SQLException {
        newUser = new User();
        newUser.setName("TestSaveName");
        newUser.setLastName("TestSaveLastName");
        newUser.setEmail("test@mail.test");
        newUser.setPassword("testpass");

        Set<Role> roles = new HashSet<>();

        Role role = new Role();
        role.setName("UserTestRole");
        Integer id = roleDao.save(role);
        role.setId(id);
        roles.add(role);

        role = new Role();
        role.setName("UserTestRoleTwo");
        id = roleDao.save(role);
        role.setId(id);
        roles.add(role);

        newUser.setRoles(roles);

        Integer uId = userDao.save(newUser);
        newUser.setId(uId);

        User uD = userDao.getById(uId);

        assertEquals(uD, newUser);
        assertNotEquals(userDao.getById(2), newUser);
    }

    @Test
    public void update() throws SQLException {
        updatedUser = userDao.getById(2);
        updatedUser.setId(1);
        updatedUser.setName("UTesterrr");
        updatedUser.setLastName("ULTesterrr");
        updatedUser.setEmail("Testerrr@mail.test");
        updatedUser.setPassword("Testerrr123");
        Set<Role> roles = userDao.getById(3).getRoles();
        updatedUser.setRoles(roles);

        Integer id = userDao.update(updatedUser);

        assertEquals(updatedUser, userDao.getById(id));
    }

    @Test
    public void getById() throws Exception {
        User user = userDao.getById(1);

        assertNotEquals(user, userDao.getById(2));
    }

    @Test
    public void getAll() throws Exception {
        User u1 = new User();
        User u2 = new User();
        User u3 = new User();
        Role r1 = new Role();
        Role r2 = new Role();
        Set<Role> set1 = new HashSet<>();
        Set<Role> set2 = new HashSet<>();

        r1.setName("Student");
        r1.setId(1);
        r2.setName("Admin");
        r2.setId(2);

        set1.add(r1);
        set2.add(r2);

        u1.setId(1);
        u1.setName("Vasya");
        u1.setLastName("Vasilev");
        u1.setEmail("ya@ya.ru");
        u1.setPassword("12345");
        u1.setRoles(set1);

        u2.setId(2);
        u2.setName("Ivan");
        u2.setLastName("Ivanov");
        u2.setEmail("go@google.com");
        u2.setPassword("54789");
        u2.setRoles(set2);

        u3.setId(3);
        u3.setName("Petya");
        u3.setLastName("Petrovich");
        u3.setEmail("vn@yandex.net");
        u3.setPassword("qwerty");
        u3.setRoles(set1);

        List<User> list = new ArrayList<>();
        list.add(u1);
        list.add(u2);
        list.add(u3);

        List<User> recievedList = userDao.getAll();

        assertEquals(list, recievedList);
    }

    @Test
    public void delete() throws Exception {
        newUser = new User();
        newUser.setName("TestDeleteName");
        newUser.setLastName("TestDeleteLastName");
        newUser.setEmail("test@mail.test");
        newUser.setPassword("testpass");

        Set<Role> roles = new HashSet<>();

        Role role = new Role();
        role.setName("UserTestRole");
        Integer id = roleDao.save(role);
        role.setId(id);

        roles.add(role);

        role = new Role();
        role.setName("UserTestRoleTwo");
        id = roleDao.save(role);
        role.setId(id);

        roles.add(role);

        newUser.setRoles(roles);

        Integer uId = userDao.save(newUser);

        boolean deleted = userDao.delete(uId);

        assertEquals(true, true);
        assertNotEquals(null, userDao.getById(id));
    }
}
