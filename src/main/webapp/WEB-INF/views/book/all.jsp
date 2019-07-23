<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>

    <title>Books</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="../../../static/css/bootstrap.min.css">

</head>

<body>

<jsp:include page='/WEB-INF/views/header.jsp'>
    <jsp:param name="articleId" value=""/>
</jsp:include>

<div>
    <form class="form-horizontal" action='${pageContext.request.contextPath}/book/find' method="POST">
        <fieldset>
            <div id="legend">
                <legend class="">Find book</legend>
            </div>
            <div class="control-group">
                <label class="control-label" for="bookName">Name</label>
                <div class="controls">
                    <input type="text" id="bookName" name="bookName" placeholder="" class="input-xlarge">
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="authorLastName">Author</label>
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
    <table class="table table-striped">

        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Authors</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${books}" var="book">
            <tr>
                <td><a href="/book/get/${book.id}"> <c:out value="${book.name}"/></a></td>
                <td><c:out value="${book.description}"/></td>
                <td>
                    <c:forEach items="${book.authors}" var="author">
                        <span><a href="/author/get/${author.id}"><c:out value="${author.name} ${author.lastName}"/></a></span>
                    </c:forEach>
                </td>
                <c:if test="${hasAdminRole}">
                    <td>
                        <a href="/book/edit/${book.id}">
                            <button class="btn btn-primary trn">Edit book
                            </button>
                        </a>
                    </td>
                    <td>

                        <form method="POST">
                            <button class="btn btn-danger trn" name="bookId" value="${book.id}">
                                Delete book
                            </button>
                        </form>

                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>

    </table>

</div>

</body>
</html>