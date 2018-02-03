<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean class="com.epam.dto.RequestForRenewalDto" scope="page" id="requestForRenewalDto" />
<fmt:setLocale scope="session" value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.locale" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.word.patient" var="patient"/>
<fmt:message bundle="${loc}" key="local.word.medicine" var="medicine"/>
<fmt:message bundle="${loc}" key="local.word.number" var="number"/>
<fmt:message bundle="${loc}" key="local.word.dosage" var="dosa"/>
<fmt:message bundle="${loc}" key="local.word.date_of_issue" var="date_of_issue"/>
<fmt:message bundle="${loc}" key="local.word.date_of_completion" var="date_of_completion"/>
<fmt:message bundle="${loc}" key="local.word.extend" var="extend"/>
<fmt:message bundle="${loc}" key="local.word.reject" var="reject"/>
<fmt:message bundle="${loc}" key="local.word.unit_of_dosage" var="unit_of_dosage"/>
<fmt:message bundle="${loc}" key="local.sentence.nothing_found" var="nothing_found"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon">
    <link href="/css/index.css" rel="stylesheet">
    <title>PHARMACY</title>
</head>

<body>
<%@include file="../header.jsp"%>
<div class="page">
    <div class="card">
    <table>
        <tr>
            <th>${patient}</th>
            <th>${medicine}</th>
            <th>${number}</th>
            <th>${dosa}</th>
            <th>${date_of_issue}</th>
            <th>${date_of_completion}</th>
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
                        <td>${requestDto.dosage} ${unit_of_dosage}</td>
                        <td>${requestDto.dateOfIssue}</td>
                        <td>${requestDto.dateOfCompletion}</td>
                        <a href="?${requestDto.idRequest}"><td>${extend}</td></a>
                        <a href=""><td>${reject}</td></a>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <h2>${nothing_found}</h2>
            </c:otherwise>
        </c:choose>
    </table>
    </div>
</div>
<%@include file="../forms.jsp"%>
<%@include file="../footer.html"%>
</body>
<script src="/js/index.js"></script>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
</html>
