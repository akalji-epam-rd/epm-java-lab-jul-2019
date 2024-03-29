package com.epam.lab.library.dao;

import com.epam.lab.library.dao.interfaces.BookDao;
import com.epam.lab.library.domain.Book;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BookDaoImplTest {

    private BookDao bookDao = new BookDaoImpl();


    @Test
    public void saveAndGetById() throws SQLException {
        bookDao.deleteAll();

        Book book1 = new Book().setName("Anna Karenina").setDescription("Death under the train");
        Book book2 = new Book().setName("Crime and Punishment").setDescription("Death from the axe");
        Book book3 = new Book().setName("War and Peace").setDescription("Many deaths");

        Integer id;
        id = bookDao.save(book1);
        book1.setId(id);
        assertEquals(book1, bookDao.getById(id));

        id = bookDao.save(book2);
        book2.setId(id);
        assertEquals(book2, bookDao.getById(id));

        id = bookDao.save(book3);
        book3.setId(id);
        assertEquals(book3, bookDao.getById(id));
    }

    @Test
    public void update() throws SQLException {
        bookDao.deleteAll();

        Book book;
        Integer id;

        book = new Book().setName("Anna Karenina").setDescription("Death under the train");
        id = bookDao.save(book);
        book.setId(id).setDescription("Sad story").setName("Foken Train");
        bookDao.update(book);
        assertEquals(book, bookDao.getById(id));

        book = new Book().setName("Crime and Punishment").setDescription("Death from the axe");
        id = bookDao.save(book);
        book.setId(id).setDescription("Someone call the police").setName("Dead Granny");
        bookDao.update(book);
        assertEquals(book, bookDao.getById(id));

        book = new Book().setName("War and Peace").setDescription("Many deaths");
        id = bookDao.save(book);
        book.setId(id).setDescription("Long story").setName("Very long story");
        bookDao.update(book);
        assertEquals(book, bookDao.getById(id));
    }

    @Test
    public void delete() throws SQLException {
        bookDao.deleteAll();

        Book book1 = new Book().setName("Anna Karenina").setDescription("Death under the train");
        Book book2 = new Book().setName("Crime and Punishment").setDescription("Death from the axe");
        Book book3 = new Book().setName("War and Peace").setDescription("Many deaths");

        book1.setId(bookDao.save(book1));
        book2.setId(bookDao.save(book2));
        book3.setId(bookDao.save(book3));

        assertTrue(bookDao.delete(book1));
        assertTrue(bookDao.delete(book2));
        assertTrue(bookDao.delete(book3));
    }
}