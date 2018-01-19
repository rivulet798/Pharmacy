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
        <script><%@include file="/js/sort.js"%></script>
        <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <title>PHARMACY</title>
    </head>
    <body>
    <%@include file="../header.html"%>
    <div class="page">
    <div class="card">
        <button class="button" onclick="showPopUp()">Дoбавить медикамент</button>

        <table id="dataTable">
        <tr>
        <th>Изображение</th>
        <th onclick="sortTable(2);">Название</th>
        <th onclick="sortTable(3);">Производитель</th>
        <th onclick="sortTable(4);">Цена</th>
        <th onclick="sortTable(5);"></th>
        </tr>
            <c:choose>
                <c:when test="${medicaments!=null}">
                    <c:forEach var="medicament" items="${medicaments}">
                        <tr>
                            <td><img src="images/medicaments/${medicament.image}" alt="${medicament.name}" class="medImg"></td>
                            <td>${medicament.name}</td>
                            <td>${medicament.producer}</td>
                            <td>${medicament.price} бел. руб.</td>
                            <td><a href="/get_and_edit_medicament.do?idMedicament=${medicament.id}" class="button">Редактировать</a></td>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <h2>Ничего не найдено</h2>
                </c:otherwise>
            </c:choose>
        </table>
    </div>
    </div>

    <%@include file="../forms.html"%>
    <%@include file="../footer.html"%>

    <div id="add-medicament" class="popup">
        <div class="card">
            <form name="Reviews" method="POST" id="form" action="/add_medicament.do" enctype="multipart/form-data" onsubmit="return validMedicament(this)">
                <h2>Добавление медикамента</h2>
                <input type="text" name="name" id="name" placeholder="Название">
                <input type="text" name="producer" id="producer" placeholder="Производитель">
                <input type="text" name="price" id="price" placeholder="Цена">
                <select name="prescription">
                    <option value="" disabled selected>Форма отпуска</option>
                    <option value="false">Без рецепта</option>
                    <option value="true">По рецепту</option>
                </select>
                <input type="file" name="image" id="image">
                <select name="availability">
                    <option value="" disabled selected>Наличие</option>
                    <option value="false">Нет в наличии</option>
                    <option value="true">Есть в наличии</option>
                </select>
                <input type="hidden" name="csrfToken" value="${csrfToken}">
                <input type="submit" name="Добавить" value="Добавить" class="button1">
                <input type="button" name="Закрыть" value="Закрыть" onclick="hidePopUp();" class="button1">
            </form>
        </div>
    </body>
</html>
