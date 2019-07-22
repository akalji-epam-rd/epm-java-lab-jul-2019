package com.epam.lab.library.dao.interfaces;

import com.epam.lab.library.domain.Author;

import java.util.List;

/**
 * Author data access object
 */
public interface AuthorDao {

    List<Author> getAll();

    Author getById(int id);

    Integer save(Author author);

    Integer update(Author author);

    boolean delete(Author author);

    @Deprecated
    void deleteAll();
}
