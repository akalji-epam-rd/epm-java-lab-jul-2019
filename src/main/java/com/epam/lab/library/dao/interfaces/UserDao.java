package com.epam.lab.library.dao.interfaces;


import com.epam.lab.library.domain.User;

import java.util.List;

public interface UserDao {

    User get(int id);

    List<User> getAll();

    int save(User user);

    int update(User user);

    int delete(int id);


}
