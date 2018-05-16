<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean class="com.bsuir.entity.Medicament" scope="page" id="medicament"/>
<jsp:useBean class="com.bsuir.entity.User" scope="page" id="user"/>
<jsp:useBean class="com.bsuir.dto.OrderDto" scope="page" id="orderDto"/>
<fmt:setLocale scope="session" value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.locale" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.word.main_title" var="main_title"/>
<fmt:message bundle="${loc}" key="local.word.cart" var="cart"/>
<fmt:message bundle="${loc}" key="local.sentence.my_orders" var="my_orders"/>
<fmt:message bundle="${loc}" key="local.word.medicine" var="medicine"/>
<fmt:message bundle="${loc}" key="local.word.producer" var="producer"/>
<fmt:message bundle="${loc}" key="local.word.number" var="number"/>
<fmt:message bundle="${loc}" key="local.word.dosage" var="dosa"/>
<fmt:message bundle="${loc}" key="local.sentence.to_pay" var="to_pay"/>
<fmt:message bundle="${loc}" key="local.button.checkout" var="checkout"/>
<fmt:message bundle="${loc}" key="local.button.delete" var="delete"/>
<fmt:message bundle="${loc}" key="local.sentence.nothing_found" var="nothing_found"/>
<fmt:message bundle="${loc}" key="local.word.payment" var="payment"/>
<fmt:message bundle="${loc}" key="local.sentence.card_number" var="card_number"/>
<fmt:message bundle="${loc}" key="local.sentence.firstname_lastname" var="firstname_lastname"/>
<fmt:message bundle="${loc}" key="local.button.pay" var="pay"/>
<fmt:message bundle="${loc}" key="local.button.cancel" var="cancel"/>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
    <link href="css/index.css" rel="stylesheet">
    <link href="css/table.css" rel="stylesheet">
    <title>${main_title}</title>
    <style>
        @media only screen and (max-width: 981px) {
            td:nth-of-type(1):before {
                content: "${medicine}";
            }

            td:nth-of-type(2):before {
                content: "${producer}";
            }

            td:nth-of-type(3):before {
                content: "${number}";
            }

            td:nth-of-type(4):before {
                content: "${dosa}";
            }

            td:nth-of-type(5):before {
                content: "${to_pay}";
            }
        }
    </style>
</head>
<body>
<%@include file="../header.jsp" %>
<div class="page">
    <div class="filter">
        <a href="get_my_cart.do">${cart}</a> |
        <a href="get_my_orders.do?">${my_orders}</a>
    </div>
    <div class="card big">
        <table>
            <thead>
            <tr>
                <th>${medicine}</th>
                <th>${producer}</th>
                <th>${number}</th>
                <th>${dosa}</th>
                <th>${to_pay}</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:choose>
                <c:when test="${orders!=null}">
                    <c:forEach var="orderDto" items="${orders}">
                        <tr>
                            <td>${orderDto.medicamentName}</td>
                            <td>${orderDto.producer}</td>
                            <td>${orderDto.number}</td>
                            <td>${orderDto.dosage}</td>
                            <td>${orderDto.price}</td>
                            <c:choose>
                                <c:when test="${orderDto.orderStatus==1}">
                                    <td><a onclick="showPaymentPopUp(${orderDto.idOrder});"
                                           class="button">${checkout}</a></td>
                                    <td><a href="delete_from_cart.do?idOrder=${orderDto.idOrder}"
                                           class="button">${delete}</a></td>
                                </c:when>
                            </c:choose>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <h2>${nothing_found}</h2>
                </c:otherwise>
            </c:choose>
        </table>
    </div>
</div>
<%@include file="../footer.html" %>
<div id="payment" class="popup">
    <div class="card">
        <div class="signup">
            <form method="POST" id="payForm" class="material-form" action="">
                <h1>${payment}</h1>
                <input class="signup" type="number" name="cardNumber" placeholder="${card_number}"
                       pattern="/[0-9]{13,16}/" required autocomplete="off"/>
                <input class="signup" type="number" name="cvv" placeholder="CVV" pattern="/[0-9]{3}/" required
                       autocomplete="off"/>
                <input class="signup" type="text" name="name" placeholder="${firstname_lastname}" required
                       autocomplete="off"/>
                <input type="submit" name="Оплатить" value="${pay}" class="button1"/>
                <input type="button" name="Закрыть" value="${cancel}" onclick="hidePopUp();" class="button1"/>
            </form>
        </div>
    </div>
</div>
</body>
<script src="js/index.js"></script>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
</html>
