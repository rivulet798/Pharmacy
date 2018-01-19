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
${information}
    <a href="/index.do" class="button">Продолжить просмотр</a>
    <a onclick="showPopUp();" class="button">Оплатить заказ</a>
</div>
<%@include file="../footer.html"%>
<div id="payment" class="popup">
    <div class="card">
        <div class="signup">
            <form method="POST" id="form" action="/pay.do?idOrder=${idOrder}">
                <h2>Оплата</h2>
                <input class="signup" type="number" name="cardnumber" placeholder="Номер карты" />
                <input class="signup" type="number" name="cvv" placeholder="CVV">
                <input class="signup" type="text" name="name" placeholder="Имя Фамилия">
                <input type="submit" name="Войти" value="Оплатить" class="button1">
                <input type="button" name="Закрыть" value="Закрыть" onclick="hidePopUp();" class="button1">
            </form>
        </div>
    </div>
</div>
</body>
</html>
