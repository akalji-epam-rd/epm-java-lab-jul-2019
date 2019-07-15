package com.epam.lab.library.util.connectionpool;

import javax.servlet.http.HttpServletRequest;

public class ViewResolver {

    /**
     * Parses request address according to REST style
     * @param request
     * @return path of respective view
     */
    public String getViewPath(HttpServletRequest request) {
        final String root = "/WEB-INF/views" + request.getServletPath();
        String pathInfo = request.getPathInfo().substring(1);
        String path = pathInfo.split("/")[0];
        return root + "/" + path + ".jsp";
    }
}
