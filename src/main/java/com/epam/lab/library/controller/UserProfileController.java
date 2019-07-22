package com.epam.lab.library.controller;

import com.epam.lab.library.dao.UserDaoImpl;
import com.epam.lab.library.dao.interfaces.UserDao;
import com.epam.lab.library.domain.Item;
import com.epam.lab.library.domain.Role;
import com.epam.lab.library.domain.User;
import com.epam.lab.library.service.ItemServiceImpl;
import com.epam.lab.library.util.RoleUtil;
import com.epam.lab.library.util.filter.ItemFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserProfileController extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(UserProfileController.class);
    private UserDao userDao = new UserDaoImpl();
    private ItemServiceImpl itemService = new ItemServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        @SuppressWarnings("unchecked")
        Set<Role> roles = (HashSet) session.getAttribute("roles");
        boolean hasAdminRole = RoleUtil.hasRole("Administrator", roles);
        if (!hasAdminRole) {
            resp.sendRedirect("/book/all");
        }
        req.setAttribute("hasAdminRole", hasAdminRole);

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            resp.sendRedirect("/book/all");
        }

        User user = userDao.getById(userId);
        List<Item> items = new ArrayList<>();
        ItemFilter filter = new ItemFilter();
        filter.setUser(user);
        try {
            items = itemService.getAll(filter, null);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }

        req.setAttribute("items", items);
        req.setAttribute("user", user);

        req.getRequestDispatcher("/WEB-INF/views/user/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] pathInfo = req.getPathInfo().split("/");

        switch (pathInfo[1]) {
            case "confirmOrder":
                confirmOrder(req, resp);
                break;
            default:
                break;
        }
    }


    private void confirmOrder(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {

        String itemId = req.getParameter("itemId");

        try {
            Item item = itemService.getById(Integer.parseInt(itemId));
            boolean isReturned = itemService.returnItem(item);
            resp.sendRedirect("/user/profile");
        } catch (SQLException | NumberFormatException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
