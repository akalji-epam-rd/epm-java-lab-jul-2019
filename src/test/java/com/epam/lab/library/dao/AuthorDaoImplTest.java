package com.epam.lab.library.dao;

import com.epam.lab.library.dao.interfaces.AuthorDao;
import com.epam.lab.library.domain.Author;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AuthorDaoImplTest {

    private AuthorDao authorDao = new AuthorDaoImpl();

    @Test
    public void saveAndGetById() throws SQLException {
        authorDao.deleteAll();

        Author author1 = new Author().setName("Lev").setLastName("Tolstoy");
        Author author2 = new Author().setName("Alex").setLastName("Pushkin");
        Author author3 = new Author().setName("Nikolai").setLastName("Gogol");

        Integer id;
        id = authorDao.save(author1);
        author1.setId(id);
        assertEquals(author1, authorDao.getById(id));

        id = authorDao.save(author2);
        author2.setId(id);
        assertEquals(author2, authorDao.getById(id));

        id = authorDao.save(author3);
        author3.setId(id);
        assertEquals(author3, authorDao.getById(id));
    }

    @Test
    public void update() throws SQLException {
        authorDao.deleteAll();

        Author author;
        Integer id;

        author = new Author().setName("Lev").setLastName("Tolstoy");
        id = authorDao.save(author);
        author.setId(id).setLastName("Disrespects dots").setName("Old Guy");
        authorDao.update(author);
        assertEquals(author, authorDao.getById(id));

        author = new Author().setName("Alex").setLastName("Pushkin");
        id = authorDao.save(author);
        author.setId(id).setName("Dantes");
        authorDao.update(author);
        assertEquals(author, authorDao.getById(id));

        author = new Author().setName("Nikolai").setLastName("Gogol");
        id = authorDao.save(author);
        author.setId(id).setName("Pretty").setLastName("Bonfire");
        authorDao.update(author);
        assertEquals(author, authorDao.getById(id));
    }
}