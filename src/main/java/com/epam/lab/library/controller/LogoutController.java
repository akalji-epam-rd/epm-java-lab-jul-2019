package com.epam.lab.library.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutController extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(LogoutController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();
//        session.removeAttribute("password");
//        session.removeAttribute("email");
//        session.removeAttribute("roles");
        session.removeAttribute("user");
        LOG.info("logout was successful");
        resp.sendRedirect("/");
    }
}
