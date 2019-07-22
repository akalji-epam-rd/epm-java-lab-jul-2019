package com.epam.lab.library.controller;

import com.epam.lab.library.dao.UserDaoImpl;
import com.epam.lab.library.dao.interfaces.UserDao;
import com.epam.lab.library.domain.Role;
import com.epam.lab.library.service.UserService;
import com.epam.lab.library.util.RoleUtil;
import com.epam.lab.library.util.ViewResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "UserController", urlPatterns = "/user/*", loadOnStartup = 1)
public class UserController extends HttpServlet {

    ViewResolver resolver = new ViewResolver();
    UserDao userDao = new UserDaoImpl();
    UserService service = new UserService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        Set<Role> roles = (HashSet)session.getAttribute("roles");
        boolean hasAdminRole = RoleUtil.hasRole("Administrator", roles);
        request.setAttribute("hasAdminRole", hasAdminRole);

        String[] info = resolver.getViewPath(request);
        String type = info[0];
        String view = info[1];
        switch (type) {
            case "all":
                request.setAttribute("users", userDao.getAll());
                request.getRequestDispatcher(view).forward(request, response);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
