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
                    <h4>${author.name}</h4>
                    <p>${author.lastName}<br/>
                </div>
                <c:if test="${hasAdminRole}">
                <div class="col-sm-6 col-md-8">

                        <a href="/author/edit/${author.id}">
                            <button class="btn btn-primary trn">Edit author
                            </button>
                        </a>
                </div>
                <div class="col-sm-6 col-md-8">
                    <form method="POST">
                        <button class="btn btn-danger trn" name="authorId" value="${author.id}">
                            Delete book
                        </button>
                    </form>
                </div>
                </c:if>
            </div>
        </div>
    </div>
</div>



</body>
</html>