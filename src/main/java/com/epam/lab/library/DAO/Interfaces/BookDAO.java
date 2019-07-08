package com.epam.lab.library.DAO.Interfaces;

import com.epam.lab.library.model.User;

public interface BookDAO {

    User getUser(int id);

    User getUser(String name);

    void save(User user);

    void update(User user);

    boolean delete(User user);

}
