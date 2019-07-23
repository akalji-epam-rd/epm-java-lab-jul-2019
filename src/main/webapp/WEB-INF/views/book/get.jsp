<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <title>Book</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="../../../static/css/pretty.css">
    <link rel="stylesheet" type="text/css" href="../../../static/css/bootstrap.min.css">

</head>
<body>

<jsp:include page='/WEB-INF/views/header.jsp'>
    <jsp:param name="articleId" value=""/>
</jsp:include>

    <div><c:out value="${book.name}"/></div>
    <div><c:out value="${book.description}"/></div>

    <c:if test="${hasAdminRole}">
        <a href="/book/edit/${book.id}">
            <button class="btn btn-primary trn">Edit book
            </button>
        </a>
        <form method="POST">
            <button class="btn btn-danger trn" name="bookId" value="${book.id}">
                Delete book
            </button>
        </form>
    </c:if>

    <form method="POST" action="/book/order/">
        <button type="submit" name="bookId" value="${book.id}">Order</button>
    </form>

</body>
</html>