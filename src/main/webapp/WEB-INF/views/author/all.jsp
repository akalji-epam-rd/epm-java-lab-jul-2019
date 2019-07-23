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
    <form class="form-horizontal" action='${pageContext.request.contextPath}/author/find' method="POST">
        <fieldset>
            <div id="legend">
                <legend class="">Find book</legend>
            </div>
            <div class="control-group">
                <label class="control-label" for="authorName">Name</label>
                <div class="controls">
                    <input type="text" id="authorName" name="authorName" placeholder="" class="input-xlarge">
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
            <th>Last Name</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${authors}" var="author">
            <tr>
                <td><a href="/author/get/${author.id}"> <c:out value="${author.name}"/></a></td>
                <td><a href="/author/get/${author.id}"> <c:out value="${author.lastName}"/></a></td>

                <c:if test="${hasAdminRole}">
                    <td>
                        <a href="/author/edit/${author.id}">
                            <button class="btn btn-primary trn">Edit author
                            </button>
                        </a>
                    </td>
                    <td>

                        <form method="POST">
                            <button class="btn btn-danger trn" name="authorId" value="${author.id}">
                                Delete author
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