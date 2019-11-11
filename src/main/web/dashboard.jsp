<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="tours" scope="request" type="java.util.List<ru.bustourism.entities.Tour>"/>
<html>
<head>
    <title>Список туров</title>
</head>
<body>

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

    <c:forEach items="${tours}" var="tour">
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

</body>
</html>
