<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="../../static/css/bootstrap.min.css">
<link rel="stylesheet" href="../../static/css/signin.css">

<html>
<head>
    <title>Login</title>
</head>


<body class="text-center">
<form class="form-signin" method="post">
    <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
    <label for="inputEmail" class="sr-only">Email address</label>
    <input name="email" type="email" id="inputEmail" class="form-control" placeholder="Email address" required
           autofocus>
    <label for="inputPassword" class="sr-only">Password</label>
    <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>


    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    <a href="/user/registernew" >
        <button type="button" class="btn btn-lg btn-primary btn-block">Sign up</button>
    </a>
</form>
</body>

</html>
