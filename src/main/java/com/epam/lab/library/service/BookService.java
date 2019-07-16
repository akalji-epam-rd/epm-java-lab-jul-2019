package com.epam.lab.library.service;

import com.epam.lab.library.dao.BookDaoImpl;
import com.epam.lab.library.dao.interfaces.BookDao;
import com.epam.lab.library.domain.Book;

import java.util.List;

public class BookService {

    BookDao bookDao = new BookDaoImpl();

    public List<Book> getAllBooks() {
        return null;
    }
}
