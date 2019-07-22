package com.epam.lab.library.controller;

import com.epam.lab.library.dao.BookDaoImpl;
import com.epam.lab.library.dao.StatusesDaoImpl;
import com.epam.lab.library.dao.UserDaoImpl;
import com.epam.lab.library.dao.interfaces.BookDao;
import com.epam.lab.library.dao.interfaces.StatusesDao;
import com.epam.lab.library.dao.interfaces.UserDao;
import com.epam.lab.library.domain.Book;
import com.epam.lab.library.domain.Item;
import com.epam.lab.library.domain.Role;
import com.epam.lab.library.domain.Status;
import com.epam.lab.library.service.ItemServiceImpl;
import com.epam.lab.library.service.interfaces.ItemService;
import com.epam.lab.library.util.RoleUtil;
import com.epam.lab.library.util.pagination.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet(loadOnStartup = 1)
public class ItemController extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ItemController.class);
    private ItemService itemService = new ItemServiceImpl();
    private BookDao bookDao = new BookDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private StatusesDao statusesDao = new StatusesDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Set<Role> roles = (HashSet)session.getAttribute("roles");

        boolean hasAdminRole = RoleUtil.hasRole("Administrator", roles);
        if (!hasAdminRole) {
            resp.sendRedirect("/book/all");
        }
        req.setAttribute("hasAdminRole", hasAdminRole);

        try {
            req.setAttribute("books", bookDao.getAll()
                    .stream().sorted(Comparator.comparingInt(Book::getId))
                    .collect(Collectors.toList()));
            req.setAttribute("users", userDao.getAll());
            req.setAttribute("statuses", statusesDao.getAll()
                    .stream().sorted(Comparator.comparingInt(Status::getId))
                    .collect(Collectors.toList()));
            req.setAttribute("items", itemService.getAll(null, new Paging())
                    .stream().sorted(Comparator.comparingInt(Item::getId))
                    .collect(Collectors.toList()));
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);

        }

        req.getRequestDispatcher("/WEB-INF/views/item/all.jsp").forward(req, resp);
    }
}
