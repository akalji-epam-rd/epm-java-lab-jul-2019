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

<div>
    <form class="form-horizontal" action='${pageContext.request.contextPath}/author/find' method="POST">
        <fieldset>
            <div id="legend">
                <legend class="">Find Author</legend>
            </div>
            <div class="control-group">
                <label class="control-label" for="authorLastName">Name</label>
                <div class="controls">
                    <input type="text" id="authorLastName" name="authorLastName" placeholder="" class="input-xlarge">
                </div>
            </div>

            <div class="control-group">
                <div class="controls">
                    <button type="submit" class="btn btn-success">Filter</button>
                </div>
            </div>
        </fieldset>
    </form>
</div>

<div class="container">
    <table>

        <thead>
        <tr>
            <th>Name</th>
            <th>Last name</th>
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
                <td><form action="/author/edit/${author.id}" method="post" target="myFrame" id="myForm"></form></td>
                <%--<td><a class="fn delete" th:href="@{/deleteProduct(id=${product.id})}" title="Delete">&times;</a></td>--%>
            </tr>
        </c:forEach>
        </tbody>

    </table>

</div>

</body>
</html>