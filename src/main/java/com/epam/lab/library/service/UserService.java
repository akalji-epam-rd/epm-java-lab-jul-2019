package com.epam.lab.library.service;

import com.epam.lab.library.dao.UserDaoImpl;
import com.epam.lab.library.dao.interfaces.UserDao;
import com.epam.lab.library.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * User service layer class
 */
public class UserService {

    UserDao userDao = new UserDaoImpl();
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    /**
     * Returns list of users
     *
     * @return list of users
     */
    public List<User> getAllUsers() {
        return null;
    }

    /**
     * Returns user with current email
     *
     * @return User with current email
     */
    public User getByEmail(String email) {
        try {
            return userDao.getByEmail(email);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
