package com.epam.lab.library.dao.interfaces;

import com.epam.lab.library.domain.Book;

import java.util.List;

public interface BookDao {

    List<Book> getAll();

    Book getById(int id);

    Integer save(Book book);

    void update(Book book);

    boolean delete(Book book);

}
