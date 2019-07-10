package com.epam.lab.library.dao;

import com.epam.lab.library.connectionpool.ConnectionPool;
import com.epam.lab.library.dao.interfaces.RoleDao;
import com.epam.lab.library.domain.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao {

    private ConnectionPool pool = ConnectionPool.getInstance();

    private String selectSql = "SELECT roles.id, roles.name FROM library.roles WHERE roles.id = ?";
    private String selectAllSql = "SELECT * FROM library.roles ";
    private String insertSql = "INSERT INTO library.roles (name) VALUES (?);";
    private String selectIdSql = "SELECT id FROM library.roles WHERE name =?;";
    private String updateSql = "INSERT INTO library.roles (id, name) VALUES (?,?);";
    private String existSql = "EXISTS(SELECT role_id FROM library.user_roles WHERE role_id = ?);";
    private String deleteSql = "DELETE from library.roles WHERE id = ?;";

    /**
     * Method return role object
     *
     * @param id Role id
     * @return Role object
     */
    @Override
    public Role getById(int id) throws SQLException {
        Connection connection = pool.getConnection();
        PreparedStatement statement = connection.prepareStatement(selectSql);
        statement.setInt(1, id);

        try (ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt("id"))
                        .setName(resultSet.getString("name"));
                return role;
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

    /**
     * Method returns collection of roles
     *
     * @return roles collection
     */
    @Override
    public List<Role> getAll() throws SQLException {
        Connection connection = pool.getConnection();
        PreparedStatement statement = connection.prepareStatement(selectAllSql);

        List<Role> list = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery()){

            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt("id"))
                        .setName(resultSet.getString("name"));
                list.add(role);
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
     * Method save role to DB
     *
     * @param role Role for saving
     * @return id of saved role
     */
    @Override
    public Integer save(Role role) {
        Connection connection = null;
        String name = role.getName();

        try {
            connection = pool.getConnection();

            PreparedStatement statement = connection.prepareStatement(insertSql);
            statement.setString(1, name);
            statement.executeQuery();

            statement = connection.prepareStatement(selectIdSql);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }
        return role.getId();
    }

    /**
     * Method updates role with current id
     *
     * @param role Role object to update
     * @return id of updated role
     */
    @Override
    public Integer update(Role role) {
        Connection connection = null;

        try {
            connection = pool.getConnection();

            PreparedStatement statement = connection.prepareStatement(updateSql);
            statement.setInt(1, role.getId());
            statement.setString(2, role.getName());
            statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }
        return role.getId();
    }

    /**
     * Method deletes record with current id in DB
     *
     * @param id ID of record to delete
     * @return Delete result
     */
    @Override
    public boolean delete(int id) {
        Connection connection = null;
        try {
            connection = pool.getConnection();

            PreparedStatement statement = connection.prepareStatement(existSql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            String result = resultSet.getString("exist");

            if (!result.equals("true")) {
                statement = connection.prepareStatement(deleteSql);
                statement.setInt(1, id);
                statement.executeQuery();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }
        return false;
    }
}
