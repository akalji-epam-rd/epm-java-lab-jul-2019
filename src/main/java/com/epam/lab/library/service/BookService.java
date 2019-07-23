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

/**
 * Book service layer class
 */
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    ConnectionPool pool = ConnectionPool.getInstance();
    private BookDao bookDao = new BookDaoImpl();
    private AuthorDao authorDao = new AuthorDaoImpl();

    /**
     * @return all books in table
     */
    public List<Book> getAll() {
        try {
            return bookDao.getAll();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * @param id
     * @return book by given id
     */
    public Book getById(int id) {
        try {
            return bookDao.getById(id);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public List<Book> findByNameAndAuthorLastName(String bookName, String authorLastName) {
        try {
            return bookDao.findByNameAndAuthorLastName(bookName, authorLastName);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Creates record in table books and respective records in table authors_books
     *
     * @param book
     * @return true if save have been successful and false if not
     */
    public boolean save(Book book) {

        Connection connection = null;
        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);

            Integer bookId = bookDao.save(book);
            String query = "INSERT INTO library.authors_books(author_id, book_id) " +
                    "VALUES(?, ?)";

            if (bookId == null) {
                return false;
            }

            for (Author author : book.getAuthors()) {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, author.getId());
                statement.setInt(2, bookId);
                try {
                    statement.executeQuery();
                } catch (SQLException e) {
                    logger.info("link already exist");
                }
            }

            connection.commit();
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

    /**
     * updates respective record in table
     * @param book
     * @return true if update have been successful and false if not
     */
    public boolean update(Book book) {
        Connection connection = null;
        try {
            connection = pool.getConnection();
            Book oldBook = bookDao.getById(book.getId());
            Integer bookId = bookDao.update(book);
            if (bookId == null) {
                return false;
            }

            String deleteQuery = "DELETE FROM library.authors_books WHERE author_id=? AND book_id=?";
            String addQuery = "INSERT INTO library.authors_books(author_id, book_id) " +
                    "VALUES(?, ?)";


            for (Author author : oldBook.getAuthors()) {
                PreparedStatement statement = connection.prepareStatement(deleteQuery);
                statement.setInt(1, author.getId());
                statement.setInt(2, oldBook.getId());
                statement.executeQuery();
            }

            if (book.getAuthors() != null) {
                for (Author author : book.getAuthors()) {
                    if (!oldBook.getAuthors().contains(author)) {
                        PreparedStatement statement = connection.prepareStatement(addQuery);
                        statement.setInt(1, author.getId());
                        statement.setInt(2, bookId);
                        statement.executeQuery();
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            if (connection != null) {
                pool.releaseConnection(connection);
            }
        }
    }

    /**
     * Try to delete book without books from database
     * @param book
     * @return true if deletion was completed and false if not
     */
    public boolean delete(Book book) {
        try {
            bookDao.delete(book);
        } catch (SQLException e) {
            logger.error("Forbidden deletion", e);
        }
        return false;
    }
}
