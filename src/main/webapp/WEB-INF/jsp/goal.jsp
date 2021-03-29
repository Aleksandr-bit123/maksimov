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
    <caption hidden></caption>
    <tr hidden>
        <th scope="col"></th>
        <th scope="col"></th>
    </tr>
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
                    <input id="inputDeliveryDate" type="date" name="deliveryDate" value="">
                </div>

                <div><label for="inputPriority">priority</label></div>
                <div>
                    <input id="inputPriority" type="number" name="priority" min="0" max="255" value="100">
                </div>

                <div><label for="inputInfo">info</label></div>
                <div>
                    <textarea id="inputInfo" name="info" placeholder="info"></textarea>
                </div>

                <input type="hidden" id="inputStatus" name="status" value="WAITING">

                <input type="hidden" id="inputLogistId" name="logistId" value="${logistId}">
                <input type="hidden" id="inputGoodTurnoverList" name="goodTurnoverList">


                <div><input id="inputSubmit" type="submit" value="Добавить"/></div>
                <div><input type="reset" value="Очистить" onclick="clearGoalForm()"/></div>
            </form>
        </td>

        <td style="vertical-align: top"> <%--Ячейка для списка сотрудников--%>
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
                    <th scope="col">Оборот товара</th>
                    <th scope="col">X</th>
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
    goods = ${goods};
    logistId = ${logistId};


    let clientsForInput = [];
    let ordersForClientsForInput = orders;

    ordersForClientsForInput = ordersForClientsForInput.filter(order => order.status == null);
        // alert(JSON.stringify(ordersForClientsForInput));
    ordersForClientsForInput.forEach(function (order, i, arrOrder) {
        clients.filter(client => client.id === order.clientId).forEach(client => clientsForInput.push(client));
    // alert(JSON.stringify(clientsForInput));
    });

    clientsForInput.forEach(function (client, i, arrClient) {
        $('#inputPointId').append($('<option>').attr("id","clientOption" + client.id).val(client.id).append(client.id + " "+ client.lastName));
    });

    contragents.forEach(function (contragent, i, arrContragent) {
        $('#inputPointId').append($('<option>').attr("id","contragentOption" + contragent.id).val(contragent.id).append(contragent.id + " "+ contragent.name));
    });

    drivers.forEach(function (driver, i, arrDriver) {
        $('#inputDriverId').append($('<option>').attr("id","driverOption" + driver.id).val(driver.driverId).append(driver.driverId + " "+ driver.employee.lastName));
    });

    let currentGoalId = null;
    let currentGoal = null;
    let currentGoodTurnoverList = null;

    goals.forEach(function (goal, i, arrGoal) {
        let client = clients.find(client => client.id == goal.pointId);
        let contragent = contragents.find(contragent => contragent.id == goal.pointId);
        let driver = drivers.find(driver => driver.driverId == goal.driverId);
        // let clientOrders = orders.filter(order => order.clientId == goal.pointId);
        // alert(JSON.stringify(clientOrders));


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
            + driver.carEntities[0].cubicCapacity + " куб</br>"
            + driver.carEntities[0].fuelConsumption + " л/100 км</br>"
            )
        )
        .append($('<td>').append(goal.logistId))
        .append($('<td>').append(goal.status))
        .append($('<td>').append(goal.deliveryDate))
        .append($('<td>').append(goal.info))
        .append($('<td>').append(goal.priority))
        .append($('<td>')
            .append($('<table>')
                .append($('<tr onclick="fillGoodTurnoverForm(' + goal.id + ')">')
                    .append($('<td id="tdGoodTurnover' + goal.id + '" hidden>')
                        .append($('<form id="goodTurnoverForm' + goal.id + '" action="" method="post">')
                            .append($('<div align="center" style="color: darkgray" onclick="hideGoodTurnoverForm(' + goal.id + ')">').append("скрыть"))

                            .append($('<div><label for="inputTurnoverId' + goal.id + '">').append("ID"))
                            .append($('<div><input id="inputTurnoverId' + goal.id + '" name="id" type="number" placeholder="auto" readonly>'))

                            .append($('<div><label for="inputTurnoverGood' + goal.id + '">').append("Товар"))
                            .append($('<div>')
                                .append($('<select id="inputTurnoverGood' + goal.id + '" name="goodId">')
                                    .append($('<option selected disabled>').append("Выберите товар"))
                                )
                            )

                            .append($('<div><label for="inputTurnoverQuantity' + goal.id + '">').append("Количество"))
                            .append($('<div><input id="inputTurnoverQuantity' + goal.id + '" name="quantity" type="number" value="1" min="1" max="20">'))

                            .append($('<div><label for="inputPaymentMethod' + goal.id + '">').append("Способ оплаты"))
                            .append($('<div>')
                                .append($('<select id="inputPaymentMethod' + goal.id + '" name="paymentMethod">')
                                    .append($('<option selected value="CASH">').append("наличные"))
                                    .append($('<option value="CASHLESS">').append("карта"))
                                    .append($('<option value="PAID_FOR">').append("оплачено"))
                                )
                            )

                            .append($('<div><label for="inputTurnover' + goal.id + '">').append("Оборот"))
                            .append($('<div>')
                                .append($('<select id="inputTurnover' + goal.id + '" name="turnover">')
                                    .append($('<option selected value="true">').append("отдать"))
                                    .append($('<option value="false">').append("забрать"))
                                )
                            )

                            .append($('<div><label for="inputTurnoverPriority' + goal.id + '">').append("priority"))
                            .append($('<div><input id="inputTurnoverPriority' + goal.id + '" name="priority" type="number" value="100" min="1" max="255">'))

                            .append($('<div><label for="inputTurnoverLinkGoal' + goal.id + '">').append("Link Goal"))
                            .append($('<div><input id="inputTurnoverLinkGoal' + goal.id + '" name="linkGoal" type="number" placeholder="Link Goal" min="0" >'))

                            .append($('<div><label for="inputTurnoverLinkPoint' + goal.id + '">').append("Link Point"))
                            .append($('<div><input id="inputTurnoverLinkPoint' + goal.id + '" name="linkPoint" type="number" placeholder="Link Point" min="0" >'))

                            .append($('<div><label for="inputTurnoverLink' + goal.id + '">').append("Link"))
                            .append($('<div><input id="inputTurnoverLink' + goal.id + '" name="link" type="number" placeholder="Link" min="0" >'))

                            .append($('<div><label for="inputTurnoverInfo' + goal.id + '">').append("info"))
                            .append($('<div><textarea id="inputTurnoverInfo' + goal.id + '" name="info" placeholder="info">'))
                            .append($('<input type="button" id="inputTurnoverButton' + goal.id + '" value="Добавить" onclick="changeTurnover(' + goal.id + ')">'))
                            .append($('<input type="reset" >'))

                        )
                    )
                    .append($('<td>')
                        .append($('<table id="goodTurnoverTable' + goal.id + '">')
                            .append($('<tr>')
                                .append($('<th>').append("ID"))
                                .append($('<th>').append("Товар"))
                                .append($('<th>').append("Количество"))
                                .append($('<th>').append("Оборот"))
                                .append($('<th>').append("Способ оплаты"))
                                .append($('<th>').append("priority"))
                                .append($('<th>').append("order"))
                                .append($('<th>').append("info"))
                                .append($('<th>').append("link"))
                                .append($('<th>').append("X"))
                            )
                        )
                    )
                )
            )

        )
        .append($('<td>').append('<input type="button" value="X" onclick="deleteGoal(' + goal.id + ')">'))
    );
    goal.goodTurnoverList.forEach(function (goodTurnover, i, arrGoodTurnover) {
        $('#goodTurnoverTable' + goal.id).append($('<tr onclick="fillGoodTurnoverForm(' + goal.id+','+goodTurnover.id + ')">')
            .append($('<td>').append(goodTurnover.id))
            .append($('<td>').append(goodTurnover.goodId))
            .append($('<td>').append(goodTurnover.quantity))
            .append($('<td>').append(goodTurnover.turnover==true?"отдать":"забрать"))
            .append($('<td>').append(goodTurnover.paymentMethod))
            .append($('<td>').append(goodTurnover.priority))
            .append($('<td>').append(goodTurnover.orderId))
            .append($('<td>').append(goodTurnover.info))
            .append($('<td>').append(goodTurnover.link))
            .append($('<td>').append('<input type="button" value="X" onclick="deleteGoodTurnover(' + goodTurnover.id + ')">'))
        );
    });

        goods.forEach(function (good,i,arrGood){
            $('#inputTurnoverGood'+goal.id).append($('<option value="'+ good.id +'">').append(good.id + " " + good.name));
            // alert($('#inputTurnoverGood'+goalId));
        });

    });

    document.getElementById('goalForm').addEventListener('submit', submitForm);
    $('#goalForm').attr("action", "/rest/logist/goal/");
    method = 'POST';

    function clearGoalForm(){
        $('#goalForm').attr("action", "/rest/logist/goal/");
        method = 'POST';
        $('#inputSubmit').val("Добавить");
    }

    function fillForm(goalId){
        $('#goalForm').attr("action", "/rest/logist/goal/"+goalId);
        method = 'PUT';

        if (currentGoalId == null){
            showForm(goalId);
            currentGoalId = goalId;
        }
        if(currentGoalId != goalId){
            showForm(currentGoalId);
            currentGoalId = goalId;
            showForm(currentGoalId);
        };
        currentGoal = goals.find(goal => goal.id == goalId);
        // alert(JSON.stringify(currentGoal));

        $('#inputId').val(currentGoal.id);
        $('#inputDeliveryDate').val(currentGoal.deliveryDate);
        $('#inputStatus').val(currentGoal.status);
        $('#inputInfo').val(currentGoal.info);
        $('#inputPriority').val(currentGoal.priority);
        $('#inputLogist').val(currentGoal.logistId);
        $('#inputPointId option[value=' + currentGoal.pointId + ']').prop('selected', true);
        $('#inputDriverId option[value=' + currentGoal.driverId + ']').prop('selected', true);

        $('#inputSubmit').val("Изменить");

        goods.forEach(function (good) {

        });

    }

    function showForm(goalId){
        if($('#tdGoodTurnover'+goalId).attr('hidden')){
            $('#tdGoodTurnover'+goalId).attr('hidden' , false);
        } else {
            $('#tdGoodTurnover'+goalId).attr('hidden' , true);
        }
    }

    function hideGoodTurnoverForm(goalId){
    }

    function cancelGoal(){

    }

    function fillGoodTurnoverForm(goalId,goodTurnoverId){
        let currentGoodTurnover = currentGoal.goodTurnoverList.find(goodTurnover => goodTurnover.id === goodTurnoverId);
        // alert(JSON.stringify(currentGoodTurnover.turnover));

        $('#inputTurnoverId'+goalId).val(currentGoodTurnover.id);
        // $('#inputTurnoverGood').val(currentGoal.deliveryDate);
        $('#inputTurnoverGood' + goalId + ' option[value=' + currentGoodTurnover.goodId + ']').prop('selected', true);
        $('#inputTurnoverQuantity'+goalId).val(currentGoodTurnover.quantity);
        $('#inputPaymentMethod' + goalId + ' option[value=' + currentGoodTurnover.paymentMethod + ']').prop('selected', true);
        $('#inputTurnover' + goalId + ' option[value=' + currentGoodTurnover.turnover + ']').prop('selected', true);
        $('#inputTurnoverPriority'+goalId).val(currentGoodTurnover.priority);
        $('#inputTurnoverLink'+goalId).val(currentGoodTurnover.link);
        $('#inputTurnoverLinkGoal'+goalId).val(currentGoodTurnover.linkGoal);
        $('#inputTurnoverLinkPoint'+goalId).val(currentGoodTurnover.linkPoint);
        $('#inputTurnoverInfo'+goalId).val(currentGoodTurnover.info);
        $('#inputTurnoverButton'+goalId).val("Изменить");
    }

    function changeTurnover(goalId){

        alert(JSON.stringify(currentGoal.goodTurnoverList));
        let currentGoodTurnover = currentGoal.goodTurnoverList.find(goodTurnover => goodTurnover.id == $('#inputTurnoverId'+goalId).val());
        alert(currentGoodTurnover);
        currentGoodTurnover.paymentMethod = $('#inputPaymentMethod' + goalId).val();
        currentGoodTurnover.priority = $('#inputTurnoverPriority' + goalId).val();
        currentGoodTurnover.link = $('#inputTurnoverLink' + goalId).val();
        currentGoodTurnover.linkGoal = $('#inputTurnoverLinkGoal' + goalId).val();
        currentGoodTurnover.linkPoint = $('#inputTurnoverLinkPoint' + goalId).val();
        currentGoodTurnover.info = $('#inputTurnoverInfo' + goalId).val();
        index = currentGoal.goodTurnoverList.findIndex(goodTurnover => goodTurnover.id === $('#inputTurnoverId'+goalId).val());
        currentGoal.goodTurnoverList[index]=currentGoodTurnover;

        alert(JSON.stringify(currentGoal.goodTurnoverList));
    }


    function submitForm(event) {
        // Отменяем стандартное поведение браузера с отправкой формы
        event.preventDefault();

        // event.target — это HTML-элемент form
        let formData = new FormData(event.target);

        // Собираем данные формы в объект

        let obj = {};
        formData.forEach((value, key) => obj[key] = value);
        if (currentGoal!=null){
            obj.goodTurnoverList = currentGoal.goodTurnoverList;
            if (obj.pointId == null) {obj.pointId = currentGoal.pointId}
        } else {
            obj.goodTurnoverList = [];
        }

        // Собираем запрос к серверу
        let request = new Request(event.target.action, {
            method: method,
            body: JSON.stringify(obj),
            headers: {
                'Content-Type': 'application/json',
            },
        });
        console.log(JSON.stringify(obj));
        alert(JSON.stringify(obj) + "method:" + method);
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

    function deleteGoal(goalId) {
        result = confirm("Удалить цель с id: " + goalId);
        if (result) {
            console.log(goalId);
            var xhr = new XMLHttpRequest();
            xhr.open('DELETE', '/rest/logist/goal/' + goalId, true);
            xhr.send();
            xhr.onreadystatechange = function () {
                if (this.readyState != 4) {
                    location.reload(true);;
                    return;
                }
                // по окончании запроса доступны:
                // status, statusText
                // responseText, responseXML (при content-type: text/xml)
                if (this.status != 200) {
                    // обработать ошибку
                    alert('ошибка: ' + (this.status ? this.statusText : 'запрос не удался'));
                    return;
                }
                // получить результат из this.responseText или this.responseXML
            }
        }
    }
</script>


</body>
</html>