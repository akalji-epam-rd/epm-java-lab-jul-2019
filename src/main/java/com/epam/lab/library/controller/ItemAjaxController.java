package com.epam.lab.library.controller;


import com.epam.lab.library.dao.BookDaoImpl;
import com.epam.lab.library.dao.StatusesDaoImpl;
import com.epam.lab.library.dao.UserDaoImpl;
import com.epam.lab.library.dao.interfaces.BookDao;
import com.epam.lab.library.dao.interfaces.StatusesDao;
import com.epam.lab.library.dao.interfaces.UserDao;
import com.epam.lab.library.domain.Item;
import com.epam.lab.library.domain.Status;
import com.epam.lab.library.service.ItemServiceImpl;
import com.epam.lab.library.service.interfaces.ItemService;
import com.epam.lab.library.util.pagination.Pagination;
import com.epam.lab.library.util.pagination.Paging;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(loadOnStartup = 1)
public class ItemAjaxController extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ItemAjaxController.class);
    private BookDao bookDao = new BookDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private StatusesDao statusesDao = new StatusesDaoImpl();
    private ItemService itemService = new ItemServiceImpl();
    private Paging paging = new Paging().setPageNumber(1);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] pathInfo = req.getPathInfo().split("/");
        if ("get".equals(pathInfo[1])) {
            Integer id;
            Item item = new Item();

            try {
                id = Integer.parseInt(pathInfo[2]);
                item = itemService.getById(id);
            } catch (NumberFormatException | SQLException e) {
                LOG.error(e.getMessage());
            }

            JSONObject itemJson = item.getAsJson();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(itemJson.toString());

        } else if ("getAll".equals(pathInfo[1])) {

            if (pathInfo.length > 2 && pathInfo[2] != null) {

                Integer pageNumber = Integer.parseInt(pathInfo[2]);
                paging.setPageNumber(pageNumber);

                Pagination<Item> pagination = new Pagination<>();
                try {
                    pagination = itemService.getAllPaginationItems(null, paging);
                    pagination.setList(pagination.getList().stream().sorted(Comparator.comparingInt(Item::getId))
                            .collect(Collectors.toList()));
                } catch (SQLException e) {
                    LOG.error(e.getMessage());
                }

                List<JSONObject> itemJsonList = new ArrayList<>();
                for (Item i : pagination.getList()) {
                    itemJsonList.add(i.getAsJson());
                }

                JSONObject paginationJson = pagination.getAsJson();
                paginationJson.put("items", itemJsonList);
                paginationJson.put("currentPage", paging.getPageNumber());

                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(paginationJson.toString());

                return;
            }

            Pagination<Item> pagination = new Pagination<>();
            try {
                pagination = itemService.getAllPaginationItems(null, paging);
                pagination.setList(pagination.getList().stream().sorted(Comparator.comparingInt(Item::getId))
                        .collect(Collectors.toList()));

            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }

            List<JSONObject> itemJsonList = new ArrayList<>();
            for (Item i : pagination.getList()) {
                itemJsonList.add(i.getAsJson());
            }

            JSONObject paginationJson = pagination.getAsJson();
            paginationJson.put("items", itemJsonList);
            paginationJson.put("currentPage", paging.getPageNumber());

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(paginationJson.toString());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StringBuilder json = new StringBuilder();
        String line = null;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null)
                json.append(line);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }

        JSONObject requestParameters = null;
        try {
            requestParameters = new JSONObject(json.toString());
        } catch (JSONException e) {
            LOG.error(e.getMessage());
        }

        Integer itemId = null;
        Integer bookId = null;
        Integer userId = null;
        Integer statusId = null;
        if (requestParameters != null) {
            itemId = requestParameters.getInt("itemId");
            bookId = requestParameters.getInt("bookId");
            userId = requestParameters.getInt("userId");
            statusId = requestParameters.getInt("status");
        }

        Item item = new Item();

        Pagination<Item> pagination = new Pagination<>();
        try {
            item.setId(itemId);
            item.setBook(bookDao.getById(bookId));
            item.setUser(userId != null ? userDao.getById(userId) : null);
            item.setStatus(statusesDao.getById(statusId));
            item.setDate(new Date(new java.util.Date().getTime()));
            Integer id;
            id = itemService.save(item);
            pagination = itemService.getAllPaginationItems(null, paging);
            pagination.setList(pagination.getList().stream().sorted(Comparator.comparingInt(Item::getId))
                    .collect(Collectors.toList()));
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }

        List<JSONObject> itemJsonList = new ArrayList<>();
        for (Item i : pagination.getList()) {
            itemJsonList.add(i.getAsJson());
        }

        JSONObject paginationJson = pagination.getAsJson();
        paginationJson.put("items", itemJsonList);
        paginationJson.put("currentPage", paging.getPageNumber());

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(paginationJson.toString());

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] pathInfo = req.getPathInfo().split("/");
        Item item = null;
        Pagination<Item> pagination = new Pagination<>();
        Integer id = null;
        try {
            id = Integer.parseInt(pathInfo[pathInfo.length - 1]);
            item = itemService.getById(id);
            itemService.delete(item);
            pagination = itemService.getAllPaginationItems(null, paging);
            pagination.setList(pagination.getList().stream().sorted(Comparator.comparingInt(Item::getId))
                    .collect(Collectors.toList()));
        } catch (NumberFormatException | SQLException e) {
            LOG.error(e.getMessage());
        }


        List<JSONObject> itemJsonList = new ArrayList<>();
        for (Item i : pagination.getList()) {
            itemJsonList.add(i.getAsJson());
        }

        JSONObject paginationJson = pagination.getAsJson();
        paginationJson.put("items", itemJsonList);
        paginationJson.put("currentPage", paging.getPageNumber());

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(paginationJson.toString());

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StringBuilder json = new StringBuilder();
        String line = null;
        JSONObject requestParameters = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            requestParameters = new JSONObject(json.toString());
        } catch (JSONException | IOException e) {
            LOG.error(e.getMessage());
        }

        if (req.getPathInfo().contains("update")) {
            Integer itemId = null;
            Integer bookId = null;
            Integer userId = null;
            Integer statusId = null;
            if (requestParameters != null) {
                itemId = requestParameters.getInt("itemId");
                bookId = requestParameters.getInt("bookId");
                userId = requestParameters.getInt("userId") > -1 ? requestParameters.getInt("userId") : null;
                statusId = requestParameters.getInt("status");
            }

            String[] pathInfo = req.getPathInfo().split("/");
            Integer id = null;
            Item item = null;
            List<Item> itemList = new ArrayList<>();
            try {
                id = Integer.parseInt(pathInfo[pathInfo.length - 1]);
                item = itemService.getById(id);
                item.setBook(bookDao.getById(bookId));
                item.setUser(userId != null ? userDao.getById(userId) : null);
                item.setStatus(statusesDao.getById(statusId));
                item.setDate(new Date(new java.util.Date().getTime()));
                itemService.update(item);
                itemList = itemService.getAll(null, paging)
                        .stream().sorted(Comparator.comparingInt(Item::getId))
                        .collect(Collectors.toList());
            } catch (NumberFormatException | SQLException e) {
                LOG.error(e.getMessage());
            }

            List<JSONObject> itemJsonList = new ArrayList<>();

            for (Item i : itemList) {
                itemJsonList.add(i.getAsJson());
            }

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(itemJsonList.toString());

        } else if (req.getPathInfo().contains("confirmOrder")) {

            Integer id = requestParameters != null ? requestParameters.getInt("itemId") : 0;
            if (id == 0) {
                return;
            }

            List<Item> itemList = new ArrayList<>();
            try {
                Item item = itemService.getById(id);
                itemService.confirmOrder(item, new Status(3));
                itemList = itemService.getAll(null, paging)
                        .stream().sorted(Comparator.comparingInt(Item::getId))
                        .collect(Collectors.toList());
            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }

            List<JSONObject> itemJsonList = new ArrayList<>();

            for (Item i : itemList) {
                itemJsonList.add(i.getAsJson());
            }

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(itemJsonList.toString());
        }
    }
}
