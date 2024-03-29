<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="now" class="java.util.Date"/>


<!DOCTYPE HTML>
<html lang="ru">
<head>
    <title>Главная</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>
<body>
<div>
    <h3>${pageContext.request.userPrincipal.name}</h3>
    <sec:authorize access="!isAuthenticated()">
        <h4>Для доступа ко всем ссылкам - login: admin, password: admin</h4>
        <h4><a href="/login">Войти</a></h4>
        <h4><a href="/registration">Зарегистрироваться</a></h4>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <h4><a href="/logout">Выйти</a></h4>
    </sec:authorize>
    <h4><a href="/news">Новости (только пользователь)</a></h4>
    <h4><a href="/admin">Пользователи (только админ)</a></h4>
    <h4><a href="/driver/<fmt:formatDate type="time" value="${now}" pattern="dd-MM-yyyy"/>">Водители (только водители)</a></h4>
    <h4><a href="/logist">Логисты (только логисты)</a></h4>
    <h4><a href="/boss">Директора (только директора)</a></h4>
</div>
</body>
</html>