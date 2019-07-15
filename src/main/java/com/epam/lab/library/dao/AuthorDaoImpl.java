package com.epam.lab.library.dao;


import com.epam.lab.library.dao.interfaces.AuthorDao;
import com.epam.lab.library.domain.Author;
import com.epam.lab.library.domain.Book;
import com.epam.lab.library.util.connectionpool.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//TODO put ResultSet in try with resources
public class AuthorDaoImpl implements AuthorDao {

    private ConnectionPool pool = ConnectionPool.getInstance();

    /**
     *
     * @return list of all authors from database
     */
    @Override
    public List<Author> getAll() {
        Connection connection = null;
        try {
            connection = pool.getConnection();

            String query = "SELECT * FROM library.authors";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

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
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }

        return null;
    }

    /**
     *
     * @param id - identifier of author in database
     * @return Book with given id from database
     */
    @Override
    public Author getById(int id) {

        Connection connection = null;
        try {
            connection = pool.getConnection();

            String query = "SELECT authors.id as a_id, authors.name as a_name, authors.lastname as a_lastname, books.id as b_id, books.name as b_name, description as b_description " +
                    "FROM library.authors " +
                    "LEFT JOIN library.authors_books ON authors.id = authors_books.author_id " +
                    "LEFT JOIN library.books ON authors_books.book_id = books.id " +
                    "WHERE authors.id=" + id;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

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
            resultSet.close();
            author.setBooks(books);

            return author;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }

        return null;
    }

    /**
     * Saves given author in database
     * @param author domain model for saving
     * @return id of saved author
     */
    @Override
    public Integer save(Author author) {

        Connection connection = null;

        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);

            String query = "INSERT INTO library.authors(name, lastname) " +
                    "VALUES('" + author.getName() + "', '" + author.getLastName() + "') " +
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
     * Updates given author
     * @param author domain model for updating
     * @return id of saved author if exists or null if not exist
     */
    @Override
    public Integer update(Author author) {

        Connection connection = null;

        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);

            String query = "UPDATE library.authors SET name='" + author.getName() + "', lastname='" + author.getLastName() + "' " +
                    "WHERE id=" + author.getId() + " " +
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
     * Deletes given author from database
     * @param author domain model for deleting
     * @return true if author have been deleted of false if not
     */
    @Override
    public boolean delete(Author author) {

        Connection connection = null;

        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);

            String query = "DELETE FROM library.authors " +
                    "WHERE id=" + author.getId() + " " +
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
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }
    }
}
