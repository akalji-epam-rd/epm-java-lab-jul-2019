package com.epam.lab.library.dao;


import com.epam.lab.library.connectionpool.ConnectionPool;
import com.epam.lab.library.dao.interfaces.ItemDao;
import com.epam.lab.library.domain.Book;
import com.epam.lab.library.domain.Item;
import com.epam.lab.library.domain.Status;
import com.epam.lab.library.domain.User;
import com.sun.corba.se.spi.ior.IORTemplate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ItemDaoImplTest {

    private ConnectionPool pool = ConnectionPool.getInstance();
    private Connection conn = null;
    private ItemDao itemDao = new ItemDaoImpl();
    private String initializeItemDataSql = "INSERT INTO library.items (book_id, user_id, status_id, date_items) " +
            "VALUES (?, ?, ?, now());";
    private String initializeBookDataSql =
            "INSERT INTO library.books (name, description) " +
                    "VALUES " +
                    "  ('Anna Karenina', 'about Anna Karenina'), " +
                    "  ('Love and Death', 'about Love and Death'), " +
                    "  ('Dogs', 'about dogs');";
    private String initializeUserDataSql =
            " INSERT INTO library.users (email, password, name, lastname) " +
                    "VALUES " +
                    "  ('ya@ya.ru', '12345', 'Vasya', 'Vasilev'), " +
                    "  ('go@google.com', '54789', 'Ivan', 'Ivanov'), " +
                    "  ('vn@yandex.net', 'qwerty', 'Petya', 'Petrovich');";
    private String initializeStatusesDataSql =
            "INSERT INTO library.statuses (name) " +
                    "VALUES " +
                    "('taken'), " +
                    "('not taken'), " +
                    "('reading room')";

    private List<Item> expectedItemList = new ArrayList<>();

    private Item newItem;
    private Item updatedItem;

    @Before
    public void initializeDbData() throws SQLException {
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


        st.execute("DROP TABLE IF EXISTS library.books CASCADE ");
        st.execute("DROP TABLE IF EXISTS library.users CASCADE ");
        st.execute("DROP TABLE IF EXISTS library.items CASCADE ");
        st.execute("DROP TABLE IF EXISTS library.statuses CASCADE ");


        st.execute("CREATE TABLE library.books (  " +
                " id SERIAL,  " +
                "    name VARCHAR(100) NOT NULL,  " +
                "    description VARCHAR,  " +
                "    CONSTRAINT books_pkey PRIMARY KEY (id)  " +
                ")");
        st.execute("CREATE TABLE library.users (  " +
                " id SERIAL,  " +
                "    email VARCHAR(100) NOT NULL,  " +
                "    password VARCHAR(100) NOT NULL,  " +
                "    name VARCHAR(50),  " +
                "    lastname VARCHAR(50),  " +
                " CONSTRAINT users_pkey PRIMARY KEY (id),  " +
                "    CONSTRAINT email_key UNIQUE (email)  " +
                ")");
        st.execute("CREATE TABLE library.statuses(" +
                "    id SERIAL," +
                "    name VARCHAR(50) NOT NULL," +
                "    CONSTRAINT statuses_pkey PRIMARY KEY(id)," +
                "    CONSTRAINT name_statuses_key UNIQUE(name)" +
                ")");
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


        st.execute(initializeBookDataSql);
        st.execute(initializeUserDataSql);
        st.execute(initializeStatusesDataSql);

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
        newItem = null;
        updatedItem = null;
        expectedItemList.clear();
        pool.releaseConnection(conn);
    }


    @Test
    public void save() throws Exception {

        newItem = new Item();
        newItem.setId(4);

        Status status = new Status();
        status.setId(1);
        status.setName("taken");
        newItem.setStatus(status);

        User user = new User(1);
        user.setEmail("ya@ya.ru");
        user.setPassword("12345");
        user.setName("Vasya");
        user.setLastName("Vasilev");
        newItem.setUser(user);

        Book book = new Book(1);
        book.setName("Anna Karenina");
        book.setDescription("about Anna Karenina");
        newItem.setBook(book);

        Integer id = itemDao.save(newItem);

        assertEquals(itemDao.getById(id), newItem);
        assertNotEquals(itemDao.getById(2), newItem);
    }

    @Test
    public void update() throws Exception {

        updatedItem = itemDao.getById(1);
        Book book = new Book(2);
        book.setName("Love and Death");
        book.setDescription("about Love and Death");
        updatedItem.setBook(book);

        Integer id = itemDao.update(updatedItem);

        assertEquals(updatedItem, itemDao.getById(id));

    }

    @Test
    public void delete() throws Exception {

        Item item = new Item();
        item.setId(1);
        boolean deleted = itemDao.delete(item);

        assertEquals(true, deleted);
        assertEquals(null, itemDao.getById(1));

    }

    @Test
    public void getAll() throws Exception {
        List<Item> itemList = itemDao.getAll();

        assertEquals(expectedItemList, itemList);
        assertNotEquals(new ArrayList<Item>(), itemList);
    }

    @Test
    public void getById() throws Exception {

        Item item = itemDao.getById(1);

        assertEquals(item, expectedItemList.get(0));
        assertNotEquals(item, expectedItemList.get(1));

    }


}