package com.epam.lab.library.dao;


import com.epam.lab.library.util.connectionpool.ConnectionPool;
import com.epam.lab.library.dao.interfaces.StatusesDao;
import com.epam.lab.library.domain.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatusesDaoImpl implements StatusesDao {

    ConnectionPool pool = ConnectionPool.getInstance();

        private String selectSql = "SELECT * FROM library.statuses WHERE id = ?";
        private String selectAllSql = "SELECT * FROM library.statuses";


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


        return null;
    }

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


        return null;
    }
}
