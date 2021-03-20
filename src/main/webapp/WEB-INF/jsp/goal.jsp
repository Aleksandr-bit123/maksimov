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

                <div><label for="inputInfo">info</label></div>
                <div>
                    <textarea id="inputInfo" type="date" name="info" placeholder="info"></textarea>
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
                    <th>info</th>
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
        let driver = drivers.find(driver => driver.driverId == goal.driverId);


    $('#goalTable').append($('<tr onclick="fillForm(' + goal.id + ')">')
        .append($('<td>').append(goal.id))
        .append($('<td>').append(
            (client)?
                client.lastName + "</br>"
                + client.firstName+ "</br>"
                + client.middleName + "<br/>"
                + client.point.address + "</br>"
                + client.point.phone + "<br/>"
                + client.info
                :
                contragent.name + "</br>"
                + contragent.point.address + "</br>"
                + contragent.point.phone
            )
        )
        .append($('<td>').append(
            driver.employee.lastName + "</br>"
            + driver.employee.firstName + "</br>"
            + driver.employee.middleName + "</br>"
            + driver.carEntities[0].brand + "</br>"
            + driver.carEntities[0].cubic_capacity + " куб</br>"
            + driver.carEntities[0].fuel_consumption + " л/100 км</br>"
            )
        )
        .append($('<td>').append(goal.logistId))
        .append($('<td>').append(goal.status))
        .append($('<td>').append(goal.info))
        .append($('<td>')
            .append($('<table>')
                .append($('<tr>')
                    .append($('<td id="tdGoodTurnover' + goal.id + '" hidden>')
                        .append($('<form id="goodTurnoverForm' + goal.id + '" action="" method="post">')
                            .append($('<div><label for="inputTurnoverId' + goal.id + '">').append("ID"))
                            .append($('<div><input id="inputTurnoverId' + goal.id + '" name="id" type="number" placeholder="auto" readonly>'))
                            .append($('<div><label for="inputTurnoverQuantity' + goal.id + '">').append("Количество"))
                            .append($('<div><input id="inputTurnoverQuantity' + goal.id + '" name="quantity" type="number" value="1" min="1" max="20">'))
                            .append($('<div><label for="inputTurnoverGood' + goal.id + '">').append("Товар"))
                            .append($('<div>')
                                .append($('<select id="inputTurnoverGood' + goal.id + '" name="goodId">')
                                    .append($('<option selected disabled>').append("Выберите товар"))
                                )
                            )
                            .append($('<div><label for="inputPaymentMethod' + goal.id + '">').append("Способ оплаты"))
                            .append($('<div>')
                                .append($('<select id="inputPaymentMethod' + goal.id + '" name="paymentMethod">')
                                    .append($('<option selected value="0">').append("наличные"))
                                    .append($('<option value="1">').append("карта"))
                                    .append($('<option value="2">').append("оплачено"))
                                )
                            )
                            .append($('<div><label for="inputTurnover' + goal.id + '">').append("Оборот"))
                            .append($('<div>')
                                .append($('<select id="inputTurnover' + goal.id + '" name="turnover">')
                                    .append($('<option selected value="true">').append("отдать"))
                                    .append($('<option value="false">').append("забрать"))
                                )
                            )
                            .append($('<div><label for="inputTurnoverInfo' + goal.id + '">').append("info"))
                            .append($('<div><textarea id="inputTurnoverInfo' + goal.id + '" name="info" placeholder="info">'))
                            .append($('<input type="submit">'))
                            .append($('<input type="reset" >'))

                        )
                    )
                    .append($('<td>')
                        .append($('<table id="goodTurnoverTable' + goal.id + '">')
                            .append($('<tr onclick="showForm(' + goal.id + ')">')
                                .append($('<th>').append("ID"))
                                .append($('<th>').append("Товар"))
                                .append($('<th>').append("Количество"))
                                .append($('<th>').append("Оборот"))
                                .append($('<th>').append("Способ оплаты"))
                                .append($('<th>').append("info"))
                                .append($('<th>').append("X"))
                            )
                        )
                    )
                )
            )

        )
        .append($('<td>').append('<input type="button" value="X" onclick="cancelGoal(' + goal.id + ')">'))
    );
    });

    document.getElementById('goalForm').addEventListener('submit', submitForm);
    $('#goalForm').attr("action", "/rest/logist/goal/");

    function clearGoalForm(){

    }

    function fillForm(goalId){

    }

    function showForm(goalId){
        if($('#tdGoodTurnover'+goalId).attr('hidden')){
            $('#tdGoodTurnover'+goalId).attr('hidden' , false);
        } else {
            $('#tdGoodTurnover'+goalId).attr('hidden' , true);
        }
    }



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