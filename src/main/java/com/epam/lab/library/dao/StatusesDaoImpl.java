package com.epam.lab.library.dao;

import com.epam.lab.library.util.connectionpool.ConnectionPool;
import com.epam.lab.library.dao.interfaces.StatusesDao;
import com.epam.lab.library.domain.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Item statuses data access object
 */
public class StatusesDaoImpl implements StatusesDao {

    ConnectionPool pool = ConnectionPool.getInstance();
    private String selectSql = "SELECT s.id, s.name FROM library.statuses s WHERE id = ?";
    private String selectAllSql = "SELECT s.id, s.name FROM library.statuses s";

    /**
     * Method returns list of all item`s statuses
     *
     * @return Statuses list
     */
    @Override
    public List<Status> getAll() {

        Connection conn = null;
        Statement statement = null;
        List<Status> statusList = new ArrayList<>();

        try {
            conn = pool.getConnection();
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(selectAllSql);
            while (rs.next()) {
                Status status = new Status();
                status.setId(rs.getInt("id"));
                status.setName(rs.getString("name"));
                statusList.add(status);
            }
            return statusList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(conn);
        }
        return statusList;
    }

    /**
     * Method returns status for current id
     *
     * @param id Status id
     * @return Status object
     */
    @Override
    public Status getById(Integer id) {

        Connection conn = null;
        PreparedStatement statement = null;
        Status status = new Status();

        try {
            conn = pool.getConnection();
            statement = conn.prepareStatement(selectSql);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                status.setId(rs.getInt("id"));
                status.setName(rs.getString("name"));
            }
            return status;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.releaseConnection(conn);
        }
        return status;
    }
}
