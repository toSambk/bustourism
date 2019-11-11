<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Список всех туров</title>
</head>
<style type="text/css">
    .error {
        color: red;
    }
</style>
<body>

<h1>Добро пожаловать!</h1>

<c:if test="${empty sessionScope['userId']}">
<form method="post" action="/login">
    <p>Login: <input type="text" name="login" value="${param['login']}"></p>
    <p>Password: <input type="password" name="password"></p>
    <p><input type="submit"></p>
</form>
</c:if>

<c:if test="${not empty sessionScope['userId']}">
    <p><a href="/dashboard">Dashboard</a></p>
</c:if>

<c:if test = "${not empty param['login']}">
    <p class = "error">
        Login or password is incorrect!!!
    </p>

</c:if>

</body>
</html>
