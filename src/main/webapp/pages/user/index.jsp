<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale scope="session" value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.locale" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.word.main_title" var="main_title"/>
<fmt:message bundle="${loc}" key="local.sentence.nothing_found" var="nothing_found"/>
<fmt:message bundle="${loc}" key="local.button.view" var="view"/>
<fmt:message bundle="${loc}" key="local.word.unit_of_price" var="unit_of_price"/>

<fmt:message bundle="${loc}" key="local.sentence.asc_sort" var="asc_sort_by_price"/>
<fmt:message bundle="${loc}" key="local.sentence.desc_sort" var="desc_sort_by_price"/>
<jsp:useBean class="com.epam.entity.Medicament" scope="page" id="medicament" />
<jsp:useBean class="com.epam.entity.User" scope="page" id="user" />

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon">
    <link href="css/index.css" rel="stylesheet">
    <link href="css/form.css" rel="stylesheet">
    <title>${main_title}</title>
</head>
<body>
<%@include file="../header.jsp"%>
<div class="page">
    <c:choose>
        <c:when test="${ information}">
            <p class="information"> ${information}</p>
        </c:when>
    </c:choose>
    <div class="row">
        <div class="filter">
            <form method="POST" action="medicaments_by_producer.do" onsubmit="return checkSearchProducer()">
                <input onkeyup="checkSearchProducer()" type="text" name="producer" placeholder="${search}" id="searchProducer" autocomplete="off" required>
                <input style="color:#FFFFFF;padding: 5px 20px;background-color:rgba(98, 162, 183, 1);" type="submit" value="${search}">
            </form>
            <div class="right">
                <a href="medicaments_asc_sorted_by_price.do">▲ ${asc_sort_by_price}</a>
                <a href="medicaments_desc_sorted_by_price.do">▼ ${desc_sort_by_price}</a>
            </div>
        </div>

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
                                <p class="bottom-button"><a href="medicament.do?idMedicament=${medicament.id}"><button class="button">${view}</button></a></p>
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
<script src="js/index.js"></script>
<script src='https://www.google.com/recaptcha/api.js'></script>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
</body>
</html>

