package com.epam.lab.library.controller;

import com.epam.lab.library.domain.Author;
import com.epam.lab.library.domain.Book;
import com.epam.lab.library.domain.Role;
import com.epam.lab.library.domain.User;
import com.epam.lab.library.service.AuthorService;
import com.epam.lab.library.service.BookService;
import com.epam.lab.library.service.ItemServiceImpl;
import com.epam.lab.library.service.interfaces.ItemService;
import com.epam.lab.library.util.RoleUtil;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Book controller class
 **/
@WebServlet(name = "BookController", urlPatterns = "/book/*", loadOnStartup = 1)
public class BookController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    ViewResolver resolver = new ViewResolver();
    BookService bookService = new BookService();
    AuthorService authorService = new AuthorService();
    ItemService itemService = new ItemServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Set<Role> roles = (HashSet)request.getSession().getAttribute("roles");
        boolean hasAdminRole = RoleUtil.hasRole("Administrator", roles);
        request.setAttribute("hasAdminRole", hasAdminRole);
        String[] info = resolver.getViewPath(request);
        String type = info[0];
        String view = info[1];
        Integer id;
        switch (type) {
            case "all":
                request.setAttribute("books", bookService.getAll());
                request.getRequestDispatcher(view).forward(request, response);
                break;
            case "get":
                id = resolver.getIdFromRequest(request);
                if (id != null) {
                    request.setAttribute("book", bookService.getById(id));
                } else {
                    request.setAttribute("message", "Book is absent");
                }
                request.getRequestDispatcher(view).forward(request, response);
                break;
            case "add":
                if (!hasAdminRole) {
                    response.sendRedirect("/book/all");
                } else {
                    request.setAttribute("authors", authorService.getAll());
                    request.getRequestDispatcher(view).forward(request, response);
                }
                break;
            case "edit":
                id = resolver.getIdFromRequest(request);
                if (!hasAdminRole) {
                    response.sendRedirect("/book/all");
                } else {
                    if (id != null) {
                        request.setAttribute("book", bookService.getById(id));
                        request.setAttribute("authors", authorService.getAll());
                    } else {
                        request.setAttribute("message", "Book is absent");
                    }
                    request.getRequestDispatcher(view).forward(request, response);
                }
                break;
            default:
                response.sendRedirect("/");
                logger.warn("GET Page doesn't exist: " + request.getPathInfo());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Set<Role> roles = (HashSet)request.getSession().getAttribute("roles");
        boolean hasAdminRole = RoleUtil.hasRole("Administrator", roles);
        request.setAttribute("hasAdminRole", hasAdminRole);
        String[] info = resolver.getViewPath(request);
        String type = info[0];
        String view = info[1];
        Integer id;
        String bookName;
        String bookDescription;
        String[] authorIds;
        Set<Author> authors;
        User user;
        Book book;
        switch (type) {
            case "all":
                request.getRequestDispatcher(view).forward(request, response);
                break;
            case "find":
                bookName = request.getParameter("bookName");
                logger.warn("bookName: " + request.getParameter("bookName"));
                String authorLastName = request.getParameter("authorLastName");
                logger.warn("authorName: " + request.getParameter("authorLastName"));
                List<Book> books = bookService.findByNameAndAuthorLastName(bookName, authorLastName);
                request.setAttribute("books", books);
                request.getRequestDispatcher(view).forward(request, response);
                //response.sendRedirect("/book/all");
                break;
            case "order":
                user = (User) request.getSession().getAttribute("user");
                if (user != null) {
                    book = bookService.getById(Integer.parseInt(request.getParameter("bookId")));
                    try {
                        boolean success = itemService.orderBook(book, user);
                        if (success) {
                            response.sendRedirect("/book/all");
                        } else {
                            request.setAttribute("message", "All books are taken");
                        }
                    } catch (SQLException e) {
                        logger.error(e.getMessage(), e);
                    }
                } else {
                    response.sendRedirect("/login");
                }
                break;
            case "add":
                if (hasAdminRole) {
                    bookName = request.getParameter("name");
                    bookDescription = request.getParameter("description");
                    authorIds = request.getParameterValues("authors");
                    authors = new HashSet<>();
                    for (String authorIdString : authorIds) {
                        Integer authorId = Integer.parseInt(authorIdString);
                        authors.add(authorService.getById(authorId));
                    }
                    book = new Book();
                    book.setName(bookName)
                            .setDescription(bookDescription)
                            .setAuthors(authors);
                    bookService.save(book);
                }
                response.sendRedirect("/book/all");
                break;
            case "edit":
                if (hasAdminRole) {
                    id = Integer.parseInt(request.getParameter("id"));
                    bookName = request.getParameter("name");
                    bookDescription = request.getParameter("description");
                    authorIds = request.getParameterValues("authors");
                    authors = new HashSet<>();
                    for (String authorIdString : authorIds) {
                        Integer authorId = Integer.parseInt(authorIdString);
                        authors.add(authorService.getById(authorId));
                    }
                    book = new Book();
                    book.setId(id)
                            .setName(bookName)
                            .setDescription(bookDescription)
                            .setAuthors(authors);
                    bookService.update(book);
                }
                response.sendRedirect("/book/all");
                break;
            case "delete":
                if (hasAdminRole) {
                    try {
                        id = Integer.parseInt(request.getParameter("bookId"));
                    } catch (NumberFormatException e) {
                        response.sendRedirect("/book/all");
                        logger.error("Something gone wrong", e);
                        break;
                    }
                    book = bookService.getById(id);
                    bookService.delete(book);
                }
                response.sendRedirect("/book/all");
                break;
            default:
                response.sendRedirect("/");
                logger.warn("POST Page doesn't exist: " + request.getPathInfo());

        }
    }
}
