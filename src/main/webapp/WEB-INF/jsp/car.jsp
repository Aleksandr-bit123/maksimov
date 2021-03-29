<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <title>авто сотрудника</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>
<body>
<h2>Информация об автомобилях водителя</h2>
<a id="backLink" href="#">назад</a>
<h3>Водитель</h3>
    <table id="driverTable"> <!-- Description -->
        <caption hidden></caption>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Фамилия</th>
            <th scope="col">Имя</th>
            <th scope="col">Отчество</th>
        </tr>
    </table>

<h3>Автомобили</h3>
    <table> <!-- Description -->
        <caption hidden></caption>
        <tr hidden>
            <th scope="col"></th>
            <th scope="col"></th>
        </tr>
        <tr>
            <td>
                <form id="carForm" action="" method="post">
                    <div><input id="inputId" type="text" name="id" placeholder="auto" readonly></div>
                    <div><input id="inputBrand" type="text" name="brand" placeholder="Марка"></div>
                    <div><input id="inputStateRegistrationMark" type="text" name="stateRegistrationMark" placeholder="Гос. номер"></div>
                    <div><input id="inputCubicCapacity" type="number" name="cubicCapacity" placeholder="Вместимость" min="0" max="100" step="0.1"></div>
                    <div><input id="inputOwnerId" type="hidden" name="ownerId" ></div>
                    <div><input id="inputFuelConsumption" type="number" name="fuelConsumption" placeholder="Расход" min="0" max="100" step="0.1"></div>
                    <div><textarea id="inputInfo" name="info" placeholder="info"></textarea></div>
                    <div><input id="inputSubmit" type="submit" value="Добавить"></div>
                    <div><input type="reset" value="Очистить" onclick="clearCarForm()"></div>
                </form>
            </td>
            <td>
                <table id="carTable"> <!-- Description -->
                    <caption hidden></caption>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Марка</th>
                        <th scope="col">гос. номер</th>
                        <th scope="col">Вместимость</th>
                        <th scope="col">Расход</th>
                        <th scope="col">info</th>
                        <th scope="col">X</th>
                    </tr>
                </table>
            </td>
        </tr>
    </table>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
    emploeeDriver = ${Employee};
    cars = ${Cars};

    $('#driverTable').append($('<tr>')
        .append($('<td>').append(emploeeDriver.id))
        .append($('<td>').append(emploeeDriver.lastName))
        .append($('<td>').append(emploeeDriver.firstName))
        .append($('<td>').append(emploeeDriver.middleName))

    );

    cars.forEach(function(car, i, arr) {
        $('#carTable').append($('<tr onclick="fillForm(this.firstChild.innerHTML)">')
            .append($('<td id="tdCarId">').append(car.id))
            .append($('<td>').append(car.brand))
            .append($('<td>').append(car.stateRegistrationMark))
            .append($('<td>').append(car.cubicCapacity))
            .append($('<td>').append(car.fuelConsumption))
            .append($('<td>').append(car.info))
            .append($('<td>').append($('<input type="button" value="X">').attr("onclick","deleteCar('"+car.id+"')")))
        );
    });



    document.getElementById('carForm').addEventListener('submit', submitForm);
    $('#carForm').attr("action","/rest/boss/employee/" + emploeeDriver.id + "/driver/car")
    $('#inputOwnerId').attr("value",emploeeDriver.id);
    $('#backLink').attr("href","/boss/employee/" + emploeeDriver.id + "/driver");

    function fillForm(id) {

        car = cars.find(car => car.id == id);

        $('#inputId').val(car.id);
        $('#inputBrand').val(car.brand);
        $('#inputStateRegistrationMark').val(car.stateRegistrationMark);
        $('#inputCubicCapacity').val(car.cubicCapacity);
        $('#inputOwnerId').val(car.ownerId);
        $('#inputFuelConsumption').val(car.fuelConsumption);
        $('#inputInfo').val(car.info);
        $('#inputSubmit').val("Изменить");

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
        console.log( JSON.stringify(obj));
        // alert(JSON.stringify(obj));
        // Отправляем (асинхронно!)
        fetch(request).then(
            function(response) {
                // Запрос успешно выполнен
                console.log(response);
                alert("статус выполнения запроса: "+ response.status + ", " + response.ok);
                // return response.json() и так далее см. документацию
                location.reload(true); /*true - загрузка с сервера , false - с кеша*/
            },
            function(error) {
                // Запрос не получилось отправить
                console.error(error);
            }
        );

        // Код после fetch выполнится ПЕРЕД получением ответа
        // на запрос, потому что запрос выполняется асинхронно,
        // отдельно от основного кода
        console.log('Запрос отправляется');
    }

    function deleteCar(carId){
        result = confirm("Удалить машину с id: " + carId);
        if (result) {
            console.log(carId);
            var xhr = new XMLHttpRequest();
            xhr.open('DELETE', '/rest/boss/employee/' + emploeeDriver.id + '/driver/car/' + carId, true);
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

    function clearCarForm(){
        $('#inputSubmit').val("Добавить");
    }
</script>
</body>
</html>