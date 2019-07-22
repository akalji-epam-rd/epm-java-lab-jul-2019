package com.epam.lab.library.auth;

import com.epam.lab.library.dao.UserDaoImpl;
import com.epam.lab.library.dao.interfaces.UserDao;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Context Listener class
 */
@WebListener
public class ContextListener implements ServletContextListener {

    private AtomicReference<UserDao> userDao;

    /**
     * Setting userDao attribute to servlet context
     *
     * @param sce Servlet context event
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        userDao = new AtomicReference<>(new UserDaoImpl());

        final ServletContext servletContext = sce.getServletContext();

        servletContext.setAttribute("userDao", userDao);
    }

    /**
     * Delete userDao attribute
     *
     * @param sce Servlet context event
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        userDao = null;
    }
}
