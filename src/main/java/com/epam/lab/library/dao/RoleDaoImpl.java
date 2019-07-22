package com.epam.lab.library.dao;

import com.epam.lab.library.dao.interfaces.RoleDao;
import com.epam.lab.library.domain.Role;
import com.epam.lab.library.util.connectionpool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Users roles data access object
 */
public class RoleDaoImpl implements RoleDao {

    private static final Logger LOG = LoggerFactory.getLogger(ItemDaoImpl.class);
    private ConnectionPool pool = ConnectionPool.getInstance();

    private String selectSql = "SELECT roles.id, roles.name FROM library.roles WHERE roles.id = ?;";
    private String selectAllSql = "SELECT * FROM library.roles;";
    private String insertSql = "INSERT INTO library.roles (name) VALUES (?) RETURNING id;";
    private String updateSql = "UPDATE library.roles SET name = ? WHERE id = ?;";
    private String existSql = "SELECT role_id FROM library.users_roles WHERE role_id = ?;";
    private String deleteSql = "DELETE from library.roles WHERE id = ? RETURNING id;";

    /**
     * Method return role object
     *
     * @param id Role id
     * @return Role object
     * @throws SQLException - if something wrong with connection or
     *                      executing query
     */
    @Override
    public Role getById(int id) throws SQLException {
        Connection connection = null;

        try {
            connection = pool.getConnection();
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(selectSql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt("id"))
                        .setName(resultSet.getString("name"));
                return role;
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        } finally {
            pool.releaseConnection(connection);
        }
        return null;
    }

    /**
     * Method returns collection of roles
     *
     * @return roles collection
     * @throws SQLException - if something wrong with connection or
     *                      *          executing query
     */
    @Override
    public List<Role> getAll() throws SQLException {

        Connection connection = null;
        List<Role> list = new ArrayList<>();

        try {
            connection = pool.getConnection();
            connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(selectAllSql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt("id"))
                        .setName(resultSet.getString("name"));
                list.add(role);
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
     * Method save role to DB
     *
     * @param role Role for saving
     * @return id of saved role
     * @throws SQLException - if something wrong with connection or
     *                      executing query
     */
    @Override
    public Integer save(Role role) throws SQLException {

        Integer id = null;
        Connection connection = null;
        String name = role.getName();

        try {
            List<Role> list = getAll();
            for (Role readyRole : list) {
                String readyRoleName = readyRole.getName();
                if (readyRoleName.equals(name)) {
                    return readyRole.getId();
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }

        try {

            connection = pool.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(insertSql);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt("id");
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
     * Method updates role with current id
     *
     * @param role Role object to update
     * @return id of updated role
     * @throws SQLException - if something wrong with connection or
     *                      executing query
     */
    @Override
    public Integer update(Role role) throws SQLException {

        Integer id = null;
        Connection connection = null;

        try {
            connection = pool.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(updateSql);
            statement.setString(1, role.getName());
            statement.setInt(2, role.getId());
            statement.executeUpdate();

            id = role.getId();
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
     * Method deletes role with current id in DB
     *
     * @param id ID of role to delete
     * @return Delete result
     * @throws SQLException - if something wrong with connection or
     *                      executing query
     */
    @Override
    public boolean delete(int id) throws SQLException {

        Connection connection = null;
        connection.setAutoCommit(false);

        try {
            connection = pool.getConnection();

            PreparedStatement statement = connection.prepareStatement(existSql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {

                statement = connection.prepareStatement(deleteSql);
                statement.setInt(1, id);
                statement.executeQuery();

                return true;
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            pool.releaseConnection(connection);
        }
        return false;
    }
}
