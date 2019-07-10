package com.epam.lab.library.dao;

import com.epam.lab.library.dao.interfaces.UserDao;
import com.epam.lab.library.connectionpool.ConnectionPool;

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

            String query = "SELECT users.id, users.name, users.lastname, users.email, users.password, roles.name FROM library.users " +
                    "LEFT JOIN library.users_roles ON library.users.id = library.users_roles.user_id " +
                    "LEFT JOIN library.roles ON library.users_roles.role_id = library.roles.id " +
                    "WHERE library.users.id = " + id + ";";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"))
                        .setName(resultSet.getString("name"))
                        .setLastName(resultSet.getString("lastname"))
                        .setEmail(resultSet.getString("email"))
                        .setPassword(resultSet.getString("password"));

                return user;

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
    public int save(User user) {
        return 0;
    }

    @Override
    public int update(User user) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }
}
