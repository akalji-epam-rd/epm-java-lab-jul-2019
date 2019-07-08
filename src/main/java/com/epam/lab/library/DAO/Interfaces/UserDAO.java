package com.epam.lab.library.DAO.Interfaces;

import com.epam.lab.library.model.User;

import java.util.List;

public interface UserDAO {

    User get(int id);

    List<User> getALL();

    List<User> getALL(String filter);

    void save(User user);

    void update(User user);

    void delete(int id);


}
