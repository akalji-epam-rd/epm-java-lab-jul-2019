<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

    <title>Edit book</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="../../../static/css/pretty.css">
    <link rel="stylesheet" href="../../../static/css/bootstrap.min.css">

</head>
<body>

<jsp:include page='/WEB-INF/views/header.jsp'>
    <jsp:param name="articleId" value=""/>
</jsp:include>

<h2 class="header">Edit author</h2>

<form class="form-horizontal" action='${pageContext.request.contextPath}/author/edit' method="POST">
    <fieldset>
        <div id="legend">
            <legend class="">Edit author</legend>
        </div>
        <input name="id" value="${author.id}" hidden/>
        <div class="control-group">
            <label class="control-label" for="name">Name</label>
            <div class="controls">
                <input type="text" id="name" value="${author.name}" name="name" placeholder="" class="input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="lastname">Last name</label>
            <div class="controls">
                <input type="text" id="lastname" value="${author.lastName}" name="lastname" placeholder="" class="input-xlarge">
            </div>
        </div>

        <div class="control-group">
            <div class="controls">
                <button type="submit" class="btn btn-success">Edit author</button>
            </div>
        </div>
    </fieldset>
</form>

</body>
</html>