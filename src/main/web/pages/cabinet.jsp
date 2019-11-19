<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="user" scope="request" type="ru.bustourism.entities.User"/>
<html lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>Список туров</title>
    <style><jsp:directive.include file="/styles/stylesDashboard.css"/></style>
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Oswald:400,300" type="text/css">


</head>
<body>

<div id="wrapper">
    <header>
        <form name="search" action="#" method="get">
            <input type="text" name="q" placeholder="Search"><button type="submit">GO</button>
        </form>
    </header>
    <nav>
        <ul class="top-menu">
            <li><a href="/">Страница авторизации</a></li>
            <li><a href="/dashboard">Список автобусных туров</a></li>
            <li class="active"><a href="/cabinet">Личный кабинет</a></li>
        </ul>
    </nav>
    <div id="heading">
        <h1>Личный кабинет</h1>
    </div>
    <aside>
        <p>Пользователь ${user.login}</p>
    </aside>
    <section>
        <p>Администратор: ${user.administrator}</p>
        <p>Пароль: ${user.password}</p>
        <p>Купленные туры: </p>
        <table>
            <thead>
            <tr>
                <th>Название тура</th>
                <th>Максимальное количество мест</th>
                <th>Текущее количество мест</th>
                <th>Дата начала</th>
                <th>Рейтинг</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${user.tours}" var="tour">
                <tr>
                    <td>${tour.name}</td>
                    <td>${tour.maxNumberOfSeats}</td>
                    <td>${tour.curNumberOfSeats}</td>
                    <td>${tour.date}</td>
                    <td>${tour.rating}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </section>
</div>

</body>
</html>
