<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean class="com.epam.entity.User" scope="page" id="user"/>
<jsp:useBean class="com.epam.entity.Medicament" scope="page" id="medicament"/>
<jsp:useBean class="com.epam.dto.PrescriptionDto" scope="page" id="prescriptionDto"/>
<fmt:setLocale scope="session" value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.locale" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.word.main_title" var="main_title"/>
<fmt:message bundle="${loc}" key="local.word.medicine" var="medicine"/>
<fmt:message bundle="${loc}" key="local.word.number" var="number"/>
<fmt:message bundle="${loc}" key="local.word.dosage" var="dosa"/>
<fmt:message bundle="${loc}" key="local.word.date_of_issue" var="date_of_issue"/>
<fmt:message bundle="${loc}" key="local.word.date_of_completion" var="date_of_completion"/>
<fmt:message bundle="${loc}" key="local.word.doctor" var="doctor"/>
<fmt:message bundle="${loc}" key="local.button.request_an_extension" var="request_an_extension"/>
<fmt:message bundle="${loc}" key="local.sentence.nothing_found" var="nothing_found"/>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
    <link href="css/index.css" rel="stylesheet">
    <link href="css/form.css" rel="stylesheet">
    <link href="css/table.css" rel="stylesheet">
    <title>${main_title}</title>
    <style>
        @media only screen and (max-width: 981px) {
            td:nth-of-type(1):before {
                content: "${medicine}";
            }

            td:nth-of-type(2):before {
                content: "${number}";
            }

            td:nth-of-type(3):before {
                content: "${dosa}";
            }

            td:nth-of-type(4):before {
                content: "${date_of_issue}";
            }

            td:nth-of-type(5):before {
                content: "${date_of_completion}";
            }

            td:nth-of-type(6):before {
                content: "${doctor}";
            }
        }
    </style>
</head>
<body>
<%@include file="../header.jsp" %>
<div class="page">
    <div class="card big">
        <table>
            <c:choose>
                <c:when test="${prescriptions!=null}">
                    <thead>
                    <tr>
                        <th>${medicine}</th>
                        <th>${number}</th>
                        <th>${dosa}</th>
                        <th>${date_of_issue}</th>
                        <th>${date_of_completion}</th>
                        <th>${doctor}</th>
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
                            <td><a class="button"
                                   href="add_request.do?idPrescription=${prescriptionDto.idPrescription}">${request_an_extension}</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
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
