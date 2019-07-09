package com.epam.lab.library.dao;

import com.epam.lab.library.dao.interfaces.BookDao;
import com.epam.lab.library.connectionpool.ConnectionPool;
import com.epam.lab.library.domain.Author;
import com.epam.lab.library.domain.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookDaoImpl implements BookDao {

    private ConnectionPool pool = ConnectionPool.getInstance();


    @Override
    public List<Book> getAll() {

        Connection connection = null;
        try {
            connection = pool.getConnection();

            String query = "SELECT * FROM books ORDER BY books.name";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

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

    @Override
    public Book getById(int id) {

        Connection connection = null;
        try {
            connection = pool.getConnection();

            String query = "SELECT books.id as id, books.name as bname, description, authors.id as aid, authors.name as aname, authors.lastname as alastname " +
                    "FROM books " +
                    "LEFT JOIN authors_books ON books.id = authors_books.book_id " +
                    "LEFT JOIN authors ON authors_books.author_id = authors.id " +
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

    @Override
    public Integer save(Book book) {

        Connection connection = null;

        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);

            String query = "INSERT INTO books VALUES(" + book.getName() + ", " + book.getDescription() + ")";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                connection.commit();
                int id = resultSet.getInt(1);
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

    @Override
    public void update(Book book) {

        Connection connection = null;

        try {
            connection = pool.getConnection();

            String query = "UPDATE books SET name=" + book.getName() + ", description=" + book.getDescription() + " " +
                    "WHERE id=" + book.getId();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public boolean delete(Book book) {
        Connection connection = null;

        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);

            String query = "DELETE FROM books WHERE id=" + book.getId();
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

}
