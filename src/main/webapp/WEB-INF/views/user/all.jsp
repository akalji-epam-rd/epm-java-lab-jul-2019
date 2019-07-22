<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="../../../static/css/bootstrap.min.css">
<script src="../../../static/js/bootstrap-native.min.js"></script>

<html>
<head>
    <title>Users</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>

<body>

<jsp:include page='/WEB-INF/views/header.jsp'>
    <jsp:param name="articleId" value=""/>
</jsp:include>

<div class="container">
    <h2>Library Users</h2>

    <button class="btn btn-primary" id="addUserBtn" type="submit">
        Add User
    </button>

    <div>
        <table id="users_table" class="table table-striped">
            <thead>
            <tr>
                <td class="trn" data-trn-key="Name">Name</td>
                <td class="trn" data-trn-key="LastName">LastName</td>
                <td class="trn" data-trn-key="Email">Email</td>
                <td class="trn" data-trn-key="Password">Password</td>
                <td class="trn" data-trn-key="Roles">Role</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td><c:out value="${user.name}"/></td>
                    <td><c:out value="${user.lastName}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.password}"/></td>
                    <td>
                        <c:forEach items="${user.roles}" var="role" varStatus="loop">
                            <c:out value="${role.name}"/>
                            <c:if test="${!loop.last}">,</c:if>
                            <%@ page trimDirectiveWhitespaces="true" %>
                        </c:forEach>
                    </td>
                    <td>
                        <button class="btn btn-primary" type="submit">
                            Edit User
                        </button>
                    </td>
                    <td>
                        <button class="btn btn-danger trn" type="submit">
                            Delete User
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>
</div>
</body>
</html>