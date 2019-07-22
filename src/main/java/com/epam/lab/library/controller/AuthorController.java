package com.epam.lab.library.controller;

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
        Integer id = resolver.getIdFromRequest(request);
        switch (type) {
            case "all":
                request.setAttribute("authors", authorService.getAll());
                request.getRequestDispatcher(view).forward(request, response);
                break;
            case "get":
                if (id != null) {
                    request.setAttribute("author", authorService.getById(id));
                    request.getRequestDispatcher(view).forward(request, response);
                } else {
                    response.sendRedirect("/author/all");
                }
                break;
            case "add":
                request.getRequestDispatcher(view).forward(request, response);
                break;
            case "edit":
                if (id != null) {
                    request.setAttribute("author", authorService.getById(id));
                }
                if (!hasAdminRole) {
                    response.sendRedirect("/author/all");
                    break;
                } else {
                    request.getRequestDispatcher(view).forward(request, response);
                    break;
                }
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
        String view = info[1];
        Integer id = resolver.getIdFromRequest(request);
        User user = null;
        switch (type) {
            case "add":

            case "edit":
                if (!hasAdminRole) {
                    response.sendRedirect("/author/all");
                    break;
                }
            case "delete":
                id = resolver.getIdFromRequest(request);
                authorService.delete(authorService.getById(id));
                //TODO Add logic for delete
                break;
            default:
                response.sendRedirect("/");
                logger.warn("Page doesn't exist: " + request.getPathInfo());

        }
    }
}
