<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <title>Add book</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="../../../static/css/pretty.css">
    <link rel="stylesheet" href="../../../static/css/bootstrap.min.css">

</head>
<body>

<jsp:include page='/WEB-INF/views/header.jsp'>
    <jsp:param name="articleId" value=""/>
</jsp:include>

<h2 class="header">Add new book</h2>

<form action="#" th:action="@{/addProduct}" th:object="${product}" method="post">
    <table class="table">
        <tr>
            <td>Name:</td>
            <td><input type="text" th:field="*{name}"/></td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><input type="text" th:field="*{description}"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <th:block th:each="category : ${categories}">
                    <input type="checkbox" name="categoryCheckbox" th:value="${category.id}">
                    <label th:text="${category.name}"></label>
                </th:block>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Submit"/> <input type="reset" value="Reset"/>
            </td>
        </tr>
    </table>
</form>

</body>
</html>