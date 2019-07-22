package com.epam.lab.library.dao;

import com.epam.lab.library.util.connectionpool.ConnectionPool;
import com.epam.lab.library.dao.interfaces.BookDao;
import com.epam.lab.library.domain.Author;
import com.epam.lab.library.domain.Book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Book data access object
 */
//TODO put ResultSet in try with resources
public class BookDaoImpl implements BookDao {

    private ConnectionPool pool = ConnectionPool.getInstance();

    /**
     * Get list of books
     *
     * @return list of all books from database
     */
    @Override
    public List<Book> getAll() {

        Connection connection = null;
        try {
            connection = pool.getConnection();

            String query = "SELECT books.id as b_id, books.name as b_name, description as b_description, authors.id as a_id, authors.name as a_name, authors.lastname as a_lastname " +
                    "FROM library.books " +
                    "LEFT JOIN library.authors_books ON books.id = authors_books.book_id " +
                    "LEFT JOIN library.authors ON authors_books.author_id = authors.id";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            List<Book> books = new ArrayList<>();

            Book book = null;
            Set<Author> authors = null;
            Integer prevBookId = null;

            while (resultSet.next()) {
                Integer currentBookId = resultSet.getInt("b_id");

                if (!currentBookId.equals(prevBookId)) {
                    prevBookId = resultSet.getInt("b_id");

                    book = new Book();
                    authors = new HashSet<>();
                    book.setAuthors(authors);
                    books.add(book);

                    book.setId(prevBookId)
                            .setName(resultSet.getString("b_name"))
                            .setDescription(resultSet.getString("b_description"));
                }

                Author author = new Author();
                author.setId(resultSet.getInt("a_id"))
                        .setName(resultSet.getString("a_name"))
                        .setLastName(resultSet.getString("a_lastname"));
                authors.add(author);
            }

            resultSet.close();
            return books;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }
        return null;
    }

    /**
     * Get book by id
     *
     * @param id - identifier of book in database
     * @return Book with given id from database
     */
    @Override
    public Book getById(int id) {

        Connection connection = null;
        try {
            connection = pool.getConnection();

            String query = "SELECT books.id as b_id, books.name as b_name, description as b_description, authors.id as a_id, authors.name as a_name, authors.lastname as a_lastname " +
                    "FROM library.books " +
                    "LEFT JOIN library.authors_books ON books.id = authors_books.book_id " +
                    "LEFT JOIN library.authors ON authors_books.author_id = authors.id " +
                    "WHERE books.id=" + id;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

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
     *
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

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

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
     *
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

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

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
     *
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

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

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
     * Delete all books from table
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
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }
    }
}
