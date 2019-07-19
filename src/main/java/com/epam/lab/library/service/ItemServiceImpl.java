package com.epam.lab.library.service;

import com.epam.lab.library.dao.ItemDaoImpl;
import com.epam.lab.library.dao.StatusesDaoImpl;
import com.epam.lab.library.dao.interfaces.ItemDao;
import com.epam.lab.library.dao.interfaces.StatusesDao;
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

    @Override
    public Integer save(Item item) throws SQLException {
        return itemDao.save(item);
    }

    @Override
    public Integer update(Item item) throws SQLException {
        return itemDao.update(item);
    }

    @Override
    public boolean delete(Item item) throws SQLException {
        return itemDao.delete(item);
    }

    @Override
    public List<Item> getAll(ItemFilter filter, Paging paging) throws SQLException {
        return itemDao.getAll(filter, paging);
    }

    public Pagination<Item> getAllPaginationItems(ItemFilter filter, Paging paging) throws SQLException {
        Pagination<Item> pagination = new Pagination<>();
        pagination.setList(itemDao.getAll(filter, paging));
        pagination.setTotal(itemDao.getTotal(filter));

        return pagination;
    }

    @Override
    public Item getById(Integer id) throws SQLException {
        return itemDao.getById(id);
    }

    @Override
    public boolean orderItem(Item item, User user) throws SQLException {

        if (!item.getStatus().getName().equals("available")) {
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

    @Override
    public boolean confirmOrder(Item item, Status status) throws SQLException {

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

    @Override
    public boolean returnItem(Item item) throws SQLException {

        try {
            Status status = statusesDao.getAll()
                    .stream().filter(s -> s.getName().equals("available"))
                    .findFirst().get();
            itemDao.update(item);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("error while returning order");
            return false;
        }

        return true;
    }
}