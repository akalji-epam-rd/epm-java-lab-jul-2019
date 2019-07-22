package com.epam.lab.library.controller;

import com.epam.lab.library.dao.RoleDaoImpl;
import com.epam.lab.library.dao.interfaces.RoleDao;
import com.epam.lab.library.domain.Role;
import com.epam.lab.library.domain.User;
import com.epam.lab.library.service.UserServiceImpl;
import com.epam.lab.library.service.interfaces.UserService;
import com.epam.lab.library.util.RoleUtil;
import com.epam.lab.library.util.pagination.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User controller class
 * */
@WebServlet(loadOnStartup = 1)
public class UserController extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ItemController.class);
    private UserService userService = new UserServiceImpl();
    private RoleDao rolesDao = new RoleDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        Set<Role> roles = (HashSet) session.getAttribute("roles");

        boolean hasAdminRole = RoleUtil.hasRole("Administrator", roles);
        if (!hasAdminRole) {
            return;
        }
        request.setAttribute("hasAdminRole", hasAdminRole);

        try {
            request.setAttribute("users", userService.getAll(new Paging())
                    .stream().sorted(Comparator.comparingInt(User::getId))
                    .collect(Collectors.toList()));
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/views/item/all.jsp").forward(request, response);
    }
}
