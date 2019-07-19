package com.epam.lab.library.controller;

import com.epam.lab.library.dao.BookDaoImpl;
import com.epam.lab.library.dao.interfaces.BookDao;
import com.epam.lab.library.domain.Role;
import com.epam.lab.library.service.BookService;
import com.epam.lab.library.util.connectionpool.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@WebServlet(name = "BookController", urlPatterns = "/book/*", loadOnStartup = 1)
public class BookController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    ViewResolver resolver = new ViewResolver();
    BookService bookService = new BookService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] info = resolver.getViewPath(request);
        String type = info[0];
        String view = info[1];
        Integer id;
        switch (type) {
            case "all":
                request.setAttribute("books", bookService.getAll());
                request.getRequestDispatcher(view).forward(request, response);
                break;
            case "add":
            case "edit":
                Set<Role> roles = (HashSet) request.getSession().getAttribute("roles");
                if (!roles.contains("admin")) {
                    response.sendRedirect("/book/all");
                }
                break;
            case "get":
                id = resolver.getIdFromRequest(request);
                request.setAttribute("book", bookService.getById(id));
                request.getRequestDispatcher(view).forward(request, response);
                break;
            case "delete":
                break;
            default:
                response.sendRedirect("/");
                logger.warn("Page doesn't exist: " + request.getPathInfo());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
