<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="tours" scope="request" type="java.util.List<ru.bustourism.entities.Tour>"/>
<jsp:useBean id="user" scope="request" type="ru.bustourism.entities.User"/>
<html lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>Список туров</title>
    <link rel="stylesheet" type="text/css" href="resources/styles/stylesDashboard.css" />
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Oswald:400,300" type="text/css">
    <script type="text/javascript" src="resources/script/script.js"></script>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
</head>

<body onload="loadTours()">

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
        <h1>Лучшие туры на автобусе</h1>
    </div>
    <aside>
        <nav>
            <ul id="tours-list" class="aside-menu">
                <li>Загрузка...</li>
            </ul>
        </nav>
    </aside>
    <section id="tour-info">
        <p>Добро пожаловать, ${user.login}!</p>
        <p>Выберите интересующий вас тур слева</p>
        <figure>
            <img src="${pageContext.request.contextPath}/resources/images/sample1.jpg" width="320" height="175" alt="">
        </figure>
        <figure>
            <img src="${pageContext.request.contextPath}/resources/images/sample2.jpg" width="320" height="175" alt="">
        </figure>
    </section>
</div>

</body>
</html>
