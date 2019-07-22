package com.epam.lab.library.dao.interfaces;

import com.epam.lab.library.domain.User;
import com.epam.lab.library.util.pagination.Paging;

import java.sql.SQLException;
import java.util.List;

/**
 * User data access object
 */
public interface UserDao {

    User getById(int id);

    List<User> getAll();

    List<User> getAll(Paging paging);

    Integer save(User user) throws SQLException;

    Integer update(User user) throws SQLException;

    boolean delete(int id) throws SQLException;

    Integer getTotal() throws SQLException;
}
