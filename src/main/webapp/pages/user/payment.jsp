<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean class="com.epam.entity.Medicament" scope="page" id="medicament" />
<jsp:useBean class="com.epam.entity.User" scope="page" id="user" />
<fmt:setLocale scope="session" value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.locale" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.word.main_title" var="main_title"/>
<fmt:message bundle="${loc}" key="local.sentence.continue_browsing" var="continue_browsing"/>
<fmt:message bundle="${loc}" key="local.sentence.pay_an_order" var="pay_an_order"/>
<fmt:message bundle="${loc}" key="local.sentence.card_number" var="card_number"/>
<fmt:message bundle="${loc}" key="local.sentence.firstname_lastname" var="firstname_lastname"/>
<fmt:message bundle="${loc}" key="local.word.payment" var="payment"/>
<fmt:message bundle="${loc}" key="local.button.pay" var="pay"/>
<fmt:message bundle="${loc}" key="local.button.cancel" var="cancel"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon">
    <link href="/css/index.css" rel="stylesheet">
    <title>${main_title}</title>
</head>
<body>
<%@include file="../header.jsp"%>
<div class="page">
${information}
    <a href="/index.do" class="button">${continue_browsing}</a>
    <a onclick="showPopUp();" class="button">${pay_an_order}</a>
</div>
<%@include file="../footer.html"%>
<div id="payment" class="popup">
    <div class="card">
        <div class="signup">
            <form method="POST" id="form"  class="material-form" action="/pay.do?idOrder=${idOrder}">
                <h1>${payment}</h1>
                <input class="signup" type="number" name="cardNumber" placeholder="${card_number}" pattern="/[0-9]{13,16}/" required autocomplete="off"/>
                <input class="signup" type="number" name="cvv" placeholder="CVV" pattern="/[0-9]{3}/" required autocomplete="off"/>
                <input class="signup" type="text" name="name" placeholder="${firstname_lastname}" required autocomplete="off"/>
                <input type="submit" name="Оплатить" value="${pay}" class="button1">
                <input type="button" name="Закрыть" value="${cancel}" onclick="hidePopUp();" class="button1">
            </form>
        </div>
    </div>
</div>
</body>
<script src="/js/index.js"></script>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
</html>
