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
    <button class="button" onclick="showPopUp();">Дoбавить работника</button>

    <div class="row">
        <c:choose>
            <c:when test="${users!=null}">
                <table>
                    <thead>
                        <tr>
                            <th>Работник</th>
                            <th>Электронная почта</th>
                            <th>Адрес</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${users}">
                            <tr>
                                <td>${user.name} ${user.surname}</td>
                                <td>${user.email}</td>
                                <td>${user.address}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <h2>Ничего не найдено</h2>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%@include file="../forms.html"%>
<%@include file="../footer.html"%>
<div id="add-user" class="popup">
    <div class="card">
        <form name="Reviews" method="POST" id="form" action="/add_user.do" onsubmit="return valid(this)">
            <h2>Добавление работника</h2>
            <input class="signup" type="text" name="name" placeholder="Введите имя" id="name" />
            <input class="signup" type="text" name="surname" placeholder="Введите фамилию" id="surname" />
            <input class="signup" type="text" name="address" placeholder="Введите адрес" id="address" >
            <div class="tooltip">
                <input class="signup" type="text" name="email" placeholder="Введите e-mail" id="email" />
                <span class="tooltiptext">Введите e-mail в виде name@mail.ru.</span>
            </div>
            <div class="tooltip">
                <input class="signup" type="text" name="login" placeholder="Введите логин">
                <span class="tooltiptext">Разрешено использовать латинские
                            буквы, цифры и знак "_".
                            Первый символ латинская буква.
                            Длина логина не менее 5 символов.</span>
            </div>
            <div class="tooltip">
                <input class="signup" type="password" name="password" placeholder="Введите пароль" id="password" >
                <span class="tooltiptext">Пароль должен содержать не менее
                            6 символов.Не менее одной буквы в каждом
                            регистре и не менее одной цифры.</span>
            </div>
            <input class="signup" type="password" name="rePassword" placeholder="Подтвердите пароль" id="repassword">
            <input class="signup" type="hidden" name="roleUser" id="roleUser" value="${newUserRole}">

            <input type="submit" name="Добавить" value="Добавить" class="button1">
            <input type="button" name="Закрыть" value="Закрыть" onclick="hidePopUp();" class="button1">
        </form>
    </div>
</div>
</body>
</html>
