<%--
  Created by IntelliJ IDEA.
  User: Fedor
  Date: 18.07.2019
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="true" %>

<style>
    .glyphicon {  margin-bottom: 10px;margin-right: 10px;}

    small {
        display: block;
        line-height: 1.428571429;
        color: #999;
    }
</style>

<html>
<head>
    <title>Header</title>
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">

        <div class="navbar-header">
            <a class="navbar-brand" href="#">Library</a>
        </div>

        <ul class="nav navbar-nav">
            <li><a href="<c:url value='/book/all'/>">Books</a></li>
            <li><a href="<c:url value='/author/all'/>">Authors</a></li>
            <c:if test="${hasAdminRole == true}">
                <li><a href="<c:url value='/item/all'/>">Items</a></li>
                <li><a href="<c:url value='/user/all'/>">Users</a></li>
                <li><a href="<c:url value='/book/add'/>">Add book</a></li>
                <li><a href="<c:url value='/author/add'/>">Add author</a></li>
            </c:if>
        </ul>

        <ul class="nav navbar-nav navbar-right">

            <c:if test="${empty email || empty password}">
                <li><a href="<c:url value='/login'/>"><span class="glyphicon glyphicon-log-in"></span>Login</a></li>
            </c:if>
            <c:if test="${not empty email && not empty password}">
                <li><a href="<c:url value='/user/profile'/>"><span class="glyphicon glyphicon-user"></span>Profile</a></li>
                <li><a href="<c:url value='/logout'/>"><span class="glyphicon glyphicon-log-out"></span>Logout</a></li>
            </c:if>
        </ul>

    </div>
</nav>
</body>
</html>
