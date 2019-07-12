package com.epam.lab.library.dao;


import com.epam.lab.library.dao.interfaces.RoleDao;
import com.epam.lab.library.domain.Role;
import com.epam.lab.library.util.connectionpool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao {

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
     */
    @Override
    public Role getById(int id) throws SQLException {
        Connection connection = pool.getConnection();
        PreparedStatement statement = connection.prepareStatement(selectSql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        try {

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
            ResultSet resultSet = statement.executeQuery();
            Integer id = null;
            if (resultSet.next()) {
                id = resultSet.getInt("id");
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

            statement.setString(1, role.getName());
            statement.setInt(2, role.getId());

            statement.executeUpdate();

            return role.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }
        return null;
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

            if (!resultSet.next()) {

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
