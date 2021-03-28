<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <title>Водитель</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>
<body>
<div>
    <h2>Водитель <br> Только для залогинившихся водителей.</h2>
    <a href="/">Главная</a>
    <table id="goalTable"> <!-- Таблица товаров -->
        <caption>Таблица целей</caption>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Точка</th>
            <th scope="col">Водитель</th>
            <th scope="col">Логист</th>
            <th scope="col">Статус</th>
            <th scope="col">Дата</th>
            <th scope="col">info</th>
            <th scope="col">priority</th>
        </tr>
    </table>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
    goals = ${goals};

    goals.forEach(function (goal, i, arrGoal) {
        $('#goalTable').append($('<tr onclick="selectGoal(' + goal.id + ')">')
            .append($('<td>').append(goal.id))
            .append($('<td>').append(goal.pointId))
            .append($('<td>').append(goal.driverId))
            .append($('<td>').append(goal.logistId))
            .append($('<td>').append(goal.status))
            .append($('<td>').append(goal.deliveryDate))
            .append($('<td>').append(goal.info))
            .append($('<td>').append(goal.priority))
        )
    });

    function selectGoal(goalId){

    }
</script>
</body>
</html>