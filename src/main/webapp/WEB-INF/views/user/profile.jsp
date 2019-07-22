<%@ page pageEncoding="utf-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="../../../static/css/bootstrap.min.css">
<script src="../../../static/js/bootstrap-native.min.js"></script>

<style>
    .glyphicon {  margin-bottom: 10px;margin-right: 10px;}

    small {
        display: block;
        line-height: 1.428571429;
        color: #999;
    }
</style>

<html>
<head>
    <title>User Profile</title>
</head>
<body>

<jsp:include page='/WEB-INF/views/header.jsp'>
    <jsp:param name="articleId" value=""/>
</jsp:include>

<div class="container">
    <div class="row">
        <div class="col-xs-12 col-sm-6 col-md-6">
            <div class="well well-sm">
                <div class="row">
                    <div class="col-sm-6 col-md-4">
                        <img src="http://placehold.it/380x500" alt="" class="img-rounded img-responsive" />
                    </div>
                    <div class="col-sm-6 col-md-8">
                        <h4>${user.name} ${user.lastName}</h4>
                        <p>
                            <i class="glyphicon glyphicon-envelope"></i>${user.email}<br />
                    </div>
                </div>
            </div>
        </div>
    </div>

    <table id="items_table" class="table table-striped">
        <thead>
        <tr>
            <td class="trn" data-trn-key="Book">Book</td>
            <td class="trn" data-trn-key="Status">Status</td>
            <td></td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${items}" var="item">
            <tr>
                <td><c:out value="${item.book.name}"/></td>
                <td><c:out value="${item.status.name}"/></td>
                <td>
                    <c:if test="${item.status.name != 'ordered'}">
                        <form method="POST" action="${pageContext.request.contextPath}/user/profile/confirmOrder" >
                            <button class="btn btn-default" type="submit" value="${item.id}" name="itemId">
                                Return book
                            </button>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>



</body>
</html>
