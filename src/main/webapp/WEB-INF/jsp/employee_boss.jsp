<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <title>Директор</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>
<body>
    <h2>Информация о директоре</h2>
    <a href="/boss/employee">назад</a>
    <form id="employeeBossForm" action="/rest/boss/employee/" method="post">
        <table id="bossTable">
            <caption hidden></caption>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Фамилия</th>
                <th scope="col">Имя</th>
                <th scope="col">Отчество</th>
                <th scope="col">Дата рождения</th>
                <th scope="col">Паспорт</th>
                <th scope="col">Телефон</th>
                <th scope="col">info</th>
                <th scope="col">Act</th>
            </tr>
        </table>

    </form>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>
        emploeeBoss = ${Employee};
        boss = ${Boss};
        $('#bossTable').append($('<tr>')
            .append($('<input type="hidden" name="id" readonly value="'+ emploeeBoss.id +'">'))
            .append($('<td>').append(emploeeBoss.id))
            .append($('<td>').append(emploeeBoss.lastName))
            .append($('<td>').append(emploeeBoss.firstName))
            .append($('<td>').append(emploeeBoss.middleName))
            .append($('<td>').append(emploeeBoss.birthdate))
            .append($('<td>').append(emploeeBoss.passport))
            .append($('<td>').append(emploeeBoss.phone))
            .append($('<td>').append($('<textarea name="info">').append(boss!=null?boss.info:"")))
            .append($('<td>').append('<input type="button" value="X" onclick="deleteBoss(' + emploeeBoss.id + ')">')
                             .append($('<input type="submit" value="OK">')))
            .append('<input type="hidden" name="employee">')
        );

        document.getElementById('employeeBossForm').addEventListener('submit', submitForm);

        function submitForm(event) {
            // Отменяем стандартное поведение браузера с отправкой формы
            event.preventDefault();

            // event.target — это HTML-элемент form
            let formData = new FormData(event.target);

            // Собираем данные формы в объект
            let obj = {};
            formData.forEach((value, key) => obj[key] = value);
            // Собираем запрос к серверу
            obj.employee=boss.employee;
            let request = new Request(event.target.action + emploeeBoss.id +'/boss', {
                method: 'PUT',
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

        function deleteBoss(bossId) {
            result = confirm("Снять с должности директора сотудника с id: " + bossId);
            if (result) {
                console.log(bossId);
                var xhr = new XMLHttpRequest();
                xhr.open('DELETE', '/rest/boss/employee/' + bossId + '/boss', true);
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