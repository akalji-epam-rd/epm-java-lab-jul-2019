package com.epam.lab.library.controller;

import com.epam.lab.library.domain.Author;
import com.epam.lab.library.domain.Role;
import com.epam.lab.library.domain.User;
import com.epam.lab.library.service.AuthorService;
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
import java.util.HashSet;
import java.util.Set;

/**
 * Author interaction class
 */
@WebServlet(name = "AuthorController", urlPatterns = "/author/*", loadOnStartup = 1)
public class AuthorController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    ViewResolver resolver = new ViewResolver();
    AuthorService authorService = new AuthorService();

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
                request.setAttribute("authors", authorService.getAll());
                request.getRequestDispatcher(view).forward(request, response);
                break;
            case "get":
                id = resolver.getIdFromRequest(request);
                if (id != null) {
                    request.setAttribute("author", authorService.getById(id));
                } else {
                    request.setAttribute("message", "Book is absent");
                }
                request.getRequestDispatcher(view).forward(request, response);
                break;
            case "add":
                if (!hasAdminRole) {
                    response.sendRedirect("/book/all");
                } else {
                    request.getRequestDispatcher(view).forward(request, response);
                }
                break;
            case "edit":
                id = resolver.getIdFromRequest(request);
                if (!hasAdminRole) {
                    response.sendRedirect("/book/all");
                } else {
                    if (id != null) {
                        request.setAttribute("author", authorService.getById(id));
                    } else {
                        request.setAttribute("message", "Book is absent");
                    }
                    request.getRequestDispatcher(view).forward(request, response);
                }
                break;
            default:
                response.sendRedirect("/");
                logger.warn("Page doesn't exist: " + request.getPathInfo());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Set<Role> roles = (HashSet)request.getSession().getAttribute("roles");
        boolean hasAdminRole = RoleUtil.hasRole("Administrator", roles);
        request.setAttribute("hasAdminRole", hasAdminRole);
        String[] info = resolver.getViewPath(request);
        String type = info[0];
        Integer id;
        String authorName;
        String authorLastName;
        Author author;
        switch (type) {
            case "add":
                if (hasAdminRole) {
                    authorName = request.getParameter("name");
                    authorLastName = request.getParameter("lastname");
                    author = new Author();
                    author.setName(authorName)
                            .setLastName(authorLastName);
                    authorService.save(author);
                }
                response.sendRedirect("/author/all");
                break;
            case "edit":
                if (hasAdminRole) {
                    id = Integer.parseInt(request.getParameter("id"));
                    authorName = request.getParameter("name");
                    authorLastName = request.getParameter("lastname");
                    author = new Author();
                    author.setId(id)
                            .setName(authorName)
                            .setLastName(authorLastName);
                    authorService.update(author);
                }
                response.sendRedirect("/author/all");
                break;
            case "delete":
                try {
                    id = Integer.parseInt(request.getParameter("authorId"));
                } catch (NumberFormatException e) {
                    response.sendRedirect("/author/all");
                    logger.error("Something gone wrong", e);
                    break;
                }
                author = authorService.getById(id);
                authorService.delete(author);
                response.sendRedirect("/author/all");
                break;
            default:
                response.sendRedirect("/");
                logger.warn("Page doesn't exist: " + request.getPathInfo());

        }
    }
}
