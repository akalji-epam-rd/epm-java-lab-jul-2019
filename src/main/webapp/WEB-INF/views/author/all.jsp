<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>First JSP</title>
</head>
<body>
    <c:forEach items="${authors}" var="author">
        <c:out value="${author.name}" />
        <c:out value="${author.lastName}" />
    </c:forEach>
</body>
</html>