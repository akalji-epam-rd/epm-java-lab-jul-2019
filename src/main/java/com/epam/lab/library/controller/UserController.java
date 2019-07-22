package com.epam.lab.library.controller;

import com.epam.lab.library.dao.RoleDaoImpl;
import com.epam.lab.library.dao.interfaces.RoleDao;
import com.epam.lab.library.domain.Role;
import com.epam.lab.library.domain.User;
import com.epam.lab.library.service.UserServiceImpl;
import com.epam.lab.library.service.interfaces.UserService;
import com.epam.lab.library.util.RoleUtil;
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
 */
@WebServlet(loadOnStartup = 1)
public class UserController extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ItemController.class);
    private UserService userService = new UserServiceImpl();
    private RoleDao rolesDao = new RoleDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String[] pathInfo = request.getPathInfo().split("/");

        switch (pathInfo[1]) {
            case "all":
                getAll(request, response);
                break;
            case "register":
                register(request, response);
                break;
            case "edit":
                break;
            default:
                response.sendRedirect("/");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] pathInfo = req.getPathInfo().split("/");

        switch (pathInfo[1]) {
            case "add":
                addUser(req, resp);
                break;
            default:
                resp.sendRedirect("/");
                break;
        }
    }

    private void getAll(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        Set<Role> roles = (HashSet) session.getAttribute("roles");

        boolean hasAdminRole = RoleUtil.hasRole("Administrator", roles);
        if (!hasAdminRole) {
            return;
        }
        request.setAttribute("hasAdminRole", hasAdminRole);

        try {
            request.setAttribute("users", userService.getAll()
                    .stream().sorted(Comparator.comparingInt(User::getId))
                    .collect(Collectors.toList()));
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/views/user/all.jsp").forward(request, response);

    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        Set<Role> roles = (HashSet) session.getAttribute("roles");

        boolean hasAdminRole = RoleUtil.hasRole("Administrator", roles);
        if (!hasAdminRole) {
            return;
        }
        request.setAttribute("hasAdminRole", hasAdminRole);

        try {
            request.setAttribute("roles", rolesDao.getAll()
                    .stream().sorted(Comparator.comparingInt(Role::getId))
                    .collect(Collectors.toList()));
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        request.getRequestDispatcher("/WEB-INF/views/user/register.jsp").forward(request, response);
    }

    private void addUser(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        User user = new User();
        user.setName((String) req.getParameter("username"));
        user.setLastName((String) req.getParameter("userLastName"));
        user.setEmail((String) req.getParameter("email"));
        user.setPassword((String) req.getParameter("password"));

        String[] rolesId = req.getParameterValues("roles");
        Set<Role> roleSet = new HashSet<>();
        try {
            for (int i = 0; i < rolesId.length; i++) {
                roleSet.add(rolesDao.getById(Integer.parseInt(rolesId[i])));
            }
            user.setRoles(roleSet);
            Integer id = userService.save(user);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }

        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Set<Role> roles = (HashSet) session.getAttribute("roles");
        boolean hasAdminRole = RoleUtil.hasRole("Administrator", roles);

        if (!hasAdminRole) {
            resp.sendRedirect("/login");
        } else {
            resp.sendRedirect("/user/all");
        }
    }
}
