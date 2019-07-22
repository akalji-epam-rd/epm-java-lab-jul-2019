package com.epam.lab.library.dao;

import com.epam.lab.library.dao.interfaces.BookDao;
import com.epam.lab.library.domain.Author;
import com.epam.lab.library.domain.Book;
import com.epam.lab.library.util.connectionpool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookDaoImpl implements BookDao {

    private ConnectionPool pool = ConnectionPool.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(AuthorDaoImpl.class);

    /**
     * @return list of all books from database
     */
    @Override
    public List<Book> getAll() throws SQLException {

        Connection connection = pool.getConnection();

        String query = "SELECT books.id as b_id, books.name as b_name, description as b_description, " +
                "authors.id as a_id, authors.name as a_name, authors.lastname as a_lastname " +
                "FROM library.books " +
                "LEFT JOIN library.authors_books ON books.id = authors_books.book_id " +
                "LEFT JOIN library.authors ON authors_books.author_id = authors.id";

        Statement statement = connection.createStatement();
        try (ResultSet resultSet = statement.executeQuery(query)) {

            List<Book> books = new ArrayList<>();
            ArrayList<Integer> identifies = new ArrayList<>();

            while (resultSet.next()) {

                Author author = new Author();
                author.setId(resultSet.getInt("a_id"))
                        .setName(resultSet.getString("a_name"))
                        .setLastName(resultSet.getString("a_lastname"));

                Integer bookId = resultSet.getInt("b_id");

                if (identifies.contains(bookId)) {
                    for (Book book : books) {
                        if (book.getId() == bookId) {
                            book.getAuthors().add(author);
                        }
                    }
                } else {
                    Book book = new Book();
                    book.setId(resultSet.getInt("b_id"))
                            .setName(resultSet.getString("b_name"))
                            .setDescription(resultSet.getString("b_description"));
                    Set<Author> authors = new HashSet<>();
                    authors.add(author);
                    book.setAuthors(authors);
                    books.add(book);
                    identifies.add(bookId);
                }
            }

            return books;

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConnection(connection);
        }
        return null;
    }

    /**
     * @param id - identifier of book in database
     * @return Book with given id from database
     */
    @Override
    public Book getById(int id) throws SQLException {

        Connection connection = pool.getConnection();

        String query = "SELECT books.id as b_id, books.name as b_name, description as b_description, " +
                "authors.id as a_id, authors.name as a_name, authors.lastname as a_lastname " +
                "FROM library.books " +
                "LEFT JOIN library.authors_books ON books.id = authors_books.book_id " +
                "LEFT JOIN library.authors ON authors_books.author_id = authors.id " +
                "WHERE books.id=?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        try (ResultSet resultSet = statement.executeQuery()) {

            if (!resultSet.next()) {
                return null;
            }

            Book book = new Book();
            book.setId(resultSet.getInt("b_id"))
                    .setName(resultSet.getString("b_name"))
                    .setDescription(resultSet.getString("b_description"));

            Set<Author> authors = new HashSet<>();
            do {
                Author author = new Author();
                author.setId(resultSet.getInt("a_id"))
                        .setName(resultSet.getString("a_name"))
                        .setLastName(resultSet.getString("a_lastname"));

                authors.add(author);
            } while (resultSet.next());
            book.setAuthors(authors);

            return book;

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConnection(connection);
        }
        return null;
    }

    /**
     * Saves given book in database
     *
     * @param book domain model for saving
     * @return id of saved book
     */
    @Override
    public Integer save(Book book) throws SQLException {

        Connection connection = pool.getConnection();
        connection.setAutoCommit(false);

        String query = "INSERT INTO library.books(name, description) " +
                "VALUES(?, ?) " +
                "RETURNING id";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, book.getName());
        statement.setString(2, book.getDescription());
        try (ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                connection.commit();
                return id;
            } else {
                connection.rollback();
                return null;
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConnection(connection);
        }
        return null;
    }

    /**
     * Updates given book
     *
     * @param book domain model for updating
     * @return id of saved book if exists or null if not exist
     */
    @Override
    public Integer update(Book book) throws SQLException {

        Connection connection = pool.getConnection();
        connection.setAutoCommit(false);

        String query = "UPDATE library.books SET " +
                "name=? " +
                "description=? " +
                "WHERE id=? " +
                "RETURNING id";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, book.getName());
        statement.setString(2, book.getDescription());
        statement.setInt(3, book.getId());

        try (ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                connection.commit();
                return id;
            } else {
                connection.rollback();
                return null;
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConnection(connection);
        }
        return null;
    }

    /**
     * Deletes given book from database
     *
     * @param book domain model for deleting
     * @return true if book have been deleted of false if not
     */
    @Override
    public boolean delete(Book book) throws SQLException {

        Connection connection = pool.getConnection();
        connection.setAutoCommit(false);

        String query = "DELETE FROM library.books " +
                "WHERE id=? " +
                "RETURNING id";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, book.getId());
        try (ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                connection.commit();
                resultSet.close();
                return true;
            } else {
                connection.rollback();
                resultSet.close();
                return false;
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConnection(connection);
        }
        return false;
    }

    /**
     * Just delete all books from table
     */
    @Override
    public void deleteAll() {

        Connection connection = null;

        try {
            connection = pool.getConnection();
            connection.setAutoCommit(true);

            String query = "DELETE FROM library.books";

            Statement statement = connection.createStatement();
            statement.execute(query);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConnection(connection);
        }
    }
}
