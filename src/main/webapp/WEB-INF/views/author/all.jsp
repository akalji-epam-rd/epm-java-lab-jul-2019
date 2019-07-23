<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>

    <title>Authors</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="../../../static/css/bootstrap.min.css">

</head>

<body>

<jsp:include page='/WEB-INF/views/header.jsp'>
    <jsp:param name="articleId" value=""/>
</jsp:include>

<div class="btnsLine">
    <a class="frmBtn" href="/book/add">Add new book</a>
    <a class="frmBtn" href="/author/add">Add new author</a>
</div>

<div>
    <form class="btnsLine" action='${pageContext.request.contextPath}/author/find' method="post">
        <input type="text" name="authorLastName" placeholder="Author's last name">
        <input class="frmBtn orange" type="submit" value="Filter"/>
    </form>
</div>

<div class="container">
    <table>

        <thead>
        <tr>
            <th>Name</th>
            <th>LastName</th>
            <th></th>
            <th></th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${authors}" var="author">
            <tr>
                <td><c:out value="${author.name}"/></td>
                <td><c:out value="${author.lastName}"/></td>
                <td><a href="/author/edit/${author.id}" title="Edit">&rsaquo;</a></td>
                <td><a class="fn delete" th:href="@{/deleteProduct(id=${product.id})}" title="Delete">&times;</a></td>
            </tr>
        </c:forEach>
        </tbody>

    </table>

</div>

</body>
</html>