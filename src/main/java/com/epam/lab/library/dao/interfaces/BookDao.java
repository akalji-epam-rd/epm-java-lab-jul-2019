package com.epam.lab.library.dao.interfaces;

import com.epam.lab.library.domain.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookDao {

    List<Book> getAll() throws SQLException;

    Book getById(int id) throws SQLException;

    Integer save(Book book) throws SQLException;

    Integer update(Book book) throws SQLException;

    boolean delete(Book book) throws SQLException;

    @Deprecated
    void deleteAll();
}
