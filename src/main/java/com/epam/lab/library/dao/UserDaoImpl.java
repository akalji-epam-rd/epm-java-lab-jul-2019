package com.epam.lab.library.dao;

import com.epam.lab.library.dao.interfaces.RoleDao;
import com.epam.lab.library.dao.interfaces.UserDao;
import com.epam.lab.library.domain.Role;
import com.epam.lab.library.domain.User;
import com.epam.lab.library.util.connectionpool.ConnectionPool;
import com.epam.lab.library.util.pagination.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User data access object
 */
public class UserDaoImpl implements UserDao {

    private static final Logger LOG = LoggerFactory.getLogger(ItemDaoImpl.class);
    private ConnectionPool pool = ConnectionPool.getInstance();
    private RoleDao roleDao = new RoleDaoImpl();

    private String selectSql = "SELECT u.id, u.name, u.lastname, u.email, u.password, ro.name AS role_name FROM library.users u " +
            "LEFT JOIN library.users_roles ur ON u.id = ur.user_id " +
            "LEFT JOIN library.roles ro ON ur.role_id = ro.id " +
            "WHERE u.id = ?;";
    private String selectAllSql = "SELECT u.id, u.name, u.lastname, u.email, u.password, ro.name AS role_name, ro.id AS role_id FROM library.users u" +
            "            LEFT JOIN library.users_roles ur ON u.id = ur.user_id" +
            "            LEFT JOIN library.roles ro ON ur.role_id = ro.id";
    private String insert1Sql = "INSERT INTO library.users (name, lastname, email, password) VALUES (?, ?, ?, ?) RETURNING id;";
    private String insert2Sql = "INSERT INTO library.users_roles (user_id, role_id) VALUES (?,?) RETURNING user_id;";
    private String updateSql = "UPDATE library.users SET name = ?, lastname = ?, email = ?, password = ? WHERE id = ? RETURNING id;";
    private String deleteURSql = "DELETE FROM library.users_roles WHERE user_id = ? RETURNING user_id;";
    private String updateItem = "UPDATE library.items SET status_id = 1, user_id = null WHERE user_id = ? RETURNING user_id;";
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
            LOG.error(e.getMessage());
        } finally {
            pool.releaseConnection(connection);
        }
        return null;
    }

    /**
     * Return list of users with default pagination
     *
     * @return list of items with filter and pagination
     */
    public List<User> getAll() {
        Paging paging = new Paging().setPageNumber(1);
        return getAll(paging);
    }

    /**
     * Method returns collection of users
     *
     * @param paging - pagination settings
     * @return users collection
     */
    @Override
    public List<User> getAll(Paging paging) {
        Connection connection = null;
        List<User> list = new ArrayList<>();
        List<Integer> idList = new ArrayList<>();

        try {
            connection = pool.getConnection();
            connection = pool.getConnection();
            String paginationSql = selectAllSql + appendPagination(paging);
            PreparedStatement statement = connection.prepareStatement(paginationSql);

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
            LOG.error(e.getMessage());
        } finally {
            pool.releaseConnection(connection);
        }
        return list;
    }

    /**
     * Method save user to DB
     *
     * @param user User for saving
     * @return id of saved user
     * @throws SQLException - if something wrong with connection or
     *                      executing query
     */
    @Override
    public Integer save(User user) throws SQLException {

        Connection connection = null;
        Integer id = null;
        String name = user.getName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String password = user.getPassword();
        Set<Role> roles = user.getRoles();

        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(insert1Sql);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, password);

            ResultSet resultSet = statement.executeQuery();

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
            LOG.error(e.getMessage());
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            pool.releaseConnection(connection);
        }
        return id;
    }

    /**
     * Method updates User with current id
     *
     * @param user User object to update
     * @return id of updated user
     * @throws SQLException - if something wrong with connection or
     *                      *          executing query
     */
    @Override
    public Integer update(User user) throws SQLException {

        Connection connection = null;
        Integer id = user.getId();
        String name = user.getName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String password = user.getPassword();
        Set<Role> roles = user.getRoles();

        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);

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
            LOG.error(e.getMessage());
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            pool.releaseConnection(connection);
        }
        return null;
    }

    /**
     * Method deletes user with current id in DB
     *
     * @param id ID of user to delete
     * @return Delete result
     * @throws SQLException - if something wrong with connection or
     *                      executing query
     */
    @Override
    public boolean delete(int id) throws SQLException {
        Connection connection = null;
        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(deleteURSql);
            statement.setInt(1, id);
            statement.executeQuery();

            statement = connection.prepareStatement(updateItem);
            statement.setInt(1, id);
            statement.executeQuery();

            statement = connection.prepareStatement(deleteSql);
            statement.setInt(1, id);
            statement.executeQuery();
            return true;

        } catch (SQLException e) {
            LOG.error(e.getMessage());
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            pool.releaseConnection(connection);
        }
        return false;
    }

    private String appendPagination(Paging paging) {

        if (paging == null) {
            return "";
        }

        int limit = paging.getCountPerPage();

        int start = paging.getPageNumber() * paging.getCountPerPage() - 10;

        // ORDER BY added to query because of using OFFSET and LIMIT make strange returning query
        return " ORDER BY u.id ASC OFFSET " + start + " LIMIT " + limit + ";";
    }

    /**
     * return number of queried rows from table
     *
     * @return number of queried rows from table
     * @throws SQLException - if something wrong with connection or
     *                      executing query
     */
    @Override
    public Integer getTotal() throws SQLException {

        Connection conn = pool.getConnection();
        Statement st = conn.createStatement();

        String sql = "SELECT COUNT(u.id) FROM library.users u WHERE 1=1 ";
        int total = 0;

        try (ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                total = rs.getInt("count");
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        } finally {
            pool.releaseConnection(conn);
        }
        return total;
    }
}
