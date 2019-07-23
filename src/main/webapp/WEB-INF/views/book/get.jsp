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


    <a href="/book/edit/${book.id}">Edit book</a>

    <form method="POST" action="/book/order/">
        <button type="submit" name="bookId" value="${book.id}">Order</button>
    </form>

    <form method="POST" action="/book/delete/">
         <button type="submit" name="bookId" value="${book.id}">Edit</button>
    </form>
</body>
</html>