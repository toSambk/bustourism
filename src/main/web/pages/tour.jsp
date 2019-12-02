<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>${tour.name}</title>
    <link rel="stylesheet" type="text/css" href="resources/styles/stylesDashboard.css" />
    <link rel="stylesheet" type="text/css" href="resources/styles/ratingStars.css"/>
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Oswald:400,300" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script type="text/javascript" src="resources/script/script.js"></script>

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
            <li><a href="/sessionclear">Выйти</a></li>
            <li class="active"><a href="/dashboard">Список автобусных туров</a></li>
            <li><a href="/cabinet">Личный кабинет</a></li>
        </ul>
    </nav>
    <div id="heading">
        <h1>${tour.name}</h1>
    </div>
    <aside>
        <nav>
            <ul class="aside-menu">
                <p>Количество занятых мест: ${tour.curNumberOfSeats}</p>
                <p>Максимальное количество мест: ${tour.maxNumberOfSeats}</p>
            </ul>
        </nav>
    </aside>
    <section>
            <form:form action="/tour" method="post" modelAttribute="acceptForm">
        <p>
            <form:input type="text" path="quantity"/>
            <form:errors path="quantity" cssStyle="color: red"/>
        </p>
        <input type="hidden" name="tourId" value="${tour.id}"/>
        <input type="submit" value="Приобрести тур"/>
        </form:form>

        <div id="rating">
            <div class="param">Параметр 1:</div>
            <div><div class="stars"></div><p class="progress" id="p1"></p></div>
            <div class="rating" id="param1">5.0</div>
            <div class="param">Параметр 2:</div>
            <div><div class="stars"></div><p class="progress" id="p2"></p></div>
            <div class="rating" id="param2">5.0</div>
            <div class="param">Параметр 3:</div>
            <div><div class="stars"></div><p class="progress" id="p3"></p></div>
            <div class="rating" id="param3">5.0</div>
            <div class="param">Общая оценка:</div>
            <div><div id="sum_stars"></div><p id="sum_progress"></p></div>
            <div id="summ">5.00</div>
            <input id="el_999" type="submit" value="Отправить!">
            <p id="message"></p>
        </div>

    </section>
</div>

</body>
</html>
