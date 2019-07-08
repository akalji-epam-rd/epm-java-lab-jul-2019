package com.epam.lab.library.dao;

import com.epam.lab.library.dao.interfaces.BookDao;
import com.epam.lab.library.connectionpool.ConnectionPool;
import com.epam.lab.library.domain.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookDaoImpl implements BookDao {

    private ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public Book getById(int id) {

        Connection connection = null;
        try {
            connection = pool.getConnection();

            String query = "SELECT books.id books.name books.description FROM books " +
                    "INNER JOIN authors_books ON books.id = authors_books.book_id " +
                    "INNER JOIN authors ON authors_books.author_id = authors.id " +
                    "WHERE books.id=" + id;

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            if (true) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"))
                        .setName(resultSet.getString("name"))
                        .setDescription(resultSet.getString("description"));
                return book;
            } else {
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
    public List<Book> getAllByName(String name) {
        return null;
    }

    @Override
    public Integer save(Book book) {
        return null;
    }

    @Override
    public void update(Book book) {

    }

    @Override
    public boolean delete(Book book) {
        return false;
    }

}
