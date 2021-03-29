<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

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
            <th scope="col">action</th>
        </tr>
    </table>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
    goals = ${goals};

    goals.forEach(function (goal, i, arrGoal) {
        $('#goalTable').append($('<tr>')
            .append($('<td>').append(goal.id))
            .append($('<td>').append(goal.pointId))
            .append($('<td>').append(goal.driverId))
            .append($('<td>').append(goal.logistId))
            .append($('<td>').append(goal.status))
            .append($('<td>').append(goal.deliveryDate))
            .append($('<td>').append(goal.info))
            .append($('<td>').append(goal.priority))
            .append($('<td>').append($('<input type="button" value="начать" id="' + goal.id + '" onclick="submitGoalForm(' + goal.id + ')">')))
        )
        if (goal.status === "WAITING") {
            $('#' + goal.id).val("начать");
        }
        if (goal.status === "DOING") {
            $('#' + goal.id).val("завершить");
        }
        if (goal.status === "COMPLETED" || goal.status ==="CANCELED") {
            $('#' + goal.id).prop("disabled",true);
        }
    });



    function submitGoalForm(goalId) {
        submitGoal(goalId);
    }

    function submitGoal(goalId){
        result = confirm(" цель id: " + goalId);
        if (result) {
            console.log(goalId);
            var xhr = new XMLHttpRequest();
            xhr.open('PUT', '/rest/driver/goal/' + goalId, true);
            xhr.send();
            xhr.onreadystatechange = function() {
                if (this.readyState != 4) {
                    location.reload(true); /*true - загрузка с сервера , false - с кеша*/
                    return;
                }
                // по окончании запроса доступны:
                // status, statusText
                // responseText, responseXML (при content-type: text/xml)
                if (this.status != 200) {
                    // обработать ошибку
                    alert( 'ошибка: ' + (this.status ? this.statusText : 'запрос не удался') );
                    return;
                }
                // получить результат из this.responseText или this.responseXML
            }
        }
    }

</script>
</body>
</html>