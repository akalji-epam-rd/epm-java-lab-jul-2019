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

<div class="row">
    <div class="col-xs-12 col-sm-6 col-md-6">
        <div class="well well-sm">
            <div class="row">
                <div class="col-sm-6 col-md-4">
                    <img src="http://placehold.it/380x500" alt="" class="img-rounded img-responsive"/>
                </div>
                <div class="col-sm-6 col-md-8">
                    <h4>${book.name}</h4>
                    <p>${book.description}<br/>
                </div>
                <c:if test="${hasAdminRole}">
                <div class="col-sm-6 col-md-8">

                        <a href="/book/edit/${book.id}">
                            <button class="btn btn-primary trn">Edit book
                            </button>
                        </a>
                </div>
                <div class="col-sm-6 col-md-8">
                    <form method="POST">
                        <button class="btn btn-danger trn" name="bookId" value="${book.id}">
                            Delete book
                        </button>
                    </form>
                </div>
                </c:if>
                <div class="col-sm-6 col-md-8">
                    <form method="POST" action="/book/order/">
                        <button class="btn btn-default trn" type="submit" name="bookId" value="${book.id}">Order</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>



</body>
</html>