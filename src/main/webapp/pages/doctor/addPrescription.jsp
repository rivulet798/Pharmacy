<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean class="com.epam.entity.Medicament" scope="page" id="medicament" />
<jsp:useBean class="com.epam.entity.User" scope="page" id="user" />
<fmt:setLocale scope="session" value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.locale" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.word.main_title" var="main_title"/>
<fmt:message bundle="${loc}" key="local.word.dosage" var="dosa"/>
<fmt:message bundle="${loc}" key="local.word.patient" var="patient"/>
<fmt:message bundle="${loc}" key="local.button.writing_the_recipe" var="writing_the_recipe"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon">
    <link href="/css/index.css" rel="stylesheet">
    <title>${main_title}</title>
</head>
<body>
    <%@include file="../header.jsp"%>
    <form action="add_prescription.do" method="post" onsubmit="return validPrescription(this)">
        <input type="hidden" name="idMedicament" value="${idMedicament}">
        <select name="user">
            <option value="" disabled selected>${patient}</option>
            <c:forEach var="user" items="${users}">
                <option value="${user.id}">${user.name} ${user.surname}</option>
            </c:forEach>
        </select>
        <select name="idDosage">
            <option value="" disabled selected>${dosa}</option>
            <c:forEach var="dosage" items="${dosages}">
                <option value="${dosage.id}">${dosage.dosage}</option>
            </c:forEach>
        </select>
        <input type="number" name="number" autocomplete="off">
        <input type="date" name="dateOfCompletion" autocomplete="off">
        <input type="hidden" name="csrfToken" value="${csrfToken}">
        <input type="submit" value="${writing_the_recipe}">
    </form>
    <%@include file="../footer.html"%>
</body>
<script src="/js/index.js"></script>
</html>
