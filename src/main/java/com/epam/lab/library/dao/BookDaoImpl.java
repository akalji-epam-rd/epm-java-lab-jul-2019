package com.epam.lab.library.dao;

import com.epam.lab.library.util.connectionpool.ConnectionPool;
import com.epam.lab.library.dao.interfaces.BookDao;
import com.epam.lab.library.domain.Author;
import com.epam.lab.library.domain.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookDaoImpl implements BookDao {

    private ConnectionPool pool = ConnectionPool.getInstance();

    /**
     *
     * @return List of all books from database
     */
    @Override
    public List<Book> getAll() {

        Connection connection = null;
        try {
            connection = pool.getConnection();

            String query = "SELECT * FROM library.books " +
                    "ORDER BY books.name";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"))
                        .setName(resultSet.getString("name"))
                        .setDescription(resultSet.getString("description"));
                books.add(book);
            }
            return books;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }

        return null;
    }

    /**
     *
     * @param id - identifier of book in database
     * @return Book with given id from database
     */
    @Override
    public Book getById(int id) {

        Connection connection = null;
        try {
            connection = pool.getConnection();

            String query = "SELECT books.id as id, books.name as bname, description, authors.id as aid, authors.name as aname, authors.lastname as alastname " +
                    "FROM library.books " +
                    "LEFT JOIN library.authors_books ON books.id = authors_books.book_id " +
                    "LEFT JOIN library.authors ON authors_books.author_id = authors.id " +
                    "WHERE books.id=" + id;

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            Book book = new Book();
            book.setId(resultSet.getInt("id"))
                    .setName(resultSet.getString("bname"))
                    .setDescription(resultSet.getString("description"));

            Set<Author> authors = new HashSet<>();
            do {
                Author author = new Author();
                author.setId(resultSet.getInt("aid"))
                        .setName(resultSet.getString("aname"))
                        .setLastName(resultSet.getString("alastname"));

                authors.add(author);
            } while (resultSet.next());
            resultSet.close();
            book.setAuthors(authors);

            return book;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }

        return null;
    }

    /**
     * Saves given book in database
     * @param book domain model for saving
     * @return id of saved book
     */
    @Override
    public Integer save(Book book) {

        Connection connection = null;

        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);

            String query = "INSERT INTO library.books(name, description) " +
                    "VALUES('" + book.getName() + "', '" + book.getDescription() + "') " +
                    "RETURNING id";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                connection.commit();
                resultSet.close();
                return id;
            } else {
                connection.rollback();
                resultSet.close();
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }
        return null;
    }

    /**
     * Updates given book
     * @param book domain model for updating
     * @return id of saved book if exists or null if not exist
     */
    @Override
    public Integer update(Book book) {

        Connection connection = null;

        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);

            String query = "UPDATE library.books SET name='" + book.getName() + "', description='" + book.getDescription() + "' " +
                    "WHERE id=" + book.getId() + " " +
                    "RETURNING id";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                connection.commit();
                resultSet.close();
                return id;
            } else {
                connection.rollback();
                resultSet.close();
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }
        return null;
    }

    /**
     * Deletes given book from database
     * @param book domain model for deleting
     * @return true if book have been deleted of false if not
     */
    @Override
    public boolean delete(Book book) {
        Connection connection = null;

        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);

            String query = "DELETE FROM library.books " +
                    "WHERE id=" + book.getId() + " " +
                    "RETURNING id";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

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
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }
        return false;
    }

    /**
     * Just delete all books
     */
    @Override
    public void deleteAll() {
        Connection connection = null;

        try {
            connection = pool.getConnection();
            connection.setAutoCommit(true);

            String query = "DELETE FROM library.books";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }
    }
}
