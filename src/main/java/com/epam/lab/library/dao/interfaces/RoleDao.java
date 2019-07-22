package com.epam.lab.library.dao.interfaces;

import com.epam.lab.library.domain.Role;

import java.sql.SQLException;
import java.util.List;

/**
 * Users roles data access object
 */
public interface RoleDao {

    Role getById(int id) throws SQLException;

    List<Role> getAll() throws SQLException;

    Integer save(Role role) throws SQLException;

    Integer update(Role role) throws SQLException;

    boolean delete(int id) throws SQLException;
}
