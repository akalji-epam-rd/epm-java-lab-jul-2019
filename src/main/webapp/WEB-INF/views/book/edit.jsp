<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <title>Edit book</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="../../../static/css/pretty.css">
    <link rel="stylesheet" href="../../../static/css/bootstrap.min.css">

</head>
<body>

<jsp:include page='/WEB-INF/views/header.jsp'>
    <jsp:param name="articleId" value=""/>
</jsp:include>

<h2 class="header">Edit book</h2>

<form class="form-horizontal" action='${pageContext.request.contextPath}/book/edit' method="POST">
    <fieldset>
        <div id="legend">
            <legend class="">Edit book</legend>
        </div>
        <input name="id" value="${book.id}" hidden/>
        <div class="control-group">
            <label class="control-label" for="name">Name</label>
            <div class="controls">
                <input type="text" id="name" value="${book.name}" name="name" placeholder="" class="input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="description">Description</label>
            <div class="controls">
                <textarea type="text" id="description" name="description" placeholder="" class="input-xlarge">
                    <c:out value="${book.description}" />
                    <%@ page trimDirectiveWhitespaces="true" %>
                </textarea>
            </div>
        </div>

        <select name="authors" multiple>
            <c:forEach items="${authors}" var="author">
                <c:forEach items="${book.authors}" var="bookauthor">
                    <c:choose>
                        <c:when test="${bookauthor.id == author.id}">
                            <option value="${author.id}" selected>${author.name} ${author.lastName}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${author.id}">${author.name} ${author.lastName}</option>
                        </c:otherwise>
                    </c:choose>

                </c:forEach>
            </c:forEach>
        </select>

        <div class="control-group">
            <div class="controls">
                <button type="submit" class="btn btn-success">Edit Book</button>
            </div>
        </div>
    </fieldset>
</form>

</body>
</html>