<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
        <div class="card medicament">
            <form action="edit_medicament.do?idMedicament=${medicament.id}" method="post" enctype="multipart/form-data">
                <h2>Название: <input type="text" name="name" value="${medicament.name}"/></h2>
                <img src="images/medicaments/${medicament.image}" class="good">
                <div class="container" >
                <p class="title">Изображение: <input type="file" name="image"/></p>
                <p class="title"> Производитель:<input type="text" name="producer" value="${medicament.producer}"/></p>
                    <p class="title"> Цена: <input type="text" name="price" value="${medicament.price}"/> бел. руб.</p>
                    <p><select name="prescription">
                        <option value="" disabled selected>Форма отпуска</option>
                    <c:choose>
                        <c:when test="${medicament.prescription}">
                            <option value="false">Без рецепта</option>
                            <option selected value="true">По рецепту</option>
                        </c:when>
                        <c:when test="${!medicament.prescription}">
                            <option selected value="false">Без рецепта</option>
                            <option value="true">По рецепту</option>
                        </c:when>
                    </c:choose>
                    </select>
                    <select name="availability">
                        <option value="" disabled selected>Наличие</option>
                    <c:choose>
                        <c:when test="${medicament.availability}">
                            <option value="false">Нет в наличии</option>
                            <option selected value="true">Есть в наличии</option>
                        </c:when>
                        <c:when test="${!medicament.availability}">
                            <option selected value="false">Нет в наличии</option>
                            <option value="true">Есть в наличии</option>
                        </c:when>
                    </c:choose>
                    </select>
                    <input type="submit" class="button" value="Готово"/>
                </div>
            </form>
        </div>

</div>
<%@include file="../forms.html"%>
<%@include file="../footer.html"%>
</body>
</html>
