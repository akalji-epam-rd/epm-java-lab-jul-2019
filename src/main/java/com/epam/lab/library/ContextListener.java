package com.epam.lab.library;

import com.epam.lab.library.dao.UserDaoImpl;
import com.epam.lab.library.dao.interfaces.UserDao;
import com.epam.lab.library.domain.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@WebListener
public class ContextListener implements ServletContextListener{

    private AtomicReference<UserDao> userDao;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        userDao = new AtomicReference<>(new UserDaoImpl());

        final ServletContext servletContext = sce.getServletContext();

        servletContext.setAttribute("userDao", userDao);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        userDao = null;

    }
}
