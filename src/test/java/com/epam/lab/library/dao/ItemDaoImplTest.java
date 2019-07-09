package com.epam.lab.library.dao;

import com.epam.lab.library.connectionpool.ConnectionPool;
import com.epam.lab.library.dao.interfaces.ItemDao;
import com.epam.lab.library.domain.Book;
import com.epam.lab.library.domain.Item;
import com.epam.lab.library.domain.Status;
import com.epam.lab.library.domain.User;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.Date;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class ItemDaoImplTest {

    private Item itemExpected = new Item();

    @Before
    public void initialize() throws Exception {

        Book book = new Book(1);
        book.setName("Dogs");
        book.setDescription("about dogs");
        User user = new User(1);
        user.setEmail("ya@yahoo.com");
        user.setPassword("qwerty");
        user.setName("Petya");
        user.setLastname("Petrovich");
        Status status = new Status();
        status.setId(1);
        status.setName("taken");
        itemExpected.setStatus(status);
        itemExpected.setBook(book);
        itemExpected.setUser(user);
        itemExpected.setDate(new Date());

    }


    @Test
    public void save() throws Exception {

        ItemDao itemDao = new ItemDaoImpl();

        Integer id = itemDao.save(itemExpected);
        itemExpected.setId(id);

        assertEquals(itemExpected, itemDao.getById(id));
        assertNotEquals(itemExpected, itemDao.getById(3));

    }

    @Test
    public void update() throws Exception {

        ItemDao itemDao = new ItemDaoImpl();
        itemExpected.setId(2);
        itemDao.update(itemExpected);

        assertEquals(itemExpected, itemDao.getById(2));
        itemExpected.setStatus(new Status());
        assertNotEquals(itemExpected, itemDao.getById(2));


    }

    @Test
    public void getById() throws Exception {

        ItemDao itemDao = new ItemDaoImpl();

        itemExpected.setId(1);
        assertEquals(itemExpected, itemDao.getById(1));
        assertNotEquals(itemExpected, itemDao.getById(3));

    }

    @Test
    public void delete() throws Exception {

        ItemDao itemDao = new ItemDaoImpl();

        List<Item> itemList = itemDao.getAll();

        if (!itemList.isEmpty()) {
            itemDao.delete(itemList.get(0));
            assertEquals(itemDao.getById(itemList.get(0).getId()), null);
            assertNotEquals(itemDao.getById(itemList.get(0).getId()), itemList.get(0));
        }

    }

}