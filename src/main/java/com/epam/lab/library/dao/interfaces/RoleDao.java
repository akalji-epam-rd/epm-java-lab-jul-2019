package com.epam.lab.library.dao.interfaces;


import com.epam.lab.library.domain.Role;

import java.util.List;

public interface RoleDao {

    Role get(int id);

    List<Role> getAll();

    Integer save(Role role);

    Integer update(Role role);

    boolean delete(int id);


}
