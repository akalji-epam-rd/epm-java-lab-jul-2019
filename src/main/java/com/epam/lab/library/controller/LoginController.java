package com.epam.lab.library.controller;

import com.epam.lab.library.dao.UserDaoImpl;
import com.epam.lab.library.dao.interfaces.UserDao;
import com.epam.lab.library.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Login queries control
 */
public class LoginController extends HttpServlet {

    private UserDao userDao = new UserDaoImpl();

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        final String email = req.getParameter("email");
        final String password = req.getParameter("password");

        HttpSession session = req.getSession();

        List<User> userList = userDao.getAll();

        for (User u : userList) {
            if (u.getEmail().equals(email)
                    && u.getPassword().equals(password)) {

                User user = userDao.getById(u.getId());
                session.setAttribute("userId", user.getId());
                session.setAttribute("password", password);
                session.setAttribute("email", email);
                session.setAttribute("roles", user.getRoles());
                session.setAttribute("user", user);
                LOG.info("user is exist and logged in");

                resp.sendRedirect("/book/all");
            }
        }
    }
}
