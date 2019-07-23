<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <title>Add author</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="../../../static/css/bootstrap.min.css">

</head>
<body>

<jsp:include page='/WEB-INF/views/header.jsp'>
    <jsp:param name="articleId" value=""/>
</jsp:include>

<h2 class="header">Add new author</h2>

<form class="form-horizontal" action='${pageContext.request.contextPath}/author/add' method="POST">
    <fieldset>
        <div id="legend">
            <legend class="">Add new author</legend>
        </div>
        <div class="control-group">
            <label class="control-label" for="name">Name</label>
            <div class="controls">
                <input type="text" id="name" name="name" placeholder="" class="input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="lastname">Description</label>
            <div class="controls">
                <input type="text" id="lastname" name="lastname" placeholder="" class="input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <div class="controls">
                <button type="submit" class="btn btn-success">Add author</button>
            </div>
        </div>
    </fieldset>
</form>

</body>
</html>