<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="../../../static/css/bootstrap.min.css">
<script src="../../../static/js/bootstrap-native.min.js"></script>

<html>
<head>
    <title>Edit user</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>

<body>

<jsp:include page='/WEB-INF/views/header.jsp'>
    <jsp:param name="articleId" value=""/>
</jsp:include>


<form class="form-horizontal" action='/user/edit' method="POST">
    <fieldset>

        <div id="legend">
            <legend class="">Edit user</legend>
        </div>
        <input name="id" value="${user.id}" hidden>
        <div class="control-group">
            <!-- Username -->
            <label class="control-label"  for="username">Username</label>
            <div class="controls">
                <input type="text" id="username" name="username" value="${user.name}" placeholder="" class="input-xlarge">
                <p class="help-block">Username can contain any letters or numbers, without spaces</p>
            </div>
        </div>
        <div class="control-group">
            <!-- UserLastname -->
            <label class="control-label"  for="userLastName">User last name</label>
            <div class="controls">
                <input type="text" id="userLastName" name="userLastName" value="${user.lastName}" placeholder="" class="input-xlarge">
                <p class="help-block">UserLastName can contain any letters or numbers, without spaces</p>
            </div>
        </div>

        <div class="control-group">
            <!-- E-mail -->
            <label class="control-label" for="email">E-mail</label>
            <div class="controls">
                <input type="text" id="email" name="email" value="${user.email}" placeholder="" class="input-xlarge">
                <p class="help-block">Please provide your E-mail</p>
            </div>
        </div>

        <div class="control-group">
            <!-- Password-->
            <label class="control-label" for="password">Password</label>
            <div class="controls">
                <input type="password" id="password" name="password" value="${user.password}" placeholder="" class="input-xlarge">
                <p class="help-block">Password should be at least 4 characters</p>
            </div>
        </div>

        <select name="roles" multiple>
            <c:forEach items="${roles}" var="role">
                    <c:choose>
                        <c:when test="${role.id == role.id}">
                            <option value="${role.id}" selected>${role.name}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${role.id}">${role.name}</option>
                        </c:otherwise>
                    </c:choose>
            </c:forEach>
        </select>


        <div class="control-group">
            <!-- Button -->
            <div class="controls">
                <button type="submit" class="btn btn-success">Save</button>
            </div>
        </div>
    </fieldset>
</form>

</body>
</html>
