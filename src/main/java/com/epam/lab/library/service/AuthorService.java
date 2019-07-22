package com.epam.lab.library.service;

import com.epam.lab.library.dao.AuthorDaoImpl;
import com.epam.lab.library.dao.BookDaoImpl;
import com.epam.lab.library.dao.interfaces.AuthorDao;
import com.epam.lab.library.dao.interfaces.BookDao;
import com.epam.lab.library.domain.Author;
import com.epam.lab.library.domain.Book;
import com.epam.lab.library.util.connectionpool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AuthorService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    ConnectionPool pool = ConnectionPool.getInstance();
    private AuthorDao authorDao = new AuthorDaoImpl();

    /**
     * @return all authors in table
     */
    public List<Author> getAll() {
        try {
            return authorDao.getAll();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * @param id
     * @return author by given id
     */
    public Author getById(int id) {
        try {
            return authorDao.getById(id);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Creates record in table books and respective records in table authors_books
     * @param author
     * @return true if save have been successful and false if not
     */
    public boolean save(Author author) {

        Connection connection = null;
        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);

            Integer authorId = authorDao.save(author);
            String query = "INSERT INTO library.authors_books(author_id, book_id) " +
                    "VALUES(?, ?)";

            if (authorId == null) {
                return false;
            }

            for (Book book : author.getBooks()) {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, authorId);
                statement.setInt(2, book.getId());
                statement.executeQuery();
            }

            return true;
        } catch (SQLException | NullPointerException e) {
            logger.error(e.getMessage(), e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error(e.getMessage(), e);
            }
        } finally {
            pool.releaseConnection(connection);
        }
        return false;
    }

    //TODO Think about how to make it correct
    public boolean update(Author author) {
        try {
            Integer id = authorDao.update(author);
            if (id != null) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    //TODO Implement
    public boolean delete(Book book) {
        return false;
    }
}
