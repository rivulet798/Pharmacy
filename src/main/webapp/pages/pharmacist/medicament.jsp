<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean class="com.bsuir.entity.User" scope="page" id="user" />
<fmt:setLocale scope="session" value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.locale" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.word.main_title" var="main_title"/>
<fmt:message bundle="${loc}" key="local.sentence.editing_of_medicine" var="editing_of_medicine"/>
<fmt:message bundle="${loc}" key="local.word.name" var="name"/>
<fmt:message bundle="${loc}" key="local.word.image" var="image"/>
<fmt:message bundle="${loc}" key="local.word.producer" var="producer"/>
<fmt:message bundle="${loc}" key="local.word.price" var="price"/>
<fmt:message bundle="${loc}" key="local.word.unit_of_price" var="unit_of_price"/>
<fmt:message bundle="${loc}" key="local.sentence.leave_form" var="leave_form"/>
<fmt:message bundle="${loc}" key="local.sentence.released_without_prescription" var="released_without_prescription"/>
<fmt:message bundle="${loc}" key="local.sentence.released_by_prescription" var="released_by_prescription"/>
<fmt:message bundle="${loc}" key="local.word.availability" var="availability"/>
<fmt:message bundle="${loc}" key="local.sentence.is_available" var="is_available"/>
<fmt:message bundle="${loc}" key="local.sentence.not_available" var="not_available"/>
<fmt:message bundle="${loc}" key="local.word.mode_of_application" var="mode_of_application"/>
<fmt:message bundle="${loc}" key="local.word.contraindications" var="contraindications"/>
<fmt:message bundle="${loc}" key="local.word.side_effects" var="side_effects"/>
<fmt:message bundle="${loc}" key="local.button.ready" var="ready"/>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon">
    <link href="css/index.css" rel="stylesheet">
    <link href="css/form.css" rel="stylesheet">
    <title>${main_title}</title>
</head>
<body>
<%@include file="../header.jsp"%>
<div class="page">
        <div class="card medicament">
            <form action="edit_medicament.do?idMedicament=${medicament.id}" method="post" enctype="multipart/form-data" onsubmit="return validMedicament(this)">
                <h1>${editing_of_medicine}</h1>
                <img src="images/medicaments/${medicament.image}" class="good">
                <div class="container" >
                    <div class="input-block floating-field">
                        <label>${name}</label>
                        <input type="text" name="name" value="${medicament.name}" autocomplete="off" required/>
                    </div>
                    <div class="medImg">
                        <label>${image}</label>
                        <input type="file" accept=".png, .jpg, .jpeg" name="image" autocomplete="off" required/>
                        <label></label>
                    </div>
                    <div class="input-block floating-field">
                        <label>${producer}</label>
                        <input type="text" name="producer" value="${medicament.producer}" autocomplete="off" required/>
                    </div>
                    <div class="input-block floating-field">
                        <label>${price}</label>
                        <input type="number" step="0.01" min="0.5" name="price" value="${medicament.price}" required autocomplete="off"/> ${unit_of_price}
                    </div>
                    <div class="select-block">
                        <label>${leave_form}</label>
                        <select name="prescription">
                            <option value="" disabled selected>${leave_form}</option>
                            <c:choose>
                                <c:when test="${medicament.prescription}">
                                    <option value="false">${released_without_prescription}</option>
                                    <option selected value="true">${released_by_prescription}</option>
                                </c:when>
                                <c:when test="${!medicament.prescription}">
                                    <option selected value="false">${released_without_prescription}</option>
                                    <option value="true">${released_by_prescription}</option>
                                </c:when>
                            </c:choose>
                        </select>
                    </div>
                    <div class="select-block">
                        <label>${availability}</label>
                        <select name="availability">
                            <option value="" disabled selected>${availability}</option>
                            <c:choose>
                                <c:when test="${medicament.availability}">
                                    <option value="false">${not_available}</option>
                                    <option selected value="true">${is_available}</option>
                                </c:when>
                                <c:when test="${!medicament.availability}">
                                    <option selected value="false">${not_available}</option>
                                    <option value="true">${is_available}</option>
                                </c:when>
                            </c:choose>
                        </select>
                    </div>
                    <div class="input-block floating-field textarea">
                        <label>${mode_of_application}</label>
                        <textarea name="modeOfApplication" id="modeOfApplication" rows="3" autocomplete="off">${medicament.modeOfApplication}</textarea>
                    </div>
                    <div class="input-block floating-field textarea">
                        <label>${contraindications}</label>
                        <textarea name="contraindications" id="contraindications" rows="3" autocomplete="off">${medicament.contraindications}</textarea>
                    </div>
                    <div class="input-block floating-field textarea">
                        <label>${side_effects}</label>
                        <textarea name="sideEffects" id="sideEffects" rows="2" autocomplete="off">${medicament.sideEffects}</textarea>
                    </div>
                    <input type="submit" class="button" value="${ready}"/>
                </div>
            </form>
        </div>

</div>
<%@include file="../forms.jsp"%>
<%@include file="../footer.html"%>
</body>
<script src="js/index.js"></script>
</html>
