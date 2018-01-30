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
    <fmt:message bundle="${loc}" key="local.word.error_title" var="error_title"/>
    <fmt:message bundle="${loc}" key="local.sentence.error_message" var="error_message"/>

    <title>${error_title}</title>
</head>

<body>
<%@include file="../header.jsp"%>
<div class="page">
    ${error_message}
</div>
</body>
<footer>Mariya Horuzhenko © 2018.</footer>
<%@include file="../footer.html"%>
<%@include file="../forms.jsp"%></body>
</html>
