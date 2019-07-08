package com.epam.lab.library.dao.interfaces;

import com.epam.lab.library.domain.Book;

import java.util.List;

public interface BookDao {

    Book getById(int id);

    List<Book> getAllByName(String name);

    Integer save(Book book);

    void update(Book book);

    boolean delete(Book book);

}
