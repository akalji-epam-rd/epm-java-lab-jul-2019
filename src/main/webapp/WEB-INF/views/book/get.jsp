<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>First JSP</title>
</head>
<body>

<jsp:include page='/WEB-INF/views/header.jsp'>
    <jsp:param name="articleId" value=""/>
</jsp:include>

    <div><c:out value="${book.name}"/></div>
    <div><c:out value="${book.description}"/></div>
    <div><a href="/order">Order this book</a>></div>

</body>
</html>