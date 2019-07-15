package com.epam.lab.library.dao.interfaces;


import com.epam.lab.library.domain.Role;

import java.sql.SQLException;
import java.util.List;

public interface RoleDao {

    Role getById(int id) throws SQLException;

    List<Role> getAll() throws SQLException;

    Integer save(Role role);

    Integer update(Role role);

    boolean delete(int id);


}
