<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>First JSP</title>
</head>
<body>
    <div><c:out value="${book.name}"/></div>
    <div><c:out value="${book.description}"/></div>
</body>
</html>