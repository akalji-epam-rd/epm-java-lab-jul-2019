package com.epam.lab.library.dao;


import com.epam.lab.library.dao.interfaces.AuthorDao;
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

public class AuthorDaoImpl implements AuthorDao {

    private ConnectionPool pool = ConnectionPool.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(AuthorDaoImpl.class);

    /**
     * @return list of all authors from database
     */
    @Override
    public List<Author> getAll() throws SQLException {
        Connection connection = pool.getConnection();

        String query = "SELECT id, name, lastname FROM library.authors";
        Statement statement = connection.createStatement();

        try (ResultSet resultSet = statement.executeQuery(query)) {
            List<Author> authors = new ArrayList<>();

            while (resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getInt("id"))
                        .setName(resultSet.getString("name"))
                        .setLastName(resultSet.getString("lastname"));
                authors.add(author);
            }

            return authors;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConnection(connection);
        }
        return null;
    }

    /**
     * @param id - identifier of author in database
     * @return Book with given id from database
     */
    @Override
    public Author getById(int id) throws SQLException {

        Connection connection = pool.getConnection();

        String query = "SELECT authors.id as a_id, authors.name as a_name, authors.lastname as a_lastname, books.id as b_id, books.name as b_name, description as b_description " +
                "FROM library.authors " +
                "LEFT JOIN library.authors_books ON authors.id = authors_books.author_id " +
                "LEFT JOIN library.books ON authors_books.book_id = books.id " +
                "WHERE authors.id=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        try (ResultSet resultSet = statement.executeQuery()) {

            if (!resultSet.next()) {
                return null;
            }

            Author author = new Author();
            author.setId(resultSet.getInt("a_id"))
                    .setName(resultSet.getString("a_name"))
                    .setLastName(resultSet.getString("a_lastname"));

            Set<Book> books = new HashSet<>();
            do {
                Book book = new Book();
                book.setId(resultSet.getInt("b_id"))
                        .setName(resultSet.getString("b_name"))
                        .setDescription(resultSet.getString("b_description"));

                books.add(book);
            } while (resultSet.next());
            author.setBooks(books);
            return author;

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConnection(connection);
        }
        return null;
    }

    /**
     * Saves given author in database
     *
     * @param author domain model for saving
     * @return id of saved author
     */
    @Override
    public Integer save(Author author) throws SQLException {

        Connection connection = pool.getConnection();
        connection.setAutoCommit(false);

        String query = "INSERT INTO library.authors(name, lastname) " +
                "VALUES(?, ?) RETURNING id";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, author.getName());
        statement.setString(2, author.getLastName());
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
     * Updates given author
     *
     * @param author domain model for updating
     * @return id of saved author if exists or null if not exist
     */
    @Override
    public Integer update(Author author) throws SQLException {

        Connection connection = pool.getConnection();
        connection.setAutoCommit(false);

        String query = "UPDATE library.authors SET " +
                "name=?, " +
                "lastname=? " +
                "WHERE id=?" +
                "RETURNING id";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, author.getName());
        statement.setString(2, author.getLastName());
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
     * Deletes given author from database
     *
     * @param author domain model for deleting
     * @return true if author have been deleted of false if not
     */
    @Override
    public boolean delete(Author author) throws SQLException {

        Connection connection = pool.getConnection();
        connection.setAutoCommit(false);

        String query = "DELETE FROM library.authors " +
                "WHERE id=? " +
                "RETURNING id";

        PreparedStatement statement = connection.prepareStatement(query);
        try (ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
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

            String query = "DELETE FROM library.authors";

            Statement statement = connection.createStatement();
            statement.execute(query);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConnection(connection);
        }
    }
}
