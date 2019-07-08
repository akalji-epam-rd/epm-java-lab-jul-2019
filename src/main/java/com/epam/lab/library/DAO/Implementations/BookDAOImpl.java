package com.epam.lab.library.DAO.Implementations;

import com.epam.lab.library.DAO.Interfaces.BookDAO;
import com.epam.lab.library.connectionpool.ConnectionPool;
import com.epam.lab.library.domain.Book;
import com.epam.lab.library.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookDAOImpl implements BookDAO {

    ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public Book getBook(int id) {

        Connection connection = null;
        try {

            connection = pool.getConnection();
            String query = "SELECT * FROM books " +
                    "INNER JOIN authors_books ON books.id = authors_books.book_id " +
                    "INNER JOIN authors ON authors_books.author_id = authors.id " +
                    "INNER JOIN items ON books.id = items.book_id " +
                    "WHERE books.id=" + id;

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            if (true) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"))
                        .setName(resultSet.getString("name"))
                        .setDescription(resultSet.getString("description"));
                System.out.println(resultSet.getInt("id"));
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
    public List<Book> getBooks(String name) {
        return null;
    }

    @Override
    public void save(Book book) {

    }

    @Override
    public void update(Book book) {

    }

    @Override
    public boolean delete(Book book) {
        return false;
    }

}
