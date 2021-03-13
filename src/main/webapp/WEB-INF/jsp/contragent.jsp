<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <title>Контрагенты</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>
<body>
<h2>Контрагенты</h2>
<a href="/boss">назад</a>
<table> <!-- Main table -->
    <tr>
        <td id="tdForm" style="vertical-align: top; "> <%--Ячейка для форм--%>
            <form id="contragentForm" action="" method="post" >
                <div>ID</div><div><input id="inputId" type="text" name="id" placeholder="id" readonly value="null"></div>
                <div>Наименование</div><div><input id="inputName" type="search" name="name" placeholder="Наименование"/></div>
                <input id="inputPoint" type="hidden" name="point"/>
                <div>Адрес</div><div><input id="inputAddress" type="text" placeholder="Адрес" /></div>
                <div>Телефон</div><div><input id="inputPhone" type="text" placeholder="Телефон" /></div>
                <div>info</div><div><textarea id="inputInfo" name="info" placeholder="info"></textarea></div>
                <div><input id="inputSubmit" type="submit" value="Добавить"/></div>
                <div><input type="reset" value="Очистить" onclick="clearContragentForm()"/></div>
            </form>
        </td>

        <td style="vertical-align: top"> <%--Ячейка для списка сотрудников--%>
            <table id ="goodTable"> <!-- Таблица товаров -->
                <caption>Таблица контрагентов</caption>
                <tr>
                    <th>ID</th>
                    <th>Наименование</th>
                    <th>Адрес</th>
                    <th>Телефон</th>
                    <th>info</th>
                    <th>X</th>
                </tr>

            </table>
        </td>
    </tr>
</table>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script> <%--Заполняем список сотрудников--%>
contragents = ${Contragents};
contragents.forEach(function(contragent, i, arrUser) {
    $('#goodTable').append($('<tr onclick="fillForm(this.firstChild.innerHTML)">')
        .append($('<td>').append(contragent.id))
        .append($('<td>').append(contragent.name))
        .append($('<td>').append(contragent.point.address))
        .append($('<td>').append(contragent.point.phone))
        .append($('<td>').append(contragent.info))
        .append($('<td>').append('<input type="button" value="X" onclick="deleteContragent(' + contragent.id + ')">'))
    );
});
</script>

<script>

    document.getElementById('contragentForm').addEventListener('submit', submitForm);
    $('#contragentForm').attr("action","/rest/boss/contragent/");

    function fillForm(id) {

        contragent = contragents.find(contragent => contragent.id == id);

        $('#inputId').val(contragent.id);
        $('#inputName').val(contragent.name);
        $('#inputAddress').val(contragent.point.address);
        $('#inputPhone').val(contragent.point.phone);
        $('#inputInfo').val(contragent.info);
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
        console.log( JSON.stringify(obj));
        alert(JSON.stringify(obj));
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

    function deleteContragent(contragentId){
        result = confirm("Удалить контрагента с id: " + contragentId);
        if (result) {
            console.log(contragentId);
            var xhr = new XMLHttpRequest();
            xhr.open('DELETE', '/rest/boss/contragent/' + contragentId, true);
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

    function clearContragentForm(){
        $('#inputSubmit').val("Добавить");
    }
</script>
</body>
</html>