package com.epam.lab.library;


import com.epam.lab.library.dao.UserDaoImpl;
import com.epam.lab.library.dao.interfaces.UserDao;
import com.epam.lab.library.domain.Role;
import com.epam.lab.library.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.nonNull;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse res = (HttpServletResponse) servletResponse;

        final String email = req.getParameter("email");
        final String password = req.getParameter("password");

        @SuppressWarnings("unchecked")
        final AtomicReference<UserDao> userDao = (AtomicReference<UserDao>) req.getServletContext().getAttribute("userDao");

        final HttpSession session = req.getSession();

        boolean isStaticResource = req.getRequestURI().startsWith("/static/");

        if (isStaticResource) {
            filterChain.doFilter(req, res);
        } else if (nonNull(session) &&
                nonNull(session.getAttribute("email")) &&
                nonNull(session.getAttribute("password"))) {

            filterChain.doFilter(req, res);

        } else if (userIsExist(email, password, req.getSession(), userDao.get())) {

            res.sendRedirect("/book/all");

        } else {

            req.getRequestDispatcher("/").forward(req, res);

        }

    }

    @Override
    public void destroy() {

    }

    private boolean userIsExist(String email, String password, HttpSession session, UserDao userDao) {

        List<User> userList = userDao.getAll();

        for (User u : userList) {
            if (u.getEmail().equals(email)
                    && u.getPassword().equals(password)) {

                User user = userDao.getById(u.getId());

                session.setAttribute("password", password);
                session.setAttribute("email", email);
                session.setAttribute("roles", user.getRoles());

                return true;
            }
        }

        return false;

    }
}
