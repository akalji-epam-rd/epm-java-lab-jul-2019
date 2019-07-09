package com.epam.lab.library.DAO;

import com.epam.lab.library.connectionpool.ConnectionPool;
import com.epam.lab.library.dao.Interfaces.RoleDao;
import com.epam.lab.library.domain.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao {

    private ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public Role get(int id) {
        Connection connection = null;
        try {
            connection = pool.getConnection();

            String query = "SELECT roles.id roles.name FROM library.roles " +
                    "WHERE roles.id=" + id;

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
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

    @Override
    public List<Role> getAll() {
        Connection connection = null;
        List<Role> list = new ArrayList<>();
        try {
            connection = pool.getConnection();

            String query = "SELECT * FROM library.roles ";

            PreparedStatement statement = connection.prepareStatement(query);
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
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public int save(Role role) {
        Connection connection = null;
        String name = role.getName();

        try {
            connection = pool.getConnection();

            String query = "INSERT INTO library.roles (name) VALUES (" + name + ");";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }
        return role.getId();
    }

    @Override
    public int update(Role role) {
        Connection connection = null;
        String name = role.getName();

        try {
            connection = pool.getConnection();

            String query = "INSERT INTO library.roles (name) VALUES (" + name + ");";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeQuery();


            query = "SELECT library.roles.id FROM library.roles WHERE name = " + name;
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getInt("id");
            } else {
                return 0;

                //TODO What to give back if no update?
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }
        return role.getId();
    }

    @Override
    public int delete(int id) {
        Connection connection = null;
        try {
            connection = pool.getConnection();

            String query = "SELECT EXISTS(SELECT role_id FROM library.user_roles WHERE role_id = " + id + ");";

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String result = resultSet.getString("exist");

                if (result.equals("true")) return id;
                else {
                    query = "DELETE from library.roles WHERE id = " + id + ";";
                    statement = connection.prepareStatement(query);
                    statement.executeQuery();
                }
            } else {
                return id; //TODO Return id anyway?
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(connection);
        }
        return id;
    }
}
