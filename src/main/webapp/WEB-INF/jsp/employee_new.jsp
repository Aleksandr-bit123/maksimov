<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <title>Сотрудники</title>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
    <style>
        #divLogist,#divDriver,#divBoss {
            text-align: center;
            border: black 1px solid;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<a href="/boss">назад</a>
<h2>Сотрудники</h2>
<table><%--MainTable--%>
    <caption hidden></caption>
    <tr hidden>
        <th scope="col"></th>
        <th scope="col"></th>
    </tr>
    <tr>
        <td id="tdForm" style="vertical-align: top;"> <%--Ячейка для форм--%>
            <form action="/rest/boss/employee" method="post" id="employeeForm">
                <div><label for="inputId">ID</label></div>
                <div><input id="inputId" type="text" name="id" placeholder="id" readonly></div>
                <div><label for="selectUsername">Username</label></div>
                <select id="selectUsername" onchange="selectUsernamefunc(this)">
                    <option disabled selected>Выберите username</option>
                </select>
                <div><label for="inputLastName">Фамилия</label></div>
                <div><input id="inputLastName" type="search" name="lastName" placeholder="Фамилия"/></div>
                <div><label for="inputFirstName">Имя</label></div>
                <div><input id="inputFirstName" type="text" name="firstName" placeholder="Имя"/></div>
                <div><label for="inputMiddleName">Отчество</label></div>
                <div><input id="inputMiddleName" type="text" name="middleName" placeholder="Отчество"/></div>
                <div><label for="inputBirthdate"></label>Дата рождения</div>
                <div><input id="inputBirthdate" type="date" name="birthdate" data-date-format="YYYY-MMMM-DD"/></div>
                <div><label for="inputPassport"></label>Паспорт</div>
                <div><input id="inputPassport" type="text" name="passport" placeholder="паспорт"/></div>
                <div><label for="inputPhone"></label>Телефон</div>
                <div><input id="inputPhone" type="text" name="phone" placeholder="телефон"/></div>
                <input id="inputBoss" type="hidden" name="boss"/>
                <input id="inputLogist" type="hidden" name="logist"/>
                <input id="inputDriver" type="hidden" name="driver"/>
                <div><input id="inputSubmit" type="submit" value="Добавить сотрудника"/></div>
            </form>
            <div id="divBoss"></div>
            <div id="divLogist"></div>
            <div id="divDriver"></div>
        </td>

        <td style="vertical-align: top"> <%--Ячейка для списка сотрудников--%>
            <table id="employeeTable">
                <caption hidden></caption>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">UserName</th>
                    <th scope="col">Фамилия</th>
                    <th scope="col">Имя</th>
                    <th scope="col">Отчество</th>
                    <th scope="col">Дата рождения</th>
                    <th scope="col">Паспорт</th>
                    <th scope="col">Телефон</th>
                    <th scope="col">Директор</th>
                    <th scope="col">Логист</th>
                    <th scope="col">Водитель</th>
                    <th scope="col">X</th>
                </tr>

            </table>
        </td>
    </tr>
</table>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript"> <%--Заполняем список сотрудников--%>
employeeList = ${allUsers};

employeeList.forEach(function (itemUser, i, arrUser) {
    if (itemUser.employeeEntity != null) {
        //заполняем таблицу сотрудников
        $('#employeeTable').append($('<tr onclick="fillForm(this.firstChild.innerHTML)">')
            .append($('<td>').append(itemUser.id))
            .append($('<td>').append(itemUser.username))
            .append($('<td>').append(itemUser.employeeEntity.lastName))
            .append($('<td>').append(itemUser.employeeEntity.firstName))
            .append($('<td>').append(itemUser.employeeEntity.middleName))
            .append($('<td>').append(itemUser.employeeEntity.birthdate))
            .append($('<td>').append(itemUser.employeeEntity.passport))
            .append($('<td>').append(itemUser.employeeEntity.phone))
            .append($('<td>').append(itemUser.employeeEntity.boss == true ? "да" : "нет"))
            .append($('<td>').append(itemUser.employeeEntity.logist == true ? "да" : "нет"))
            .append($('<td>').append(itemUser.employeeEntity.driver == true ? "да" : "нет"))
            .append($('<td>').append('<input type="button" value="X" onclick="deleteEmployee(' + itemUser.id + ')">'))
        );
    } else {
        //заполняем список имен (username) всех зарегистрированных users
        $('#selectUsername').append($('<option value="' + itemUser.id + '">').append(itemUser.username));
    }

});

</script>
<script>
    function fillForm(id) {
        document.getElementById("selectUsername").setAttribute("disabled",true);

        document.getElementById("inputSubmit").value = "Изменить";

        employee = employeeList.find(employee => employee.id == id);

        document.getElementById("inputId").value = employee.id;
        // document.getElementById("inputUsername").value = employee.username;
        document.getElementById("inputLastName").value = employee.employeeEntity.lastName;
        document.getElementById("inputFirstName").value = employee.employeeEntity.firstName;
        document.getElementById("inputMiddleName").value = employee.employeeEntity.middleName;
        document.getElementById("inputBirthdate").value = employee.employeeEntity.birthdate;
        document.getElementById("inputPassport").value = employee.employeeEntity.passport;
        document.getElementById("inputPhone").value = employee.employeeEntity.phone;
        document.getElementById("inputBoss").value = employee.employeeEntity.boss;
        document.getElementById("inputLogist").value = employee.employeeEntity.logist;
        document.getElementById("inputDriver").value = employee.employeeEntity.driver;
        if (employee.employeeEntity.boss == true) {
            document.getElementById("divBoss").innerHTML = '<h4 align="center">Директор</h4><a href="${contextPath}/boss/employee/' + id + '/boss">редактировать</a>';
        } else {
            document.getElementById("divBoss").innerHTML = '<a href="${contextPath}/boss/employee/' + id + '/boss">назначить директором</a>';
        }
        if (employee.employeeEntity.logist == true) {
            document.getElementById("divLogist").innerHTML = '<h4 align="center">Логист</h4><a href="${contextPath}/boss/employee/' + id + '/logist">редактировать</a>';
        } else {
            document.getElementById("divLogist").innerHTML = '<a href="${contextPath}/boss/employee/' + id + '/logist">назначить логистом</a>';
        }
        if (employee.employeeEntity.driver == true) {
            document.getElementById("divDriver").innerHTML = '<h4 align="center">Водитель</h4><a href="${contextPath}/boss/employee/' + id + '/driver">редактировать</a>';
        } else {
            document.getElementById("divDriver").innerHTML = '<a href="${contextPath}/boss/employee/' + id + '/driver">назначить водителем</a>';
        }

    }

    document.getElementById('employeeForm').addEventListener('submit', submitForm);

    function selectUsernamefunc(select) {
        // alert(select);
        document.getElementById("inputId").value = select.value;
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
        // alert(JSON.stringify(obj));
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

    function deleteEmployee(employeeId) {
        result = confirm("Удалить сотрудника с id: " + employeeId);
        if (result) {
            console.log(employeeId);
            var xhr = new XMLHttpRequest();
            xhr.open('DELETE', '/rest/boss/employee/' + employeeId, true);
            xhr.send();
            xhr.onreadystatechange = function () {
                if (this.readyState !== 4) {
                    location.reload(true); /*true - загрузка с сервера , false - с кеша*/
                    return;
                }
                // по окончании запроса доступны:
                // status, statusText
                // responseText, responseXML (при content-type: text/xml)
                if (this.status !== 200) {
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