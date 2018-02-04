<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean class="com.epam.entity.Medicament" scope="page" id="medicament" />
<jsp:useBean class="com.epam.entity.User" scope="page" id="user" />
<fmt:setLocale scope="session" value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.locale" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.word.main_title" var="main_title"/>
<fmt:message bundle="${loc}" key="local.button.add_a_doctor" var="add_a_doctor"/>
<fmt:message bundle="${loc}" key="local.button.add_a_pharmacist" var="add_a_pharmacist"/>
<fmt:message bundle="${loc}" key="local.word.employee" var="employee"/>
<fmt:message bundle="${loc}" key="local.word.email" var="email"/>
<fmt:message bundle="${loc}" key="local.word.address" var="address"/>
<fmt:message bundle="${loc}" key="local.button.add" var="add"/>
<fmt:message bundle="${loc}" key="local.button.cancel" var="cancel"/>
<fmt:message bundle="${loc}" key="local.sentence.nothing_found" var="nothing_found"/>
<fmt:message bundle="${loc}" key="local.sentence.adding_of_a_doctor" var="adding_of_a_doctor"/>
<fmt:message bundle="${loc}" key="local.sentence.adding_of_a_pharmacist" var="adding_of_a_pharmacist"/>
<fmt:message bundle="${loc}" key="local.sentence.enter_name" var="enter_name"/>
<fmt:message bundle="${loc}" key="local.sentence.enter_surname" var="enter_surname"/>
<fmt:message bundle="${loc}" key="local.sentence.enter_address" var="enter_address"/>
<fmt:message bundle="${loc}" key="local.sentence.enter_email" var="enter_email"/>
<fmt:message bundle="${loc}" key="local.sentence.enter_login" var="enter_login"/>
<fmt:message bundle="${loc}" key="local.sentence.enter_password" var="enter_password"/>
<fmt:message bundle="${loc}" key="local.sentence.confirm_password" var="confirm_password"/>
<fmt:message bundle="${loc}" key="local.sentence.hint_email" var="hint_email"/>
<fmt:message bundle="${loc}" key="local.sentence.hint_login" var="hint_login"/>
<fmt:message bundle="${loc}" key="local.sentence.hint_password" var="hint_password"/>

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
    <c:choose>
        <c:when test="${newUserRole==2}">
            <button class="button" onclick="showPopUp();">${add_a_pharmacist}</button>
        </c:when>
        <c:otherwise>
            <button class="button" onclick="showPopUp();">${add_a_pharmacist}</button>
        </c:otherwise>
    </c:choose>


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
            <c:choose>
                <c:when test="${newUserRole==2}">
                    <h2>${adding_of_a_pharmacist}</h2>
                </c:when>
                <c:otherwise>
                    <h2>${adding_of_a_doctor}</h2>
                </c:otherwise>
            </c:choose>

            <input class="signup" type="text" name="name" placeholder="${enter_name}" id="name" />
            <input class="signup" type="text" name="surname" placeholder="${enter_surname}" id="surname" />
            <input class="signup" type="text" name="address" placeholder="${enter_address}" id="address" >
            <div class="tooltip">
                <input class="signup" type="text" name="email" placeholder="${enter_email}" id="email" />
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
<script src="/js/index.js"></script>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
</html>
