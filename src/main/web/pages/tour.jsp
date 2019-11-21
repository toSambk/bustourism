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

    <form:form action="/tour" method="post" modelAttribute="acceptForm">
       <p>
        <form:input type="text" path="quantity"/>
        <form:errors path="quantity" cssStyle="color: red"/>
        </p>
        <input type="hidden" name="tourId" value="${tour.id}"/>
        <input type="submit" value="Приобрести тур"/>
    </form:form>

</p>
<p>
    <a href="/dashboard">Назад к ленте</a>
</p>
<p>
    <a href="/cabinet">Перейти в кабинет пользователя</a>
</p>
</body>
</html>
