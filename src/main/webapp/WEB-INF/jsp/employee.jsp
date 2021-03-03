<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Сотрудники</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>
<body>
<div>
    <h2>Сотрудники <br> Только для залогинившихся директоров.</h2>
    <a href="/">Главная</a>

</div>

<div>
    <table>
        <thead>
        <th>ID</th>
        <th>UserName</th>
        <th>Фамилия</th>
        <th>Имя</th>
        <th>Отчество</th>
        <th>Дата рождения</th>
        <th>Паспорт</th>
        <th>Телефон</th>
        <th>Должность</th>

        </thead>
        <c:forEach items="${allUsers}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.employeeEntity.getFirstName()}</td>
                <td>${user.employeeEntity.getLastName()}</td>
                <td>${user.employeeEntity.getMiddleName()}</td>
                <td>${user.employeeEntity.getBirthdate()}</td>
                <td>${user.employeeEntity.getPassport()}</td>
                <td>${user.employeeEntity.getPhone()}</td>
                <td>
<%--                    <c:if test="${user.employeeEntity.getBossEntity().getId()!=null}">Директор;</c:if>--%>
<%--                    <c:if test="${user.employeeEntity.getDriverEntity().getId()!=null}">Водитель;</c:if>--%>
<%--                    <c:if test="${user.employeeEntity.getLogistEntity().getId()!=null}">Логист;</c:if>--%>

                    <c:forEach items="${user.employeeEntity.getPositions()}" var="position">${position.name}; </c:forEach>
                </td>

                <td>
                    <form action="${pageContext.request.contextPath}/admin" method="post">
                        <input type="hidden" name="userId" value="${user.id}"/>
                        <input type="hidden" name="action" value="delete"/>
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>