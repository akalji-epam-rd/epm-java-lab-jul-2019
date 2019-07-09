package com.epam.lab.library.dao.interfaces;


import com.epam.lab.library.domain.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDao {

    Integer save(Item i) throws SQLException;

    Integer update(Item i) throws SQLException;

    Integer delete(Item i) throws SQLException;

    List<Item> getAll() throws SQLException;

    Item getById(Integer id) throws SQLException;

}
