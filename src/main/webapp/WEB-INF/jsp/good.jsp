<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <title>Товары</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>
<body>
<h2>Товары</h2>
<a href="/boss">назад</a>
<table> <!-- Main table -->
    <tr>
        <td id="tdForm" style="vertical-align: top; "> <%--Ячейка для форм--%>
            <form id="goodForm" action="" method="post" >
                <div>ID</div><div><input id="inputId" type="number" name="id" placeholder="id" min="0" step="1" required></div>
                <div>Наименование</div><div><input id="inputName" type="search" name="name" placeholder="Наименование"/></div>
                <div>Цена</div><div><input id="inputCost" type="number" name="cost" placeholder="Цена" min="0" step="any"/></div>
                <div>Объем</div><div><input id="inputVolume" type="number" name="volume" placeholder="Объем" min="0" step="0.1"/></div>
                <div>info</div><div><textarea id="inputInfo" name="info" placeholder="info"></textarea></div>
                <div><input id="inputSubmit" type="submit" value="Добавить товар"/></div>
                <div><input type="reset" value="Очистить" onclick="clearGoodForm()"/></div>
            </form>
        </td>

        <td style="vertical-align: top"> <%--Ячейка для списка сотрудников--%>
            <table id ="goodTable"> <!-- Таблица товаров -->
            <caption>Таблица товаров</caption>
                <tr>
                    <th>ID</th>
                    <th>Наименование</th>
                    <th>Цена</th>
                    <th>Объем</th>
                    <th>info</th>
                    <th>X</th>
                </tr>

            </table>
        </td>
    </tr>
</table>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script> <%--Заполняем список сотрудников--%>
goods = ${goods};
method = 'POST';
goods.forEach(function(good, i, arrUser) {
    $('#goodTable').append($('<tr onclick="fillForm(this.firstChild.innerHTML)">')
        .append($('<td>').append(good.id))
        .append($('<td>').append(good.name))
        .append($('<td>').append(good.cost))
        .append($('<td>').append(good.volume))
        .append($('<td>').append(good.info))
        .append($('<td>').append('<input type="button" value="X" onclick="deleteGood(' + good.id + ')">'))
    );
});

</script>
<script>

document.getElementById('goodForm').addEventListener('submit', submitForm);
$('#goodForm').attr("action","/rest/boss/good/");

function fillForm(id) {

    good = goods.find(good => good.id == id);

    $('#inputId').val(good.id);
    $('#inputName').val(good.name);
    $('#inputCost').val(good.cost);
    $('#inputVolume').val(good.volume);
    $('#inputInfo').val(good.info);
    $('#inputSubmit').val("Изменить");

    method = 'PUT';
    $('#goodForm').attr("action", "/rest/boss/good/"+good.id);
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
        method: method,
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

function deleteGood(goodId){
    result = confirm("Удалить товар с id: " + goodId);
    if (result) {
        console.log(goodId);
        var xhr = new XMLHttpRequest();
        xhr.open('DELETE', '/rest/boss/good/' + goodId, true);
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

function clearGoodForm(){
    $('#inputSubmit').val("Добавить");
    method = 'POST';
    $('#goodForm').attr("action", "/rest/boss/good/");
}
</script>
</body>
</html>