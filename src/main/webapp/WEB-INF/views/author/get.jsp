<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <title>Author</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="../../../static/css/pretty.css">
    <link rel="stylesheet" type="text/css" href="../../../static/css/bootstrap.min.css">

</head>
<body>

<jsp:include page='/WEB-INF/views/header.jsp'>
    <jsp:param name="articleId" value=""/>
</jsp:include>

<div><c:out value="${author.name}"/></div>
<div><c:out value="${author.lastName}"/></div>


<a href="/book/edit/${book.id}">Edit book</a>

<form method="POST" action="/author//delete/">
    <button type="submit" name="bookId" value="${author.id}">Edit</button>
</form>
</body>
</html>