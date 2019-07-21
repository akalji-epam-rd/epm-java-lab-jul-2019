package com.epam.lab.library.service;

import com.epam.lab.library.dao.UserDaoImpl;
import com.epam.lab.library.dao.interfaces.UserDao;
import com.epam.lab.library.domain.User;

import java.util.List;

public class UserService {

    UserDao userDao = new UserDaoImpl();

    public List<User> getAllUsers() {
        return null;
    }

}
