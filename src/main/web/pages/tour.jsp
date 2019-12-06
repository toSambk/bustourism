<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>${tour.name}</title>
    <link rel="stylesheet" type="text/css" href="resources/styles/ratingStars.css"/>
    <link rel="stylesheet" type="text/css" href="resources/styles/stylesDashboard.css" />
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
                <p>Рейтинг тура: ${tour.rating}</p>
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

        <form:form method="post" action="/assessTour">
        <div class="rating_block">
            <input name="rating" value="5" id="rating_5" type="radio"/>
            <label for="rating_5" class="label_rating"></label>

            <input name="rating" value="4" id="rating_4" type="radio" />
            <label for="rating_4" class="label_rating"></label>

            <input name="rating" value="3" id="rating_3" type="radio" />
            <label for="rating_3" class="label_rating"></label>

            <input name="rating" value="2" id="rating_2" type="radio" />
            <label for="rating_2" class="label_rating"></label>

            <input name="rating" value="1" id="rating_1" type="radio" />
            <label for="rating_1" class="label_rating"></label>
        </div>
            <input type="hidden" name="tourId" value="${tour.id}"/>
            <p><input type="submit" value="Отправить"></p>
        </form:form>

    </section>
</div>

</body>
</html>
