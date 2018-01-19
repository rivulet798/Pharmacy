<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean class="com.epam.dto.RequestForRenewalDto" scope="page" id="requestForRenewalDto" />
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon">
    <style><%@include file="/css/index.css"%></style>
    <script><%@include file="/js/index.js"%></script>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <title>PHARMACY</title>
</head>

<body>
<%@include file="../header.html"%>
<div class="page">
    <div class="card">
    <table>
        <tr>
            <th>Пациент</th>
            <th>Лекарство</th>
            <th>Количество</th>
            <th>Дозировка</th>
            <th>Дата выдачи</th>
            <th>Действителен до</th>
            <th></th>
            <th></th>
        </tr>
        <c:choose>
            <c:when test="${requestForRenewalList!=null}">
                <c:forEach var="requestDto" items="${requestForRenewalList}">
                    <tr>
                        <td>${requestDto.userName} ${requestDto.userSurname}</td>
                        <td>${requestDto.medicamentName}</td>
                        <td>${requestDto.number}</td>
                        <td>${requestDto.dosage} мг</td>
                        <td>${requestDto.dateOfIssue}</td>
                        <td>${requestDto.dateOfCompletion}</td>
                        <a href="?${requestDto.idRequest}"><td>Продлить</td></a>
                        <a href=""><td>Отклонить</td></a>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <h2>Ничего не найдено</h2>
            </c:otherwise>
        </c:choose>
    </table>
    </div>
</div>
<%@include file="../forms.html"%>
<%@include file="../footer.html"%>
</body>
</html>
