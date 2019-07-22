package com.epam.lab.library.util.connectionpool;

import javax.servlet.http.HttpServletRequest;

public class ViewResolver {

    /**
     * resolve url according to REST
     * @param request
     * @return [0] - type of request like add, edit etc. [1] - path to view
     */
    public String[] getViewPath(HttpServletRequest request) {
        final String root = "/WEB-INF/views" + request.getServletPath();
        String pathInfo = request.getPathInfo().substring(1);
        String type = pathInfo.split("/")[0];
        return new String[] {type, root + "/" + type + ".jsp"};
    }
}
