<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <title>Users</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="../../../static/css/pretty.css">
    <link rel="stylesheet" href="../../../static/css/bootstrap.min.css">

</head>

<body>

<jsp:include page='/WEB-INF/views/header.jsp'>
    <jsp:param name="articleId" value=""/>
</jsp:include>

<div class="btnsLine">
    <a class="frmBtn" href="/addUser">Add new user</a>
</div>

<div>
    <form class="btnsLine" th:action="@{/findProduct}" method="post">
        <input type="text" name="bookName" placeholder="Users's name">
        <input class="frmBtn orange" type="submit" value="Filter"/>
    </form>
</div>

<div class="table">
    <table>

        <thead>
        <tr>
            <th>id</th>
            <th>Name</th>
            <th>LastName</th>
            <th>Email</th>
            <th>Password</th>
            <th>Role</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td><c:out value="${user.id}"/> </td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.lastName}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.password}" /></td>
                <td>
                    <c:forEach items="${user.roles}" var="role" varStatus="loop">
                        <c:out value="${role.name}"/>
                        <c:if test="${!loop.last}">,</c:if>
                        <%@ page trimDirectiveWhitespaces="true" %>
                    </c:forEach>
                </td>
                <td><a class="fn edit" th:href="@{/editProduct(id=${product.id})}" title="Edit">&rsaquo;</a></td>
                <td><a class="fn delete" th:href="@{/deleteProduct(id=${product.id})}" title="Delete">&times;</a></td>
            </tr>
        </c:forEach>
        </tbody>

    </table>

</div>

</body>
</html>