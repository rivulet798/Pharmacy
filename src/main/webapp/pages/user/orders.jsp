<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean class="com.epam.entity.Medicament" scope="page" id="medicament" />
<jsp:useBean class="com.epam.entity.User" scope="page" id="user" />
<jsp:useBean class="com.epam.dto.OrderDto" scope="page" id="orderDto" />

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon">    <style><%@include file="/css/index.css"%></style>
    <script><%@include file="/js/index.js"%></script>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <title>PHARMACY</title>
</head>
<body>
<%@include file="../header.html"%>
<div class="page">

    <div class="card">
        <a class="button" href="/get_my_cart.do">Корзина</a>
        <a class="button" href="/get_my_orders.do">Мои заказы</a>
        <table>
        <tr>
            <th>Лекарство</th>
            <th>Производитель</th>
            <th>Количество</th>
            <th>Дозировка</th>
            <th>К оплате</th>
            <th></th>
        </tr>
        <c:choose>
            <c:when test="${orders!=null}">
                <c:forEach var="orderDto" items="${orders}">
                    <tr>
                        <td>${orderDto.medicamentName}</td>
                        <td>${orderDto.producer}</td>
                        <td>${orderDto.number}</td>
                        <td>${orderDto.dosage}</td>
                        <td>${orderDto.price}</td>
                        <td><a onclick="showPopUp()${orderDto.idOrder};" class="button">Оформить заказ</a></td>
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
<%@include file="../footer.html"%>
<div id="payment" class="popup">
    <div class="card">
        <div class="signup">
            <form method="POST" id="form" action="/pay.do?idOrder=${orderDto.idOrder}">
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
