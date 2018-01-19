<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean class="com.epam.entity.User" scope="page" id="user" />
<jsp:useBean class="com.epam.entity.Medicament" scope="page" id="medicament" />
<jsp:useBean class="com.epam.dto.PrescriptionDto" scope="page" id="prescriptionDto" />

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
        <c:choose>
            <c:when test="${prescriptions!=null}">
                <thead>
                    <tr>
                        <th>Лекарство</th>
                        <th>Количество</th>
                        <th>Дозировка</th>
                        <th>Дата выдачи</th>
                        <th>Действителен до</th>
                        <th>Врач</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="prescriptionDto" items="${prescriptions}">
                    <tr>
                        <td>${prescriptionDto.medicamentName}</td>
                        <td>${prescriptionDto.number}</td>
                        <td>${prescriptionDto.dosage}</td>
                        <td>${prescriptionDto.dateOfIssue}</td>
                        <td>${prescriptionDto.dateOfCompletion}</td>
                        <td>${prescriptionDto.doctorName} ${prescriptionDto.doctorSurname}</td>
                        <td><a class="button" href="/add_request.do?idPrescription=${prescriptionDto.idPrescription}">Запросить продление</a></td>
                    </tr>
                </c:forEach>
                </tbody>
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
