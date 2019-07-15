package com.epam.lab.library.dao.interfaces;


import com.epam.lab.library.domain.User;

import java.util.List;

public interface UserDao {

    User getById(int id);

    List<User> getAll();

    Integer save(User user);

    Integer update(User user);

    boolean delete(int id);


}
