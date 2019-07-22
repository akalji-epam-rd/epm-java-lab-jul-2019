package com.epam.lab.library.dao.interfaces;

import com.epam.lab.library.domain.Author;

import java.sql.SQLException;
import java.util.List;

/**
 * Author data access object
 */
public interface AuthorDao {

    List<Author> getAll() throws SQLException;

    Author getById(int id) throws SQLException;

    Integer save(Author author) throws SQLException;

    Integer update(Author author) throws SQLException;

    boolean delete(Author author) throws SQLException;

    @Deprecated
    void deleteAll();
}
