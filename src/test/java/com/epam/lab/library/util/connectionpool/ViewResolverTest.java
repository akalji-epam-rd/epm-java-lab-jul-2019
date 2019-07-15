package com.epam.lab.library.util.connectionpool;


import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ViewResolverTest {

    ViewResolver resolver = new ViewResolver();

    @Test
    public void getViewPath() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String expectedResult;

        when(request.getServletPath()).thenReturn("/author");
        when(request.getPathInfo()).thenReturn("/add");
        expectedResult = "/WEB-INF/views/author/add.jsp";
        assertEquals(expectedResult, resolver.getViewPath(request));

        when(request.getServletPath()).thenReturn("/book");
        when(request.getPathInfo()).thenReturn("/edit/45");
        expectedResult = "/WEB-INF/views/book/edit.jsp";
        assertEquals(expectedResult, resolver.getViewPath(request));

        when(request.getServletPath()).thenReturn("/item");
        when(request.getPathInfo()).thenReturn("/all");
        expectedResult = "/WEB-INF/views/item/all.jsp";
        assertEquals(expectedResult, resolver.getViewPath(request));

        when(request.getServletPath()).thenReturn("/user");
        when(request.getPathInfo()).thenReturn("/delete/whyNot");
        expectedResult = "/WEB-INF/views/user/delete.jsp";
        assertEquals(expectedResult, resolver.getViewPath(request));
    }
}