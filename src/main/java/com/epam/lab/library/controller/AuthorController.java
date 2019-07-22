package com.epam.lab.library.controller;

import com.epam.lab.library.dao.AuthorDaoImpl;
import com.epam.lab.library.dao.interfaces.AuthorDao;
import com.epam.lab.library.domain.Author;
import com.epam.lab.library.util.connectionpool.ViewResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AuthorController", urlPatterns = "/author/*", loadOnStartup = 1)
public class AuthorController extends HttpServlet {

    AuthorDao authorDao = new AuthorDaoImpl();
    ViewResolver resolver = new ViewResolver();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Author> authors = authorDao.getAll();
        request.setAttribute("authors", authors);
        request.getRequestDispatcher(resolver.getViewPath(request)[1]).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
