package com.epam.lab.library.dao.interfaces;

import com.epam.lab.library.domain.Item;
import com.epam.lab.library.util.filter.ItemFilter;
import com.epam.lab.library.util.pagination.Paging;
import java.sql.SQLException;
import java.util.List;

public interface ItemDao {

    Integer save(Item item) throws SQLException;

    Integer update(Item item) throws SQLException;

    boolean delete(Item item) throws SQLException;

    List<Item> getAll(ItemFilter filter, Paging pagination) throws SQLException;

    Item getById(Integer id) throws SQLException;

    Integer getTotal(ItemFilter filter) throws SQLException;
}
