package com.epam.lab.library.service.interfaces;

import com.epam.lab.library.domain.User;
import com.epam.lab.library.util.pagination.Pagination;
import com.epam.lab.library.util.pagination.Paging;

import java.sql.SQLException;
import java.util.List;

/**
 * Service layer object for users
 */
public interface UserService {

    Integer save(User user) throws SQLException;

    Integer update(User user) throws SQLException;

    boolean delete(User user) throws SQLException;

    List<User> getAll() throws SQLException;

    User getById(Integer id) throws SQLException;
}


