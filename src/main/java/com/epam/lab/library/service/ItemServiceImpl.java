package com.epam.lab.library.service;

import com.epam.lab.library.dao.ItemDaoImpl;
import com.epam.lab.library.dao.StatusesDaoImpl;
import com.epam.lab.library.dao.interfaces.ItemDao;
import com.epam.lab.library.dao.interfaces.StatusesDao;
import com.epam.lab.library.domain.Book;
import com.epam.lab.library.domain.Item;
import com.epam.lab.library.domain.Status;
import com.epam.lab.library.domain.User;
import com.epam.lab.library.service.interfaces.ItemService;
import com.epam.lab.library.util.filter.ItemFilter;
import com.epam.lab.library.util.pagination.Pagination;
import com.epam.lab.library.util.pagination.Paging;
import java.sql.SQLException;
import java.util.List;

public class ItemServiceImpl implements ItemService {

    private ItemDao itemDao = new ItemDaoImpl();
    private StatusesDao statusesDao = new StatusesDaoImpl();

    public ItemServiceImpl() {
    }

    public ItemServiceImpl(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    /**
     * Save item to the store
     * @param item - library item
     * @return id of saved element
     * @throws SQLException - if something wrong with connection or
     *          executing query
     */
    @Override
    public Integer save(Item item) throws SQLException {
        return itemDao.save(item);
    }

    /**
     * Update item to the store
     * @param item - library item
     * @return id of updated element
     * @throws SQLException - if something wrong with connection or
     *          executing query
     */
    @Override
    public Integer update(Item item) throws SQLException {



        return itemDao.update(item);
    }

    /**
     * delete item from store
     * @param item - library item
     * @return <tt>true</tt> if delete was successful
     * @throws SQLException - if something wrong with connection or
     *          executing query
     */
    @Override
    public boolean delete(Item item) throws SQLException {
        return itemDao.delete(item);
    }

    /**
     * return list of items with filter and pagination
     * @param filter - item filter setting
     * @param paging - pagination settings
     * @return list of items with filter and pagination
     * @throws SQLException - if something wrong with connection or
     *          executing query
     */
    @Override
    public List<Item> getAll(ItemFilter filter, Paging paging) throws SQLException {
        return itemDao.getAll(filter, paging);
    }

    /**
     * return list of items with filter and additional information about pagination
     * @param filter - item filter setting
     * @param paging - pagination settings
     * @return list of items with filter and pagination
     * @throws SQLException - if something wrong with connection or
     *          executing query
     */
    public Pagination<Item> getAllPaginationItems(ItemFilter filter, Paging paging) throws SQLException {
        Pagination<Item> pagination = new Pagination<>();
        pagination.setList(itemDao.getAll(filter, paging));
        pagination.setTotal(itemDao.getTotal(filter));

        return pagination;
    }

    /**
     * return item by id form the store
     * @param id - item's id
     * @return return item by id form the store
     * @throws SQLException - if something wrong with connection or
     *          executing query
     */
    @Override
    public Item getById(Integer id) throws SQLException {
        return itemDao.getById(id);
    }

    /**
     * ordering book by user
     * @param book - book which will be ordered
     * @param user - the user who orders item
     * @return <TT>true</TT> if order was successful
     * @throws SQLException - if something wrong with connection or
     *          executing query
     */
    public boolean orderBook(Book book, User user) throws SQLException {

        ItemFilter filter = new ItemFilter();
        Status status = statusesDao.getById(1);
        filter.setBook(book);
        filter.setStatus(status);
        List<Item> itemList = getAll(filter, null);

        return !itemList.isEmpty() && orderItem(itemList.get(0), user);
    }

    /**
     * ordering available item by user
     * @param item - item which will be ordered
     * @param user - the user who orders item
     * @return <TT>true</TT> if order was successful
     * @throws SQLException - if something wrong with connection or
     *          executing query
     */
    @Override
    public boolean orderItem(Item item, User user) throws SQLException {

        if (!"available".equals(item.getStatus().getName())) {
            return false;
        }

        try {
            Status status = statusesDao.getAll()
                    .stream().filter(s -> s.getName().equals("ordered"))
                    .findFirst().get();
            item.setStatus(status);
            item.setUser(user);
            itemDao.update(item);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("error while ordering item");
            return false;
        }

        return true;
    }

    /**
     * confirm order item with providing status
     * @param item - item which already ordered by someone (item's status must be "ordered")
     * @param status - status which will be set for item if confirmation will be successful
     * @return <TT>true</TT> if confirmation was successful
     * @throws SQLException - if something wrong with connection or
     *          executing query
     */
    @Override
    public boolean confirmOrder(Item item, Status status) throws SQLException {

        if (!"ordered".equals(item.getStatus().getName())) {
            return false;
        }

        try {
            item.setStatus(status);
            itemDao.update(item);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("error while confirmaition order");
            return false;
        }

        return true;
    }

    /**
     * return item to the library
     * @param item - item which should be returned
     * @return <TT>true</TT> if returning was successful
     * @throws SQLException - if something wrong with connection or
     *          executing query
     */
    @Override
    public boolean returnItem(Item item) throws SQLException {

        try {
            Status status = statusesDao.getAll()
                    .stream().filter(s -> s.getName().equals("available"))
                    .findFirst().get();
            item.setStatus(status);
            item.setUser(null);
            itemDao.update(item);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("error while returning order");
            return false;
        }

        return true;
    }
}