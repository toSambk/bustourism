<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>${tour.name}</title>
</head>
<body>
<p>
    Имя тура: ${tour.name}
</p>
<p>
    Оценка: ${tour.rating}
</p>
<p>
    Количество занятых мест: ${tour.curNumberOfSeats}
</p>
<p>
    Максимальное количество мест: ${tour.maxNumberOfSeats}
</p>
<p>
    Дата: ${tour.date}
</p>
<p>

<%--    <form:form method="post" modelAttribute="acceptForm">--%>
<%--        <form:label path="hiddenField"/>--%>
<%--        <form:errors path="hiddenField" cssStyle="color: red"/>--%>
<%--        <input type="submit" value="Приобрести тур"--%>
<%--    </form:form>--%>

    <form method="post" action="/tour">
        <input type = "hidden" name="tourId" value="${tour.id}">
        <button type="submit" name="submit" value="Приобрести тур">Приобрести тур</button>
    </form>
</p>
<p>
    <a href="/dashboard">Назад к ленте</a>
</p>
<p>
    <a href="/cabinet">Перейти в кабинет пользователя</a>
</p>
</body>
</html>
