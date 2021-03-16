<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <title>Заказы</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>
<body>
<h2>Заказы</h2>
<a href="/logist">назад</a>
<table> <!-- Main table -->
    <tr>
        <td id="tdForm" style="vertical-align: top; "> <%--Ячейка для форм--%>
            <form id="orderForm" action="" method="post">
                <div><label for="inputId">ID</label></div>
                <div><input id="inputId" type="text" name="id" placeholder="id" readonly value="null"></div>
                <div><label for="inputClient">Клиент</label></div>
                <div><select id="inputClient" name="client"></select></div>
                <div><label for="inputGood">Товар</label></div>
                <div><select id="inputGood" name="good"></select></div>
                <div><label for="inputQuantity">Количество</label></div>
                <div><input id="inputQuantity" type="number" name="quantity" placeholder="0" value="0" min="0"></div>
                <div><label for="inputDeliveryDate">Дата доставки</label></div>
                <div><input id="inputDeliveryDate" type="date" name="deliveryDate" data-date-format="YYYY-MMMM-DD"></div>
                <div><label for="inputInfo">info</label></div>
                <div><textarea id="inputInfo" name="info" placeholder="info"></textarea></div>
                <div><input id="inputSubmit" type="submit" value="Добавить"/></div>
                <div><input type="reset" value="Очистить" onclick="clearClientForm()"/></div>
            </form>
        </td>

        <td style="vertical-align: top"> <%--Ячейка для списка сотрудников--%>
            <table id="orderTable"> <!-- Таблица товаров -->
                <caption>Таблица заказов</caption>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Клиент</th>
                    <th scope="col">Товар</th>
                    <th scope="col">Количество</th>
                    <th scope="col">Дата доставки</th>
                    <th scope="col">X</th>
                </tr>

            </table>
        </td>
    </tr>
</table>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>

    document.getElementById('orderForm').addEventListener('submit', submitForm);
    $('#orderForm').attr("action", "/rest/logist/order/");

    orders = ${orders};
    orders.forEach(function (order, i, arrOrder) {
    $('#orderTable').append($('<tr onclick="fillForm('+ order.id + ')">')
        .append($('<td>').append(order.id))
        .append($('<td>').append(order.client.id))
        .append($('<td>').append(order.good.id))
        .append($('<td>').append(order.quantity))
        .append($('<td>').append(order.deliveryDate))
        .append($('<td>').append('<input type="button" value="X" onclick="deleteOrder(' + order.id + ')">'))
    );
    });

    clients = ${clients};
    clients.forEach(function (client, i, arrClient) {
        $('#inputClient').append($('<option>').val(client.id).append(client.lastName));
    });

    function fillForm(id) {
        let order = orders.find(order => order.id == id);

        $('#inputId').val(order.id);
        $('#inputLastName').val(client.lastName);
        $('#inputFirstName').val(client.firstName);
        $('#inputMiddleName').val(client.middleName);
        $('#inputPassport').val(client.passport);
        $('#inputAddress').val(client.point.address);
        $('#inputPhone').val(client.point.phone);
        $('#inputInfo').val(client.info);
        $('#inputSubmit').val("Изменить");
    }

    function submitForm(event) {
        // Отменяем стандартное поведение браузера с отправкой формы
        event.preventDefault();

        // event.target — это HTML-элемент form
        let formData = new FormData(event.target);

        // Собираем данные формы в объект
        let point = {     // объект
            id: $('#inputId').val(),  // под ключом "name" хранится значение "John"
            address: $('#inputAddress').val(),
            phone: $('#inputPhone').val()// под ключом "age" хранится значение 30
        };
        // point.id = $('#inputId').val();
        // point.address = $('#inputAddress').val();
        // point.phone = $('#inputPhone').val();

        let obj = {};
        formData.forEach((value, key) => obj[key] = value);
        // Собираем запрос к серверу
        obj.point = point;
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