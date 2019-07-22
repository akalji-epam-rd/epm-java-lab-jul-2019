package com.epam.lab.library.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * View resolver class
 */
public class ViewResolver {

    private static final Logger logger = LoggerFactory.getLogger(ViewResolver.class);

    /**
     * resolve url according to REST
     *
     * @param request
     * @return [0] - type of request like add, edit etc. [1] - path to view
     */
    public String[] getViewPath(HttpServletRequest request) {
        final String ROOT = "/WEB-INF/views" + request.getServletPath();
        String pathInfo = request.getPathInfo().substring(1);
        String type = pathInfo.split("/")[0];
        return new String[]{type, ROOT + "/" + type + ".jsp"};
    }

    /**
     * @param request
     * @return id that must exists at the end of request url
     */
    public Integer getIdFromRequest(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        String id = pathInfo.substring(pathInfo.lastIndexOf("/") + 1);
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
