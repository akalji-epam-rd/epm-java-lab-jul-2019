package com.epam.lab.library.service;

import com.epam.lab.library.dao.RoleDaoImpl;
import com.epam.lab.library.dao.UserDaoImpl;
import com.epam.lab.library.dao.interfaces.RoleDao;
import com.epam.lab.library.dao.interfaces.UserDao;
import com.epam.lab.library.domain.User;
import com.epam.lab.library.service.interfaces.UserService;
import com.epam.lab.library.util.pagination.Pagination;
import com.epam.lab.library.util.pagination.Paging;

import java.sql.SQLException;
import java.util.List;

/**
 * Service layer object for users
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    private RoleDao roleDao = new RoleDaoImpl();

    /**
     * Initializes a newly created UserServiceImpl object
     */
    public UserServiceImpl() {
    }

    /**
     * Initializes a newly created UserServiceImpl object with certain itemDao
     *
     * @param userDao UserDao object
     */
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Save user to the store
     *
     * @param user - library user
     * @return id of saved element
     * @throws SQLException - if something wrong with connection or
     *                      executing query
     */
    @Override
    public Integer save(User user) throws SQLException {
        return userDao.save(user);
    }

    /**
     * Update user to the store
     *
     * @param user - library item
     * @return id of updated element
     * @throws SQLException - if something wrong with connection or
     *                      executing query
     */
    @Override
    public Integer update(User user) throws SQLException {
        return userDao.update(user);
    }

    /**
     * delete user from store
     *
     * @param user - library user
     * @return <tt>true</tt> if delete was successful
     * @throws SQLException - if something wrong with connection or
     *                      executing query
     */
    @Override
    public boolean delete(User user) throws SQLException {
        return userDao.delete(user.getId());
    }

    /**
     * return list of users with pagination
     *
     * @param paging - pagination settings
     * @return list of users with pagination
     * @throws SQLException - if something wrong with connection or
     *                      executing query
     */
    @Override
    public List<User> getAll(Paging paging) throws SQLException {
        return userDao.getAll(paging);
    }

    /**
     * return list of users with additional information about pagination
     *
     * @param paging - pagination settings
     * @return list of users with pagination
     * @throws SQLException - if something wrong with connection or
     *                      executing query
     */
    @Override
    public Pagination<User> getAllPaginationUsers(Paging paging) throws SQLException {
        Pagination<User> pagination = new Pagination<>();
        pagination.setList((userDao.getAll(paging)));
        pagination.setTotal(userDao.getTotal());
        return pagination;
    }

    /**
     * return user by id form the store
     *
     * @param id - user's id
     * @return return user by id form the store
     * @throws SQLException - if something wrong with connection or
     *                      executing query
     */
    @Override
    public User getById(Integer id) throws SQLException {
        return userDao.getById(id);
    }
}
