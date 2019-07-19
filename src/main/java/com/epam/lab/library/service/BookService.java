package com.epam.lab.library.service;

import com.epam.lab.library.dao.BookDaoImpl;
import com.epam.lab.library.dao.interfaces.BookDao;
import com.epam.lab.library.domain.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class BookService {

    private BookDao bookDao = new BookDaoImpl();
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    public List<Book> getAll() {
        try {
            return bookDao.getAll();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public Book getById(int id) {
        try {
            return bookDao.getById(id);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
