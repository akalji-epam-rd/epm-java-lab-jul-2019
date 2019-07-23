package com.epam.lab.library.auth;

import com.epam.lab.library.controller.ItemAjaxController;
import com.epam.lab.library.dao.interfaces.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.nonNull;

/**
 * AuthFilter class implements logic
 * for filtering of incoming users
 */
public class AuthFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(ItemAjaxController.class);

    /**
     * Filtering every request to servlet
     * if session attribute and authentication's parameters is not providing redirect user to welcome page
     * if authentication parameters provided, then try to find user, and if he exist give him additional features
     *
     * @param servletRequest  Servlet request
     * @param servletResponse Servlet response
     * @param filterChain     Filter chain
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse res = (HttpServletResponse) servletResponse;

        final HttpSession session = req.getSession();

        boolean isStaticResource = req.getRequestURI().startsWith("/static/");
        boolean isLoginRequested = req.getRequestURI().startsWith("/login");

        boolean isRegisterRequested = req.getRequestURI().startsWith("/user/registernew");

        boolean isBookFind = req.getRequestURI().startsWith("/book/find");
        boolean isBookGet = req.getRequestURI().startsWith("/book/get");
        boolean isAuthorAll = req.getRequestURI().startsWith("/author/all");
        boolean isAuthorFind = req.getRequestURI().startsWith("/author/find");
        boolean isAuthorGet = req.getRequestURI().startsWith("/author/get");
        boolean isRegisterRequested = req.getRequestURI().startsWith("/registernew");


        if (isStaticResource) {
            filterChain.doFilter(req, res);
        } else if (isBookFind) {
            filterChain.doFilter(req, res);
        } else if (isBookGet) {
            filterChain.doFilter(req, res);
        } else if (isAuthorGet) {
            filterChain.doFilter(req, res);
        } else if (isAuthorAll) {
            filterChain.doFilter(req, res);
        } else if (isAuthorFind) {
            filterChain.doFilter(req, res);
        } else if (isLoginRequested) {
            req.getRequestDispatcher("/login").forward(req, res);
        } else if (isRegisterRequested) {
            filterChain.doFilter(req, res);
        } else if (nonNull(session) &&
                nonNull(session.getAttribute("email")) &&
                nonNull(session.getAttribute("password"))) {
            LOG.info("user has already logged in");
            filterChain.doFilter(req, res);
        } else {
            LOG.info("user have to register in the system or provide valid creds");
            req.getRequestDispatcher("/book/all").forward(req, res);
        }
    }
}
