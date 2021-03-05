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
<div>
    <h2>Сотрудники <br> Только для залогинившихся директоров.</h2>
    <a href="/">Главная</a>

</div>

<div>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>UserName</th>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Отчество</th>
            <th>Дата рождения</th>
            <th>Паспорт</th>
            <th>Телефон</th>
            <th>Должность</th>
        </tr>
        </thead>
        <c:forEach items="${allUsers}" var="user">
            <tr onclick="showForm(this)">
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.employeeEntity.getFirstName()}</td>
                <td>${user.employeeEntity.getLastName()}</td>
                <td>${user.employeeEntity.getMiddleName()}</td>
                <td>${user.employeeEntity.getBirthdate()}</td>
                <td>${user.employeeEntity.getPassport()}</td>
                <td>${user.employeeEntity.getPhone()}</td>
                <td>
                    <c:forEach items="${user.employeeEntity.getPositions()}" var="position">${position.name}; </c:forEach>
                </td>
                <td >
                    <form method="post" action="${pageContext.request.contextPath}/boss/employee/${user.id}">
<%--                        <input type="hidden" name="userId" value="${user.id}"/>--%>
<%--                        <input type="hidden" name="action" value="delete"/>--%>
                        <button type="submit">Удалить</button>
                    </form>
                </td>
            </tr>
            <tr hidden>
                <td>
                    <form action="${pageContext.request.contextPath}/boss/employee" method="post" modelAttribute="employeeForm" class="benderform">
                        <div><input type="hidden" name="id" value="${user.getId()}"></div>
                        <div><input type="text" name="firstName"  value="${user.employeeEntity.firstName}"/></div>
                        <div><input type="text" name="lastName" value="${user.employeeEntity.getLastName()}"/></div>
                        <div><input type="text" name="middleName" value="${user.employeeEntity.getMiddleName()}"/></div>
                        <div><input type="date" name="birthdate" value="${user.employeeEntity.getBirthdate()}" data-date-format="YYYY-MMMM-DD"/></div>
                        <div><input type="text" name="passport" value="${user.employeeEntity.getPassport()}"/></div>
                        <div><input type="text" name="phone" value="${user.employeeEntity.getPhone()}"/></div>
<%--                        <input type="hidden" name="positions" value="${user.employeeEntity.getPositions()}"/>--%>
<%--                        <input type="hidden" name="bossEntity" value="${user.employeeEntity.getBossEntity()}"/>--%>
<%--                        <input type="hidden" name="logistEntity" value="${user.employeeEntity.getLogistEntity()}"/>--%>
<%--                        <input type="hidden" name="driverEntity" value="${user.employeeEntity.getDriverEntity()}"/>--%>


                        <div><input type="submit" /></div>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>


<script type="text/javascript" charset="UTF-8">
    function showForm(tr){
        if (tr.nextSibling.nextSibling.hidden == true) {
            tr.nextSibling.nextSibling.hidden = false;
        } else {
            tr.nextSibling.nextSibling.hidden = true;
        }
        // tr.nextSibling.nextSibling.firstChild.addEventListener('submit', submitForm);
        // alert(tr.nextSibling.nextSibling.getElementsByTagName("form")[0].getAttribute('method'));
        tr.nextSibling.nextSibling.getElementsByTagName("form")[0].addEventListener('submit', submitForm);
        //TODO Посмотреть еще варианты выборки
    }
</script>

    <script type="text/javascript" charset="UTF-8">

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
</script>
</body>
</html>