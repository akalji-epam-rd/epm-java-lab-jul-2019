<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <title>Add book</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="../../../static/css/bootstrap.min.css">

</head>
<body>

<jsp:include page='/WEB-INF/views/header.jsp'>
    <jsp:param name="articleId" value=""/>
</jsp:include>

<h2 class="header">Add new book</h2>

<div class="row">
    <div class="col-xs-12 col-sm-6 col-md-6">
        <div class="well well-sm">
            <form class="form-horizontal" action='${pageContext.request.contextPath}/book/add' method="POST">
                <div class="row">
                    <div class="col-sm-6 col-md-4">
                        <legend class="">Add new author</legend>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-md-4">
                        <label class="control-label" for="name">Name</label>
                        <div class="controls">
                            <input type="text" id="name" name="name" placeholder="" class="input-xlarge">
                        </div>
                    </div>
                </div>
                <div class="row">
                       <div class="col-sm-6 col-md-4">
                                <label class="control-label" for="name">Name</label>
                                    <div class="controls">
                               <input type="text" id="name" name="name" placeholder="" class="input-xlarge">
                         </div>
                    </div>
                </div>
                </div>
                <div class="row">
                    <div class="col-sm-6 col-md-4">
                        <div class="controls">
                            <button type="submit" class="btn btn-success">Add Book</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</div>

</body>
</html>