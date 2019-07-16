<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <title>Books</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="../../../static/css/pretty.css">
    <link rel="stylesheet" href="../../../static/css/bootstrap.min.css">

</head>

<body>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Library</a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="<c:url value='/book/all'/>">Books</a></li>
            <li><a href="<c:url value='/allItems'/>">Items</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="<c:url value='/logout'/>"><span class="glyphicon glyphicon-log-in"></span>Logout</a></li>
        </ul>
    </div>
</nav>


<div class="btnsLine">
    <a class="frmBtn" href="/addProduct">Add new book</a>
    <a class="frmBtn" href="/addCategory">Add new author</a>
</div>

<div>
    <form class="btnsLine" th:action="@{/findProduct}" method="post">
        <input type="text" name="bookName" placeholder="Book's name">
        <input type="text" name="authorName" placeholder="Author's name">
        <input class="frmBtn orange" type="submit" value="Filter"/>
    </form>
</div>

<div class="table">
    <table>

        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Authors</th>
            <th></th>
            <th></th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${books}" var="book">
            <tr>
                <td><c:out value="${book.name}"/></td>
                <td><c:out value="${book.description}"/></td>
                <td>
                    <c:forEach items="${book.authors}" var="author">
                        <span><c:out value="${author.name} ${author.lastName}"/></span>
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