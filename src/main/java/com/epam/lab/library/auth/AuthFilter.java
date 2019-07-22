package com.epam.lab.library.auth;


import com.epam.lab.library.domain.User;
import com.epam.lab.library.service.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

    UserService userService = new UserService();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse res = (HttpServletResponse) servletResponse;

        final String email = req.getParameter("email");
        final String password = req.getParameter("password");
        final HttpSession session = req.getSession();

        boolean isStaticResource = req.getRequestURI().startsWith("/static/");

        if (isStaticResource) {
            filterChain.doFilter(req, res);
        } else if (session != null && session.getAttribute("user") != null) {
            filterChain.doFilter(req, res);
        } else if (isUserExist(email, password, session)) {
            res.sendRedirect("/book/all");
        } else {
            req.getRequestDispatcher("/").forward(req, res);
        }

    }

    private boolean isUserExist(String email, String password, HttpSession session) {

        User user = userService.getByEmail(email);
        if (user == null) {
            return false;
        } else {
            if (user.getPassword().equals(password)) {
                session.setAttribute("user", user);
                return true;
            } else {
                return false;
            }
        }
    }

//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//
//        final HttpServletRequest req = (HttpServletRequest) servletRequest;
//        final HttpServletResponse res = (HttpServletResponse) servletResponse;
//
//        final String email = req.getParameter("email");
//        final String password = req.getParameter("password");
//
//        @SuppressWarnings("unchecked")
//        final AtomicReference<UserDao> userDao = (AtomicReference<UserDao>) req.getServletContext().getAttribute("userDao");
//
//        final HttpSession session = req.getSession();
//
//        boolean isStaticResource = req.getRequestURI().startsWith("/static/");
//
//        if (isStaticResource) {
//            filterChain.doFilter(req, res);
//        } else if (nonNull(session) &&
//                nonNull(session.getAttribute("email")) &&
//                nonNull(session.getAttribute("password"))) {
//
//            filterChain.doFilter(req, res);
//
//        } else if (isUserExist(email, password, req.getSession(), userDao.get())) {
//
//            res.sendRedirect("/book/all");
//
//        } else {
//
//            req.getRequestDispatcher("/").forward(req, res);
//
//        }
//
//    }


//    private boolean isUserExist(String email, String password, HttpSession session, UserDao userDao) {
//
//        List<User> userList = userDao.getAll();
//
//        for (User u : userList) {
//            if (u.getEmail().equals(email)
//                    && u.getPassword().equals(password)) {
//
//                User user = userDao.getById(u.getId());
//
//                session.setAttribute("password", password);
//                session.setAttribute("email", email);
//                session.setAttribute("roles", user.getRoles());
//
//                return true;
//            }
//        }
//
//        return false;
//
//    }
}
