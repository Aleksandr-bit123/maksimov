<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <title>Директор</title>
</head>
<body>
<div>
    <h2>Страница директора</h2>
    <h3>Только для залогинившихся директоров.</h3>
    <h4><a href="/">Главная</a></h4>
    <h4><a href="/boss/employee">Сотрудники</a></h4>
    <h4><a href="/boss/good">Товары</a></h4>
    <h4><a href="/boss/contragent">Контрагенты</a></h4>
    <h4><a href="rest/boss/employee/boss">Список всех директоров</a></h4>
    <h4><a href="rest/boss/employee/logist">Список всех логистов</a></h4>
    <h4><a href="rest/boss/employee/driver">Список всех водителей</a></h4>
    <h4><a href="rest/boss/employee/driver/car">Список всех автомобилей водителей</a></h4>
</div>

</body>
</html>