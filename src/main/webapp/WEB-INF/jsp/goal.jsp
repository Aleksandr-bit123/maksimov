<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <title>Цели</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>
<body>
<h2>Цели</h2>
<a href="/logist">назад</a>
<table> <!-- Main table -->
    <tr>
        <td id="tdForm" style="vertical-align: top; "> <%--Ячейка для форм--%>
            <form id="goalForm" action="" method="post">
                <div><label for="inputId">ID</label></div>
                <div><input id="inputId" type="text" name="id" placeholder="auto" readonly ></div>

                <div><label for="inputPointId">Точка</label></div>
                <div>
                    <select id="inputPointId" type="text" name="pointId" value="null">
                        <option selected disabled>Выберите точку</option>
                    </select>
                </div>

                <div><label for="inputDriverId">Водитель</label></div>
                <div>
                    <select id="inputDriverId" type="text" name="driverId" value="null">
                        <option selected disabled>Выберите водителя</option>
                    </select>
                </div>

                <div><label for="inputDeliveryDate">Дата</label></div>
                <div>
                    <input id="inputDeliveryDate" type="date" name="deliveryDate">
                </div>

                <input type="hidden" id="inputStaus" name="status" value="0">

                <input type="hidden" id="inputLogistId" name="logistId" value="1">


                <div><input id="inputSubmit" type="submit" value="Добавить"/></div>
                <div><input type="reset" value="Очистить" onclick="clearGoalForm()"/></div>
            </form>
        </td>

        <td style="vertical-align: top"> <%--Ячейка для списка сотрудников--%>
            <table id="goalTable"> <!-- Таблица товаров -->
                <caption>Таблица целей</caption>
                <tr>
                    <th>ID</th>
                    <th>Точка</th>
                    <th>Водитель</th>
                    <th>Логист</th>
                    <th>Статус</th>
                    <th>Оборот товара</th>
                    <th>X</th>
                </tr>

            </table>
        </td>
    </tr>
</table>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
    goals = ${goals};
    clients = ${clients};
    orders = ${orders};
    contragents = ${contragents};
    drivers = ${drivers};

    clients.forEach(function (client, i, arrClient) {
        $('#inputPointId').append($('<option>').attr("id","clientOption" + client.id).val(client.id).append(client.id + " "+ client.lastName));
    });

    contragents.forEach(function (contragent, i, arrContragent) {
        $('#inputPointId').append($('<option>').attr("id","contragentOption" + contragent.id).val(contragent.id).append(contragent.id + " "+ contragent.name));
    });

    drivers.forEach(function (driver, i, arrDriver) {
        $('#inputDriverId').append($('<option>').attr("id","driverOption" + driver.id).val(driver.driverId).append(driver.driverId + " "+ driver.employee.lastName));
    });

    goals.forEach(function (goal, i, arrGoal) {
        let client = clients.find(client => client.id == goal.pointId);
        let contragent = contragents.find(contragent => contragent.id == goal.pointId);

        $('#goalTable').append($('<tr onclick="fillForm('+goal.id+')">')
            .append($('<td>').append(goal.id))
            .append($('<td>').append(
                (client)?
                    client.id + client.lastName
                    :
                    contragent.id + contragent.name
            ))
            .append($('<td>').append(goal.driverId))
            .append($('<td>').append(goal.logistId))
            .append($('<td>').append(goal.status))
            .append($('<td>').append(goal.goodTurnoverList))
            .append($('<td>').append('<input type="button" value="X" onclick="cancelGoal(' + goal.id + ')">'))
        );
    });

    document.getElementById('goalForm').addEventListener('submit', submitForm);
    $('#goalForm').attr("action", "/rest/logist/goal/");

    function submitForm(event) {
        // Отменяем стандартное поведение браузера с отправкой формы
        event.preventDefault();

        // event.target — это HTML-элемент form
        let formData = new FormData(event.target);

        // Собираем данные формы в объект

        let obj = {};
        formData.forEach((value, key) => obj[key] = value);
        // Собираем запрос к серверу
        let request = new Request(event.target.action, {
            method: 'POST',
            body: JSON.stringify(obj),
            headers: {
                'Content-Type': 'application/json',
            },
        });
        console.log(JSON.stringify(obj));
        alert(JSON.stringify(obj));
        // Отправляем (асинхронно!)
        fetch(request).then(
            function (response) {
                // Запрос успешно выполнен
                console.log(response);
                alert("статус выполнения запроса: " + response.status + ", " + response.ok);
                // return response.json() и так далее см. документацию
                location.reload(true); /*true - загрузка с сервера , false - с кеша*/
            },
            function (error) {
                // Запрос не получилось отправить
                console.error(error);
            }
        );

        // Код после fetch выполнится ПЕРЕД получением ответа
        // на запрос, потому что запрос выполняется асинхронно,
        // отдельно от основного кода
        console.log('Запрос отправляется');
    }
</script>


</body>
</html>