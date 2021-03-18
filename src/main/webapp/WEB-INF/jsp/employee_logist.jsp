<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Сотрудники</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>
<body>
<h2>Информация о логисте</h2>
<a href="/boss/employee">назад</a>
<form id="employeeLogistForm" action="/rest/boss/employee/" method="post">
    <table id="logistTable">
        <tr>
            <th>ID</th>
            <th>Фамилия</th>
            <th>Имя</th>
            <th>Отчество</th>
            <th>Дата рождения</th>
            <th>Паспорт</th>
            <th>Телефон</th>
            <th>info</th>
            <th>Act</th>
        </tr>
    </table>

</form>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
    emploeeLogist = ${Employee};
    logist = ${Logist};
    $('#logistTable').append($('<tr>')
        .append($('<input type="hidden" name="id" readonly value="'+ emploeeLogist.id +'">'))
        .append($('<td>').append(emploeeLogist.id))
        .append($('<td>').append(emploeeLogist.lastName))
        .append($('<td>').append(emploeeLogist.firstName))
        .append($('<td>').append(emploeeLogist.middleName))
        .append($('<td>').append(emploeeLogist.birthdate))
        .append($('<td>').append(emploeeLogist.passport))
        .append($('<td>').append(emploeeLogist.phone))
        .append($('<td>').append($('<textarea name="info">').append(logist!=null?logist.info:"")))
        .append($('<td>').append('<input type="button" value="X" onclick="deleteLogist('+ emploeeLogist.id +')">').append($('<input type="submit" value="OK">')))
    );

    document.getElementById('employeeLogistForm').addEventListener('submit', submitForm);

    function submitForm(event) {
        // Отменяем стандартное поведение браузера с отправкой формы
        event.preventDefault();

        // event.target — это HTML-элемент form
        let formData = new FormData(event.target);

        // Собираем данные формы в объект
        let obj = {};
        formData.forEach((value, key) => obj[key] = value);
        // Собираем запрос к серверу

        let request = new Request(event.target.action + emploeeLogist.id +'/logist', {
            method: 'PUT',
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

    function deleteLogist(logistId) {
        result = confirm("Снять с должности директора сотудника с id: " + logistId);
        if (result) {
            console.log(logistId);
            var xhr = new XMLHttpRequest();
            xhr.open('DELETE', '/rest/boss/employee/' + logistId + '/logist', true);
            xhr.send();
            xhr.onreadystatechange = function () {
                if (this.readyState != 4) {
                    location.replace('/boss/employee');
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