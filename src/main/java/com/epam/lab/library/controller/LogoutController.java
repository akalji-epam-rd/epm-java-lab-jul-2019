package com.epam.lab.library.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpSession session = req.getSession();
//        session.removeAttribute("password");
//        session.removeAttribute("email");
//        session.removeAttribute("roles");
        session.removeAttribute("user");
        resp.sendRedirect("/");
    }
}
