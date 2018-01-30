<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean class="com.epam.entity.User" scope="page" id="user" />

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon">
    <link href="/css/index.css" rel="stylesheet">
    <fmt:setLocale scope="session" value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localization.locale" scope="session" var="loc"/>
    <fmt:message bundle="${loc}" key="local.word.name" var="name"/>
    <fmt:message bundle="${loc}" key="local.word.image" var="image"/>
    <fmt:message bundle="${loc}" key="local.word.producer" var="producer"/>
    <fmt:message bundle="${loc}" key="local.word.price" var="price"/>
    <fmt:message bundle="${loc}" key="local.word.unit_of_price" var="unit_of_price"/>
    <fmt:message bundle="${loc}" key="local.sentence.leave_form" var="leave_form"/>
    <fmt:message bundle="${loc}" key="local.sentence.released_without_prescription" var="released_without_prescription"/>
    <fmt:message bundle="${loc}" key="local.sentence.released_by_prescription" var="released_by_prescription"/>
    <fmt:message bundle="${loc}" key="local.word.availability" var="availability"/>
    <fmt:message bundle="${loc}" key="local.sentence.is_available" var="is_available"/>
    <fmt:message bundle="${loc}" key="local.sentence.not_available" var="not_available"/>
    <fmt:message bundle="${loc}" key="local.button.ready" var="ready"/>



    <title>PHARMACY</title>
</head>
<body>
<%@include file="../header.jsp"%>
<div class="page">
        <div class="card medicament">
            <form action="edit_medicament.do?idMedicament=${medicament.id}" method="post" enctype="multipart/form-data">
                <h2>${name}: <input type="text" name="name" value="${medicament.name}"/></h2>
                <img src="images/medicaments/${medicament.image}" class="good">
                <div class="container" >
                <p class="title">${image}: <input type="file" name="image"/></p>
                <p class="title"> ${producer}:<input type="text" name="producer" value="${medicament.producer}"/></p>
                    <p class="title"> ${price}: <input type="text" name="price" value="${medicament.price}"/> ${unit_of_price}</p>
                    <p><select name="prescription">
                        <option value="" disabled selected>${leave_form}</option>
                    <c:choose>
                        <c:when test="${medicament.prescription}">
                            <option value="false">${released_without_prescription}</option>
                            <option selected value="true">${released_by_prescription}</option>
                        </c:when>
                        <c:when test="${!medicament.prescription}">
                            <option selected value="false">${released_without_prescription}</option>
                            <option value="true">${released_by_prescription}</option>
                        </c:when>
                    </c:choose>
                    </select>
                    <select name="availability">
                        <option value="" disabled selected>${availability}</option>
                    <c:choose>
                        <c:when test="${medicament.availability}">
                            <option value="false">${not_available}</option>
                            <option selected value="true">${is_available}</option>
                        </c:when>
                        <c:when test="${!medicament.availability}">
                            <option selected value="false">${not_available}</option>
                            <option value="true">${is_available}</option>
                        </c:when>
                    </c:choose>
                    </select>
                    <input type="submit" class="button" value="${ready}"/>
                </div>
            </form>
        </div>

</div>
<%@include file="../forms.jsp"%>
<%@include file="../footer.html"%>
</body>
<%@include file="../scripts.jsp"%>
</html>
