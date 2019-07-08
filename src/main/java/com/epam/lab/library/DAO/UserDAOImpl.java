package com.epam.lab.library.dao;

import com.epam.lab.library.connectionpool.ConnectionPool;
import com.epam.lab.library.dao.Interfaces.UserDao;
import com.epam.lab.library.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public User get(int id) {

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
                User user = new User();
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
    public List<User> getAll() {
        return null;
    }


    @Override
    public void save(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(int id) {

    }
}
