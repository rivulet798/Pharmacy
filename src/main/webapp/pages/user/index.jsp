<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean class="com.epam.entity.Medicament" scope="page" id="medicament" />
<jsp:useBean class="com.epam.entity.User" scope="page" id="user" />
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
                                <p>${medicament.price}</p>
                                <p class="bottom-button"><a href="/medicament.do?idMedicament=${medicament.id}"><button class="button">Просмотреть</button></a></p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <h2>Ничего не найдено</h2>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%@include file="../forms.html"%>
<%@include file="../footer.html"%>
</body>
</html>
