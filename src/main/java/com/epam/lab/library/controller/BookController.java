package com.epam.lab.library.controller;

import com.epam.lab.library.dao.BookDaoImpl;
import com.epam.lab.library.dao.interfaces.BookDao;
import com.epam.lab.library.service.BookService;
import com.epam.lab.library.util.connectionpool.ViewResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BookController", urlPatterns = "/book/*", loadOnStartup = 1)
public class BookController extends HttpServlet {

    ViewResolver resolver = new ViewResolver();
    BookDao bookDao = new BookDaoImpl();
    BookService service = new BookService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] info = resolver.getViewPath(request);
        String type = info[0];
        String view = info[1];
        switch (type) {
            case "all":
                request.setAttribute("books", bookDao.getAll());
                request.getRequestDispatcher(view).forward(request, response);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
