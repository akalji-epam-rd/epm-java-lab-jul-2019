package com.epam.lab.library.dao.Interfaces;


import com.epam.lab.library.domain.Role;

import java.util.List;

public interface RoleDao {

    Role get(int id);

    List<Role> getAll();

    void save(Role role);

    void update(Role role);

    void delete(int id);


}
