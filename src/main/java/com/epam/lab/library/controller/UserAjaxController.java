package com.epam.lab.library.controller;

import com.epam.lab.library.dao.RoleDaoImpl;
import com.epam.lab.library.dao.interfaces.RoleDao;
import com.epam.lab.library.domain.Role;
import com.epam.lab.library.domain.User;
import com.epam.lab.library.service.UserServiceImpl;
import com.epam.lab.library.service.interfaces.UserService;
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

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User ajax controller class
 */
@WebServlet(loadOnStartup = 1)
public class UserAjaxController extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(UserAjaxController.class);
    private RoleDao roleDao = new RoleDaoImpl();
    private UserService userService = new UserServiceImpl();
    private Paging paging = new Paging().setPageNumber(1);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] pathInfo = req.getPathInfo().split("/");
        if ("get".equals(pathInfo[1])) {
            Integer id;
            User user = new User();

            try {
                id = Integer.parseInt(pathInfo[2]);
                user = userService.getById(id);
            } catch (NumberFormatException | SQLException e) {
                LOG.error(e.getMessage());
            }

            JSONObject userJson = user.getAsJson();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(userJson.toString());

        } else if ("getAll".equals(pathInfo[1])) {

            if (pathInfo.length > 2 && pathInfo[2] != null) {

                Integer pageNumber = Integer.parseInt(pathInfo[2]);
                paging.setPageNumber(pageNumber);

                Pagination<User> pagination = new Pagination<>();
                try {
                    pagination = userService.getAllPaginationUsers(paging);
                    pagination.setList(pagination.getList().stream().sorted(Comparator.comparingInt(User::getId))
                            .collect(Collectors.toList()));
                } catch (SQLException e) {
                    LOG.error(e.getMessage());
                }

                List<JSONObject> userJsonList = new ArrayList<>();
                for (User u : pagination.getList()) {
                    userJsonList.add(u.getAsJson());
                }

                JSONObject paginationJson = pagination.getAsJson();
                paginationJson.put("users", userJsonList);
                paginationJson.put("currentPage", paging.getPageNumber());

                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(paginationJson.toString());

                return;
            }

            Pagination<User> pagination = new Pagination<>();
            try {
                pagination = userService.getAllPaginationUsers(paging);
                pagination.setList(pagination.getList().stream().sorted(Comparator.comparingInt(User::getId))
                        .collect(Collectors.toList()));

            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }

            List<JSONObject> userJsonList = new ArrayList<>();
            for (User u : pagination.getList()) {
                userJsonList.add(u.getAsJson());
            }

            JSONObject paginationJson = pagination.getAsJson();
            paginationJson.put("users", userJsonList);
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

        Integer userId = null;
        String userName = null;
        String userLastName = null;
        String userEmail = null;
        String userPass = null;
        Set<Role> userRoles = new HashSet<>();

        try {
            if (requestParameters != null) {
                userId = requestParameters.getInt("userId");
                userName = requestParameters.getString("userName");
                userLastName = requestParameters.getString("userLastName");
                userEmail = requestParameters.getString("userEmail");
                userPass = requestParameters.getString("userPass");

                Role role = new Role();
                role.setName(requestParameters.getString("userRole"));
                Integer roleId = roleDao.save(role);
                role.setId(roleId);
                userRoles.add(role);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }

        User user = new User();
        user.setId(userId);
        user.setName(userName);
        user.setLastName(userLastName);
        user.setEmail(userEmail);
        user.setPassword(userPass);
        user.setRoles(userRoles);

        Pagination<User> pagination = new Pagination<>();
        try {
            Integer id;
            id = userService.save(user);
            pagination = userService.getAllPaginationUsers(paging);
            pagination.setList(pagination.getList().stream().sorted(Comparator.comparingInt(User::getId))
                    .collect(Collectors.toList()));
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }

        List<JSONObject> userJsonList = new ArrayList<>();
        for (User u : pagination.getList()) {
            userJsonList.add(u.getAsJson());
        }

        JSONObject paginationJson = pagination.getAsJson();
        paginationJson.put("users", userJsonList);
        paginationJson.put("currentPage", paging.getPageNumber());

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(paginationJson.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] pathInfo = req.getPathInfo().split("/");
        User user = null;
        Pagination<User> pagination = new Pagination<>();
        Integer id = null;
        try {
            id = Integer.parseInt(pathInfo[pathInfo.length - 1]);
            user = userService.getById(id);
            userService.delete(user);
            pagination = userService.getAllPaginationUsers(paging);
            pagination.setList(pagination.getList().stream().sorted(Comparator.comparingInt(User::getId))
                    .collect(Collectors.toList()));
        } catch (NumberFormatException | SQLException e) {
            LOG.error(e.getMessage());
        }

        List<JSONObject> userJsonList = new ArrayList<>();
        for (User u : pagination.getList()) {
            userJsonList.add(u.getAsJson());
        }

        JSONObject paginationJson = pagination.getAsJson();
        paginationJson.put("users", userJsonList);
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
            while ((line = reader.readLine()) != null)
                json.append(line);
            requestParameters = new JSONObject(json.toString());
        } catch (JSONException | IOException e) {
            LOG.error(e.getMessage());
        }

        if (req.getPathInfo().contains("update")) {
            Integer userId = null;
            String userName = null;
            String userLastName = null;
            String userEmail = null;
            String userPass = null;
            Set<Role> userRoles = new HashSet<>();

            try {
                if (requestParameters != null) {
                    userId = requestParameters.getInt("userId");
                    userName = requestParameters.getString("userName");
                    userLastName = requestParameters.getString("userLastName");
                    userEmail = requestParameters.getString("userEmail");
                    userPass = requestParameters.getString("userPass");

                    Role role = new Role();
                    role.setName(requestParameters.getString("userRole"));
                    Integer roleId = roleDao.save(role);
                    role.setId(roleId);
                    userRoles.add(role);
                }
            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }

            String[] pathInfo = req.getPathInfo().split("/");
            Integer id = null;
            User user = null;
            List<User> userList = new ArrayList<>();

            try {
                id = Integer.parseInt(pathInfo[pathInfo.length - 1]);
                user = userService.getById(id);
                user.setName(userName);
                user.setLastName(userLastName);
                user.setEmail(userEmail);
                user.setPassword(userPass);
                user.setRoles(userRoles);
            } catch (SQLException e) {
                LOG.error(e.getMessage());
            }

            List<JSONObject> userJsonList = new ArrayList<>();
            for (User u : userList) {
                userJsonList.add(u.getAsJson());
            }

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(userJsonList.toString());
        }
    }
}
