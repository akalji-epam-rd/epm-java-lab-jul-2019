package com.epam.lab.library.DAO.Interfaces;

import com.epam.lab.library.domain.Book;

import java.util.List;

public interface BookDAO {

    Book getBook(int id);

    List<Book> getBooks(String name);

    void save(Book book);

    void update(Book book);

    boolean delete(Book book);

}
