<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="tours" scope="request" type="java.util.List<ru.bustourism.entities.Tour>"/>
<jsp:useBean id="user" scope="request" type="ru.bustourism.entities.User"/>
<html lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>Список туров</title>
    <style><jsp:directive.include file="/styles/stylesDashboard.css"/></style>
    <link rel="stylesheet" href="styles/stylesDashboard.css" type="text/css">
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
            <li><a href="/sessionclear">Страница авторизации</a></li>
            <li class="active"><a href="/dashboard">Список автобусных туров</a></li>
            <li><a href="/cabinet">Личный кабинет</a></li>
        </ul>
    </nav>
    <div id="heading">
        <h1>Список автобусных туров</h1>
    </div>
    <aside>
        <nav>
            <ul class="aside-menu">
                <c:forEach items="${tours}" var="tour">
                    <li><a href="/tour?tourId=${tour.id}">${tour.name}</a></li>
                </c:forEach>
            </ul>
        </nav>
    </aside>
    <section>
<%--        <table>--%>
<%--            <thead>--%>
<%--            <tr>--%>
<%--                <th>Название тура</th>--%>
<%--                <th>Максимальное количество мест</th>--%>
<%--                <th>Текущее количество мест</th>--%>
<%--                <th>Дата начала</th>--%>
<%--                <th>Рейтинг</th>--%>
<%--            </tr>--%>
<%--            </thead>--%>
<%--            <tbody>--%>
<%--            <c:forEach items="${tours}" var="tour">--%>
<%--                <tr>--%>
<%--                    <td>${tour.name}</td>--%>
<%--                    <td>${tour.maxNumberOfSeats}</td>--%>
<%--                    <td>${tour.curNumberOfSeats}</td>--%>
<%--                    <td>${tour.date}</td>--%>
<%--                    <td>${tour.rating}</td>--%>
<%--                </tr>--%>
<%--            </c:forEach>--%>
<%--            </tbody>--%>
<%--        </table>--%>

        <p>Добро пожаловать, ${user.login}!</p>
        <p>Выберите интересующий вас тур слева</p>
        <figure>
            <img src="${pageContext.request.contextPath}/images/sample1.jpg" width="320" height="175" alt="">
        </figure>
        <figure>
            <img src="${pageContext.request.contextPath}/images/sample2.jpg" width="320" height="175" alt="">
        </figure>

    </section>
</div>

</body>
</html>
