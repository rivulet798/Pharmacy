<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean class="com.epam.entity.Medicament" scope="page" id="medicament" />
<jsp:useBean class="com.epam.entity.User" scope="page" id="user" />
<jsp:useBean class="com.epam.dto.PrescriptionDto" scope="page" id="prescription" />

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
        <div class="card medicament">
            <h2>${med.name}</h2>
            <img src="images/medicaments/${med.image}" class="good">
            <div class="container" >
            <p class="title"> Производитель: ${med.producer}</p>
                <p class="title"> Цена: ${med.price} бел. руб.</p>
                <c:choose>
                    <c:when test="${med.prescription}">
                        <p class="title"> Отпускается только по рецепту.</p>
                    </c:when>
                    <c:when test="${!med.prescription}">
                        <p class="title"> Отпускается без рецепта.</p>
                    </c:when>
                </c:choose>
                <c:choose>
                    <c:when test="${med.availability}">
                        <p class="title"> Есть в наличии.</p>
                    </c:when>
                    <c:when test="${!med.availability}">
                        <p class="title"> Нет в наличии.</p>
                    </c:when>
                </c:choose>
                <p class="title"> Способ применения: ${med.modeOfApplication}</p>
                <p class="title"> Противопоказания: ${med.contraindications}</p>
                <p class="title"> Побочные эффекты: ${med.sideEffects}</p>
                <c:choose>
                    <c:when test="${idUser!=null}">
                        <c:choose>
                            <c:when test="${!med.prescription}">
                            <form  method="POST" action="/add_to_cart.do?idMedicament=${med.id}" >
                                    <c:when test="${dosages!=null}">
                                        <select name="idDosage">
                                            <option value="" disabled selected>Дозировка</option>
                                            <c:forEach var="dosage" items="${dosages}">
                                                <option value=${dosage.id}>${dosage.dosage}</option>
                                            </c:forEach>
                                        </select>
                                    </c:when>
                                    <input type="number" name="number" id="number" placeholder="Количество упаковок"/>
                                    <input type="submit" class="button" value="Заказать" />
                            </form>
                            </c:when>
                            <c:when test="${med.prescription}">
                                <form  method="POST" action="/add_to_cart.do?idMedicament=${med.id}" >
                                    <select name="idPrescription">
                                        <option value="" disabled selected>Выберите рецепт</option>
                                        <c:forEach var="prescription" items="${prescriptions}">
                                            <option value=${prescription.idPrescription}>Дозировка: ${prescription.dosage}. Количество: ${prescription.number}</option>
                                        </c:forEach>
                                    </select>
                                    <input type="submit" class="button" value="Заказать" />
                                </form>
                            </c:when>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                         <p class="title"> Для покупки необходимо выполнить вход.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

</div>
<%@include file="../forms.html"%>
<%@include file="../footer.html"%>
</body>
</html>
