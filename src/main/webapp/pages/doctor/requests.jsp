<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean class="com.bsuir.dto.RequestForRenewalDto" scope="page" id="requestForRenewalDto"/>
<fmt:setLocale scope="session" value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.locale" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.word.main_title" var="main_title"/>
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
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon">
    <link href="css/index.css" rel="stylesheet">
    <link href="css/form.css" rel="stylesheet">
    <link href="css/table.css" rel="stylesheet">
    <title>${main_title}</title>
    <style>
        @media only screen and (max-width: 981px) {
            td:nth-of-type(1):before {
                content: "${patient}";
            }
            td:nth-of-type(2):before {
                content: "${medicine}";
            }
            td:nth-of-type(3):before {
                content: "${number}";
            }
            td:nth-of-type(4):before {
                content: "${dosa}";
            }
            td:nth-of-type(5):before {
                content: "${date_of_issue}";
            }
            td:nth-of-type(6):before {
                content: "${date_of_completion}";
            }
        }
    </style>
</head>

<body>
<%@include file="../header.jsp" %>
<div class="page">
    <div class="card big">
        <table>
            <thead>
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
            </thead>
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
                            <td><a href="extend_request.do?idRequest=${requestDto.idRequest}"
                                   class="button">${extend}</a></td>
                            <td><a href="reject_request.do?idRequest=${requestDto.idRequest}"
                                   class="button">${reject}</a></td>
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
<%@include file="../forms.jsp" %>
<%@include file="../footer.html" %>
</body>
<script src="js/index.js"></script>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
</html>
