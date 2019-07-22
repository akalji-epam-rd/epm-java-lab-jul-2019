package com.epam.lab.library.service.interfaces;

import com.epam.lab.library.domain.Book;
import com.epam.lab.library.domain.Item;
import com.epam.lab.library.domain.Status;
import com.epam.lab.library.domain.User;
import com.epam.lab.library.util.filter.ItemFilter;
import com.epam.lab.library.util.pagination.Pagination;
import com.epam.lab.library.util.pagination.Paging;

import java.sql.SQLException;
import java.util.List;

/**
 * Service layer object for items
 */
public interface ItemService {

    Integer save(Item item) throws SQLException;

    Integer update(Item item) throws SQLException;

    boolean delete(Item item) throws SQLException;

    List<Item> getAll(ItemFilter filter, Paging paging) throws SQLException;

    Pagination<Item> getAllPaginationItems(ItemFilter filter, Paging paging) throws SQLException;

    Item getById(Integer id) throws SQLException;

    boolean orderBook(Book book, User user) throws SQLException;

    boolean orderItem(Item item, User user) throws SQLException;

    boolean confirmOrder(Item item, Status status) throws SQLException;

    boolean returnItem(Item item) throws SQLException;
}
