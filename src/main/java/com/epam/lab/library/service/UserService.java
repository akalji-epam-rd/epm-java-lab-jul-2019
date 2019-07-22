package com.epam.lab.library.service;

import com.epam.lab.library.dao.UserDaoImpl;
import com.epam.lab.library.dao.interfaces.UserDao;
import com.epam.lab.library.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    UserDao userDao = new UserDaoImpl();
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    public List<User> getAllUsers() {
        return null;
    }

    public User getByEmail(String email) {
        try {
            return userDao.getByEmail(email);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

}
