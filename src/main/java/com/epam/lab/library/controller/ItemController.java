package com.epam.lab.library.controller;

import com.epam.lab.library.dao.BookDaoImpl;
import com.epam.lab.library.dao.ItemDaoImpl;
import com.epam.lab.library.dao.StatusesDaoImpl;
import com.epam.lab.library.dao.UserDaoImpl;
import com.epam.lab.library.dao.interfaces.BookDao;
import com.epam.lab.library.dao.interfaces.ItemDao;
import com.epam.lab.library.dao.interfaces.StatusesDao;
import com.epam.lab.library.dao.interfaces.UserDao;
import com.epam.lab.library.domain.Book;
import com.epam.lab.library.domain.Item;
import com.epam.lab.library.domain.Status;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.stream.Collectors;

@WebServlet(loadOnStartup = 1)
public class ItemController extends HttpServlet {

    private ItemDao itemDao = new ItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private StatusesDao statusesDao = new StatusesDaoImpl();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        try {
            req.setAttribute("books", bookDao.getAll()
                    .stream().sorted(Comparator.comparingInt(Book::getId))
                    .collect(Collectors.toList()));
            req.setAttribute("users", userDao.getAll());
            req.setAttribute("statuses", statusesDao.getAll()
                    .stream().sorted(Comparator.comparingInt(Status::getId))
                    .collect(Collectors.toList()));
            req.setAttribute("items", itemDao.getAll()
                    .stream().sorted(Comparator.comparingInt(Item::getId))
                    .collect(Collectors.toList()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.getRequestDispatcher("items.jsp").forward(req, resp);

        //        System.out.println(req.getQueryString());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
