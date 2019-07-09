package com.epam.lab.library.dao.Interfaces;


import com.epam.lab.library.domain.Role;

import java.util.List;

public interface RoleDao {

    Role get(int id);

    List<Role> getAll();

    int save(Role role);

    int update(Role role);

    int delete(int id);


}
