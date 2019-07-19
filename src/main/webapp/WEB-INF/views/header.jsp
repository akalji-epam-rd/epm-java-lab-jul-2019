<%--
  Created by IntelliJ IDEA.
  User: Fedor
  Date: 18.07.2019
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <li><a href="<c:url value='/allItems'/>">Items</a></li>
            <li><a href="<c:url value='/user/all'/>">Users</a></li>
        </ul>

        <ul class="nav navbar-nav navbar-right">
            <li><a href="<c:url value='/logout'/>"><span class="glyphicon glyphicon-log-in"></span>Logout</a></li>
        </ul>

    </div>
</nav>
</body>
</html>
