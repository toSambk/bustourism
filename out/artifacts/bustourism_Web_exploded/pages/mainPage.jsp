<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="login" scope="request" class="java.lang.String"/>

<html lang="en">
<head>
    <meta charset="UTF-8" />
    <link rel="stylesheet" type="text/css" href="../css/style.css" />
    <script src="js/modernizr.custom.63321.js"></script>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Список всех туров</title>
</head>
<body>

<c:if test="${empty sessionScope['userId']}">
<section class="main">
        <form class="form-1" method="post" action="/login">
                <p class="field">
                <input type="text" name="login" value="${login}" placeholder="Введите логин">
                    <i class="icon-user icon-large"></i>
                </p>
                <p class="field">
                <input type="password" name="password" placeholder="Введите пароль">
                    <i class="icon-lock icon-large"></i>
                </p>
                <p class="submit">
                    <button type="submit" name="submit" value="Войти"><i class="icon-arrow-right icon-large"></i></button>
                </p>
        </form>
</section>
</c:if>

<c:if test="${not empty sessionScope['userId']}">
    <p><a href="/dashboard">Dashboard</a></p>
</c:if>

<c:if test = "${not empty login}">
    <p class = "error">
        Login or password is incorrect!!!
    </p>

</c:if>

</body>
</html>
