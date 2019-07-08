package com.epam.lab.library.dao.Interfaces;


import com.epam.lab.library.domain.User;

import java.util.List;

public interface UserDao {

    User get(int id);

    List<User> getAll();

    void save(User user);

    void update(User user);

    void delete(int id);


}
