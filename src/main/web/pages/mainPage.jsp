<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="login" scope="request" class="java.lang.String"/>

<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="resources/styles/style.css" />
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Список всех туров</title>
</head>
<body>
<section class="main">
<c:if test="${empty sessionScope['userId']}">
        <form class="form-1" method="post" action="/login">
                <p class="field">
                <input type="text" name="login" value="${login}" placeholder="Введите логин">
                    <i class="icon-user icon-large"></i>
                </p>
                <p class="field">
                <input type="password" name="password" placeholder="Введите пароль">
                    <i class="icon-lock icon-large"></i>
                </p>
                <security:csrfInput/>
                <p class="submit">
                    <button type="submit" name="submit" value="Войти"><i class="icon-arrow-right icon-large"></i></button>
                </p>
                <p>
                    <a href="/register">Регистрация нового пользователя</a>
                </p>

        </form>
</c:if>

</section>
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
