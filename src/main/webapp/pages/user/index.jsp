<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale scope="session" value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.locale" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.word.title" var="title"/>
<fmt:message bundle="${loc}" key="local.sentence.nothing_found" var="nothing_found"/>
<fmt:message bundle="${loc}" key="local.button.view" var="view"/>
<fmt:message bundle="${loc}" key="local.word.unit_of_price" var="unit_of_price"/>
<jsp:useBean class="com.epam.entity.Medicament" scope="page" id="medicament" />
<jsp:useBean class="com.epam.entity.User" scope="page" id="user" />

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon">
    <link href="/css/index.css" rel="stylesheet">
    <title>${title}</title>
</head>
<body>
<%@include file="../header.jsp"%>
<div class="page">
    <div class="row">
        <c:choose>
            <c:when test="${medicaments!=null}">
                <c:forEach var="medicament" items="${medicaments}">
                    <div class="column">
                        <div class="card">
                            <img src="images/medicaments/${medicament.image}" alt="${medicament.name}" style="width:100%">
                            <div class="container">
                                <h2>${medicament.name}</h2>
                                <p class="title">${medicament.producer}</p>
                                <p class="title">${medicament.price} ${unit_of_price}</p>
                                <p class="bottom-button"><a href="/medicament.do?idMedicament=${medicament.id}"><button class="button">${view}</button></a></p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <h2>${nothing_found}</h2>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%@include file="../forms.jsp"%>
<%@include file="../footer.html"%>
</body>
<%@include file="../scripts.jsp"%>
</html>
