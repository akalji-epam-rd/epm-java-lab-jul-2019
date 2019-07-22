package com.epam.lab.library.controller;

import com.epam.lab.library.domain.Book;
import com.epam.lab.library.domain.User;
import com.epam.lab.library.service.BookService;
import com.epam.lab.library.service.ItemServiceImpl;
import com.epam.lab.library.service.interfaces.ItemService;
import com.epam.lab.library.util.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "BookController", urlPatterns = "/book/*", loadOnStartup = 1)
public class BookController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    ViewResolver resolver = new ViewResolver();
    BookService bookService = new BookService();
    ItemService itemService = new ItemServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] info = resolver.getViewPath(request);
        String type = info[0];
        String view = info[1];
        Integer id;
        User user;
        switch (type) {
            case "all":
                request.setAttribute("books", bookService.getAll());
                request.getRequestDispatcher(view).forward(request, response);
                break;
            case "get":
                id = resolver.getIdFromRequest(request);
                if (id != null) {
                    request.setAttribute("book", bookService.getById(id));
                }
                request.getRequestDispatcher(view).forward(request, response);
                break;
            case "order":
                user = (User) request.getSession().getAttribute("user");
                Book book = new Book(); //TODO Take book id from jsp
                try {
                    boolean success = itemService.orderBook(book, user);
                    if (success) {
                        response.sendRedirect("/book/all");
                    } else {
                        //TODO Make message for jsp
                        request.setAttribute("message", "All books are taken");
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                    break;
                }
            case "add":
            case "edit":
                user = (User) request.getSession().getAttribute("user");
                if (!user.getRoles().contains("admin")) {
                    response.sendRedirect("/book/all");
                    break;
                }
            default:
                response.sendRedirect("/");
                logger.warn("Page doesn't exist: " + request.getPathInfo());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] info = resolver.getViewPath(request);
        String type = info[0];
        String view = info[1];
        Integer id;
        User user;
        switch (type) {
            case "add":
                Book book = new Book(); //TODO Get book from jsp
                bookService.save(book);
                response.sendRedirect("/book/all");
            case "edit":
                user = (User) request.getSession().getAttribute("user");
                if (user.getRoles().contains("admin")) {
                    response.sendRedirect("/book/all");
                    break;
                }
                response.sendRedirect("/book/all");
                break;
            case "delete":
                id = resolver.getIdFromRequest(request);
                //TODO Add logic for delete
                request.getRequestDispatcher(view).forward(request, response);
                break;
            default:
                response.sendRedirect("/");
                logger.warn("Page doesn't exist: " + request.getPathInfo());

        }
    }
}
