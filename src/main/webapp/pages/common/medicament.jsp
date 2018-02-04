<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean class="com.epam.entity.Medicament" scope="page" id="medicament" />
<jsp:useBean class="com.epam.entity.User" scope="page" id="user" />
<jsp:useBean class="com.epam.dto.PrescriptionDto" scope="page" id="prescription" />
<fmt:setLocale scope="session" value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.locale" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.word.main_title" var="main_title"/>
<fmt:message bundle="${loc}" key="local.word.producer" var="producer"/>
<fmt:message bundle="${loc}" key="local.word.price" var="price"/>
<fmt:message bundle="${loc}" key="local.word.unit_of_price" var="unit_of_price"/>
<fmt:message bundle="${loc}" key="local.sentence.released_by_prescription" var="released_by_prescription"/>
<fmt:message bundle="${loc}" key="local.sentence.released_without_prescription" var="released_without_prescription"/>
<fmt:message bundle="${loc}" key="local.sentence.is_available" var="is_available"/>
<fmt:message bundle="${loc}" key="local.sentence.not_available" var="not_available"/>
<fmt:message bundle="${loc}" key="local.word.mode_of_application" var="mode_of_application"/>
<fmt:message bundle="${loc}" key="local.word.contraindications" var="contraindications"/>
<fmt:message bundle="${loc}" key="local.word.side_effects" var="side_effects"/>
<fmt:message bundle="${loc}" key="local.word.dosage" var="dosa"/>
<fmt:message bundle="${loc}" key="local.sentence.number_of_packages" var="number_of_packages"/>
<fmt:message bundle="${loc}" key="local.word.order" var="order"/>
<fmt:message bundle="${loc}" key="local.sentence.select_a_recipe" var="select_a_recipe"/>
<fmt:message bundle="${loc}" key="local.sentence.you_must_be_logged_in_to_purchase" var="you_must_be_logged_in_to_purchase"/>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon">
    <link href="/css/index.css" rel="stylesheet">
    <title>${main_title}</title>
</head>
<body>
<%@include file="../header.jsp"%>
<div class="page">
        <div class="card medicament">
            <h2>${med.name}</h2>
            <div><img src="images/medicaments/${med.image}" class="good"></div>
            <div class="container medicament" >
            <p class="title"> ${producer}: ${med.producer}</p>
                <p class="title"> ${price}: ${med.price} ${unit_of_price}</p>
                <c:choose>
                    <c:when test="${med.prescription}">
                        <p class="title"> ${released_by_prescription}.</p>
                    </c:when>
                    <c:when test="${!med.prescription}">
                        <p class="title"> ${released_without_prescription}.</p>
                    </c:when>
                </c:choose>
                <c:choose>
                    <c:when test="${med.availability}">
                        <p class="title"> ${is_available}.</p>
                    </c:when>
                    <c:when test="${!med.availability}">
                        <p class="title"> ${not_available}.</p>
                    </c:when>
                </c:choose>
                <p class="title"> ${mode_of_application}: ${med.modeOfApplication}</p>
                <p class="title"> ${contraindications}: ${med.contraindications}</p>
                <p class="title"> ${side_effects}: ${med.sideEffects}</p>
                <c:choose>
                    <c:when test="${roleUser==1}">
                        <c:choose>
                            <c:when test="${med.prescription}">
                                <form  method="POST" action="/add_to_cart.do?idMedicament=${med.id}" >
                                    <select name="idPrescription">
                                        <option value="" disabled selected>${select_a_recipe}</option>
                                        <c:forEach var="prescription" items="${prescriptions}">
                                            <option value=${prescription.idPrescription}>${dosa}: ${prescription.dosage}. ${number_of_packages}: ${prescription.number}</option>
                                        </c:forEach>
                                    </select>
                                    <input type="submit" class="button" value="${order}" />
                                </form>
                            </c:when>
                            <c:when test="${!med.prescription}">
                                <form  method="POST" action="/add_to_cart.do?idMedicament=${med.id}" >
                                    <c:choose>
                                        <c:when test="${dosages!=null}">
                                            <select name="idDosage">
                                                <option value="" disabled selected>${dosa}</option>
                                                <c:forEach var="dosage" items="${dosages}">
                                                    <option value=${dosage.id}>${dosage.dosage}</option>
                                                </c:forEach>
                                            </select>
                                        </c:when>
                                    </c:choose>
                                    <input type="number" name="number" id="number" placeholder="${number_of_packages}"/>
                                    <input type="submit" class="button" value="${order}" />
                                </form>
                            </c:when>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                         <p class="title"> ${you_must_be_logged_in_to_purchase}.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

</div>
<%@include file="../forms.jsp"%>
<%@include file="../footer.html"%>
</body>
<script src="/js/index.js"></script>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
</html>
