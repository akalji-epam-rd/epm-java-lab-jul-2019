package com.epam.lab.library.controller;

import com.epam.lab.library.dao.AuthorDaoImpl;
import com.epam.lab.library.dao.interfaces.AuthorDao;
import com.epam.lab.library.domain.Author;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AuthorController", urlPatterns = "/author", loadOnStartup = 1)
public class AuthorController extends HttpServlet {

    AuthorDao authorDao = new AuthorDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = request.getPathInfo();
        request.setAttribute("message", message);
        List<Author> authors = authorDao.getAll();
        request.setAttribute("authors", authors);
        request.getRequestDispatcher("/WEB-INF/views/allEntities.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}