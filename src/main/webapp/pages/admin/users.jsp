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
    <fmt:setLocale scope="session" value="${sessionScope.locale}"/>
    <fmt:setBundle basename="localization.locale" scope="session" var="loc"/>
    <fmt:message bundle="${loc}" key="local.button.add_an_employee" var="add_an_employee"/>
    <fmt:message bundle="${loc}" key="local.word.employee" var="employee"/>
    <fmt:message bundle="${loc}" key="local.word.email" var="email"/>
    <fmt:message bundle="${loc}" key="local.word.address" var="address"/>
    <fmt:message bundle="${loc}" key="local.button.add" var="add"/>
    <fmt:message bundle="${loc}" key="local.button.cancel" var="cancel"/>
    <fmt:message bundle="${loc}" key="local.sentence.nothing_found" var="nothing_found"/>
    <fmt:message bundle="${loc}" key="local.sentence.adding_of_an_employee" var="adding_of_an_employee"/>
    <fmt:message bundle="${loc}" key="local.sentence.enter_name" var="enter_name"/>
    <fmt:message bundle="${loc}" key="local.sentence.enter_surname" var="enter_surname"/>
    <fmt:message bundle="${loc}" key="local.sentence.enter_address" var="enter_address"/>
    <fmt:message bundle="${loc}" key="local.sentence.enter_e-mail" var="enter_e-mail"/>
    <fmt:message bundle="${loc}" key="local.sentence.enter_login" var="enter_login"/>
    <fmt:message bundle="${loc}" key="local.sentence.enter_password" var="enter_password"/>
    <fmt:message bundle="${loc}" key="local.sentence.confirm_password" var="confirm_password"/>
    <fmt:message bundle="${loc}" key="local.sentence.hint_email" var="hint_email"/>
    <fmt:message bundle="${loc}" key="local.sentence.hint_login" var="hint_login"/>
    <fmt:message bundle="${loc}" key="local.sentence.hint_password" var="hint_password"/>



    <title>PHARMACY</title>
</head>

<body>
<%@include file="../header.jsp"%>
<div class="page">
    <button class="button" onclick="showPopUp();">${add_an_employee}</button>

    <div class="row">
        <c:choose>
            <c:when test="${users!=null}">
                <table>
                    <thead>
                        <tr>
                            <th>${employee}</th>
                            <th>${email}</th>
                            <th>${address}</th>
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
                <h2>${nothing_found}</h2>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%@include file="../forms.jsp"%>
<%@include file="../footer.html"%>
<div id="add-user" class="popup">
    <div class="card">
        <form name="Reviews" method="POST" id="form" action="/add_user.do" onsubmit="return valid(this)">
            <h2>${adding_of_an_employee}</h2>
            <input class="signup" type="text" name="name" placeholder="${enter_name}" id="name" />
            <input class="signup" type="text" name="surname" placeholder="${enter_surname}" id="surname" />
            <input class="signup" type="text" name="address" placeholder="${enter_address}" id="address" >
            <div class="tooltip">
                <input class="signup" type="text" name="email" placeholder="${enter_e-mail}" id="email" />
                <span class="tooltiptext">${hint_email}.</span>
            </div>
            <div class="tooltip">
                <input class="signup" type="text" name="login" placeholder="${enter_login}">
                <span class="tooltiptext">${hint_login}.</span>
            </div>
            <div class="tooltip">
                <input class="signup" type="password" name="password" placeholder="${enter_password}" id="password" >
                <span class="tooltiptext">${hint_password}.</span>
            </div>
            <input class="signup" type="password" name="rePassword" placeholder="${confirm_password}" id="repassword">
            <input class="signup" type="hidden" name="roleUser" id="roleUser" value="${newUserRole}">

            <input type="submit" name="Добавить" value="${add}" class="button1">
            <input type="button" name="Закрыть" value="${cancel}" onclick="hidePopUp();" class="button1">
        </form>
    </div>
</div>
</body>
</html>
