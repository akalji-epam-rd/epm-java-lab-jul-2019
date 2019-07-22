package com.epam.lab.library.dao;


import com.epam.lab.library.dao.interfaces.RoleDao;
import com.epam.lab.library.dao.interfaces.UserDao;


import com.epam.lab.library.domain.Role;

import com.epam.lab.library.domain.User;
import com.epam.lab.library.util.connectionpool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class UserDaoImpl implements UserDao {

    private ConnectionPool pool = ConnectionPool.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(AuthorDaoImpl.class);

    private RoleDao roleDao = new RoleDaoImpl();

    private String selectSql = "SELECT u.id, u.name, u.lastname, u.email, u.password, ro.name AS role_name FROM library.users u " +
            "LEFT JOIN library.users_roles ur ON u.id = ur.user_id " +
            "LEFT JOIN library.roles ro ON ur.role_id = ro.id " +
            "WHERE u.id = ?;";
    private String selectAllSql = "SELECT u.id, u.name, u.lastname, u.email, u.password, ro.name AS role_name, ro.id AS role_id FROM library.users u" +
            "            LEFT JOIN library.users_roles ur ON u.id = ur.user_id" +
            "            LEFT JOIN library.roles ro ON ur.role_id = ro.id;";
    private String insert1Sql = "INSERT INTO library.users (name, lastname, email, password) VALUES (?, ?, ?, ?) RETURNING id;";
    private String insert2Sql = "INSERT INTO library.users_roles (user_id, role_id) VALUES (?,?) RETURNING user_id;";
    private String updateSql = "UPDATE library.users SET name = ?, lastname = ?, email = ?, password = ? WHERE id = ? RETURNING id;";
    private String deleteURSql = "DELETE FROM library.users_roles WHERE user_id = ? RETURNING user_id;";
    private String exist1Sql = "SELECT role_id FROM library.users_roles WHERE user_id = ?;";
    private String exist2Sql = "SELECT user_id FROM library.items WHERE user_id = ?;";
    private String deleteSql = "DELETE from library.users WHERE id = ? RETURNING id;";

    /**
     * Method return user object
     *
     * @param id User id
     * @return User object
     */
    @Override
    public User getById(int id) {


        Connection connection = null;
        try {
            connection = pool.getConnection();
            connection = pool.getConnection();

            PreparedStatement statement = connection.prepareStatement(selectSql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            User user = new User();
            Set<Role> roles = new HashSet<>();

            if (resultSet.next()) {

                user.setId(resultSet.getInt("id"))
                        .setName(resultSet.getString("name"))
                        .setLastName(resultSet.getString("lastname"))
                        .setEmail(resultSet.getString("email"))
                        .setPassword(resultSet.getString("password"));
                do {
                    Role role = new Role();
                    role.setName(resultSet.getString("role_name"));
                    Integer roleId = roleDao.save(role);
                    role.setId(roleId);
                    roles.add(role);
                } while (resultSet.next());
                user.setRoles(roles);
            }
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }
        return null;
    }


    /**
     *
     * @param email
     * @return user with given email
     * @throws SQLException
     */
    @Override
    public User getByEmail(String email) throws SQLException {

        Connection connection = pool.getConnection();

        String query = "SELECT u.id, u.name, u.lastname, u.email, u.password, ro.id AS role_id, ro.name AS role_name " +
                "FROM library.users u " +
                "LEFT JOIN library.users_roles ur ON u.id = ur.user_id " +
                "LEFT JOIN library.roles ro ON ur.role_id = ro.id " +
                "WHERE u.email = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        try (ResultSet resultSet = statement.executeQuery()) {

            if (!resultSet.next()) {
                return null;
            }

            User user = new User();
            user.setId(resultSet.getInt("id"))
                    .setEmail(resultSet.getString("email"))
                    .setPassword(resultSet.getString("password"))
                    .setName(resultSet.getString("name"))
                    .setLastName(resultSet.getString("lastname"));

            Set<Role> roles = new HashSet<>();
            do {
                Role role = new Role();
                role.setId(resultSet.getInt("role_id"))
                        .setName(resultSet.getString("role_name"));
                roles.add(role);
            } while (resultSet.next());
            user.setRoles(roles);

            return user;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            pool.releaseConnection(connection);
        }

        return null;
    }

    /**
     * Method returns collection of users
     *
     * @return users collection
     */
    @Override
    public List<User> getAll() {
        Connection connection = null;
        List<User> list = new ArrayList<>();
        List<Integer> idList = new ArrayList<>();

        try {
            connection = pool.getConnection();
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(selectAllSql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                User user = new User();
                Set<Role> roles = new HashSet<>();

                user.setId(resultSet.getInt("id"))
                        .setName(resultSet.getString("name"))
                        .setLastName(resultSet.getString("lastname"))
                        .setEmail(resultSet.getString("email"))
                        .setPassword(resultSet.getString("password"));

                int id = resultSet.getInt("id");

                Role role = new Role();
                role.setName(resultSet.getString("role_name"));
                role.setId(resultSet.getInt("role_id"));

                if (idList.contains(id)) {
                    for (User localUser : list) {
                        if (localUser.getId() == id) {
                            roles = localUser.getRoles();
                            roles.add(role);
                        }
                    }
                } else {
                    roles.add(role);
                    user.setRoles(roles);
                    idList.add(user.getId());
                    list.add(user);
                }
            }

            if (list.isEmpty()) return null;
            else return list;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }
        return null;
    }

    /**
     * Method save user to DB
     *
     * @param user User for saving
     * @return id of saved user
     */
    @Override
    public Integer save(User user) {
        Connection connection = null;

        String name = user.getName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String password = user.getPassword();
        Set<Role> roles = user.getRoles();

        try {
            connection = pool.getConnection();

            PreparedStatement statement = connection.prepareStatement(insert1Sql);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, password);


            ResultSet resultSet = statement.executeQuery();

            Integer id = null;
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }

            for (Role role : roles) {
                statement = connection.prepareStatement(insert2Sql);
                statement.setInt(1, id);
                statement.setInt(2, role.getId());
                statement.executeQuery();
            }
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }

        return null;
    }

    /**
     * Method updates User with current id
     *
     * @param user User object to update
     * @return id of updated user
     */
    @Override
    public Integer update(User user) {
        Connection connection = null;
        Integer id = user.getId();
        String name = user.getName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String password = user.getPassword();
        Set<Role> roles = user.getRoles();

        try {
            connection = pool.getConnection();

            PreparedStatement statement = connection.prepareStatement(updateSql);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, password);
            statement.setInt(5, id);

            ResultSet resultSet = statement.executeQuery();

            id = null;
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }

            statement = connection.prepareStatement(deleteURSql);
            statement.setInt(1, id);
            statement.executeQuery();

            for (Role role : roles) {
                statement = connection.prepareStatement(insert2Sql);
                statement.setInt(1, id);
                statement.setInt(2, role.getId());

                statement.executeQuery();
            }

            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }
        return null;
    }

    /**
     * Method deletes user with current id in DB
     *
     * @param id ID of user to delete
     * @return Delete result
     */
    @Override
    public boolean delete(int id) {
        Connection connection = null;
        try {
            connection = pool.getConnection();

            PreparedStatement statement = connection.prepareStatement(exist1Sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                statement = connection.prepareStatement(exist2Sql);
                statement.setInt(1, id);
                resultSet = statement.executeQuery();

                if (!resultSet.next()) {
                    statement = connection.prepareStatement(deleteSql);
                    statement.setInt(1, id);
                    statement.executeQuery();
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }
        return false;
    }
}
