<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean class="com.epam.entity.Medicament" scope="page" id="medicament" />
<jsp:useBean class="com.epam.entity.User" scope="page" id="user" />
<fmt:setLocale scope="session" value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.locale" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.button.writing_the_recipe" var="writing_the_recipe"/>
<html>
    <head>
        <title>Выписать рецепт</title>
    </head>
    <body>
        <form action="add_prescription.do" method="post">
            <select name="medicament">
                <c:forEach var="medicament" items="${medicaments}">
                    <option value="${medicament.id}">${medicament.name}</option>
                </c:forEach>
            </select>
            <select name="user">
                <c:forEach var="user" items="${users}">
                    <option value="${user.id}">${user.name} ${user.surname}</option>
                </c:forEach>
            </select>
            <input type="number" name="idDosage">
            <input type="number" name="number">
            <input type="date" name="dateOfCompletion">
            <input type="hidden" name="csrfToken" value="${csrfToken}">
            <input type="submit" value="${writing_the_recipe}">
        </form>
    </body>
</html>
