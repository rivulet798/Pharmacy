<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean class="com.epam.entity.Medicament" scope="page" id="medicament"/>
<jsp:useBean class="com.epam.entity.User" scope="page" id="user"/>
<fmt:setLocale scope="session" value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.locale" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.word.main_title" var="main_title"/>
<fmt:message bundle="${loc}" key="local.button.add_medication" var="add_medication"/>
<fmt:message bundle="${loc}" key="local.word.picture" var="picture"/>
<fmt:message bundle="${loc}" key="local.word.name" var="name"/>
<fmt:message bundle="${loc}" key="local.word.producer" var="producer"/>
<fmt:message bundle="${loc}" key="local.word.price" var="price"/>
<fmt:message bundle="${loc}" key="local.word.image" var="image"/>
<fmt:message bundle="${loc}" key="local.word.unit_of_price" var="unit_of_price"/>
<fmt:message bundle="${loc}" key="local.button.edit" var="edit"/>
<fmt:message bundle="${loc}" key="local.button.writing_the_recipe" var="writing_the_recipe"/>
<fmt:message bundle="${loc}" key="local.sentence.nothing_found" var="nothing_found"/>
<fmt:message bundle="${loc}" key="local.sentence.adding_of_medicine" var="adding_of_medicine"/>
<fmt:message bundle="${loc}" key="local.sentence.leave_form" var="leave_form"/>
<fmt:message bundle="${loc}" key="local.sentence.released_by_prescription" var="released_by_prescription"/>
<fmt:message bundle="${loc}" key="local.sentence.released_without_prescription" var="released_without_prescription"/>
<fmt:message bundle="${loc}" key="local.word.availability" var="availability"/>
<fmt:message bundle="${loc}" key="local.sentence.is_available" var="is_available"/>
<fmt:message bundle="${loc}" key="local.sentence.not_available" var="not_available"/>
<fmt:message bundle="${loc}" key="local.word.mode_of_application" var="mode_of_application"/>
<fmt:message bundle="${loc}" key="local.word.contraindications" var="contraindications"/>
<fmt:message bundle="${loc}" key="local.word.side_effects" var="side_effects"/>
<fmt:message bundle="${loc}" key="local.word.dosage" var="dosa"/>
<fmt:message bundle="${loc}" key="local.button.add_dosage" var="add_dosage"/>
<fmt:message bundle="${loc}" key="local.button.add" var="add"/>
<fmt:message bundle="${loc}" key="local.button.cancel" var="cancel"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
    <link href="css/index.css" rel="stylesheet">
    <link href="css/form.css" rel="stylesheet">
    <link href="css/table.css" rel="stylesheet">
    <title>${main_title}</title>
    <style>
        @media only screen and (max-width: 981px) {
            td:nth-of-type(1):before {
                content: "${picture}";
            }
            td:nth-of-type(2):before {
                 content: "${name}";
             }

            td:nth-of-type(3):before {
                content: "${producer}";
            }

            td:nth-of-type(4):before {
                content: "${price}";
            }
        }
    </style>
</head>
<body class="body">
<%@include file="../header.jsp" %>
<div class="page">
    <c:choose>
        <c:when test="${roleUser==2}">
            <button class="button" onclick="showPopUp()">${add_medication}</button>
        </c:when>
    </c:choose>
    <div class="card">
        <table id="dataTable">
            <thead>
            <tr>
                <th>${picture}</th>
                <th onclick="sortTable(1);">${name}</th>
                <th onclick="sortTable(2);">${producer}</th>
                <th onclick="sortTableByPrice(3);">${price}</th>
                <th></th>
            </tr>
            </thead>
            <c:choose>
                <c:when test="${medicaments!=null}">
                    <c:forEach var="medicament" items="${medicaments}">
                        <tr>
                            <td><img src="images/medicaments/${medicament.image}" alt="${medicament.name}"
                                     class="medImg"></td>
                            <td>${medicament.name}</td>
                            <td>${medicament.producer}</td>
                            <td price=${medicament.price}>${medicament.price} ${unit_of_price}</td>
                            <c:choose>
                                <c:when test="${roleUser==2}">
                                    <td><a href="get_and_edit_medicament.do?idMedicament=${medicament.id}"
                                           class="button">${edit}</a></td>
                                </c:when>
                                <c:when test="${roleUser==3}">
                                    <td><a href="new_prescription.do?idMedicament=${medicament.id}"
                                           class="button">${writing_the_recipe}</a></td>
                                </c:when>
                            </c:choose>
                            </td>
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
<div id="add-medicament" class="popup">
    <div class="card">
        <form method="POST" class="material-form" action="add_medicament.do"
              enctype="multipart/form-data" onsubmit="return validMedicament(this)">
            <h1>${adding_of_medicine}</h1>
            <div class="input-block floating-field">
                <label>${name}</label>
                <input type="text" name="name" placeholder="${name}" autocomplete="off" required>
            </div>
            <div class="input-block floating-field">
                <label>${producer}</label>
                <input type="text" name="producer" placeholder="${producer}" autocomplete="off" required>
            </div>
            <div class="input-block floating-field">
                <label>${price}</label>
                <input type="number" step="0.01" min="0.5" name="price" placeholder="${price}" autocomplete="off" required>
            </div>
            <div class="input-block">
                <label>${image}</label>
                <input type="file" accept=".png, .jpg, .jpeg" name="image" required>
            </div>
            <div class="select-block">
                <label>${leave_form}</label>
                <select name="prescription">
                    <option value="" disabled selected>${leave_form}</option>
                    <option value="false">${released_without_prescription}</option>
                    <option value="true">${released_by_prescription}</option>
                </select>
            </div>
            <div class="select-block">
                <label>${availability}</label>
                <select name="availability">
                    <option value="" disabled selected>${availability}</option>
                    <option value="false">${not_available}</option>
                    <option value="true">${is_available}</option>
                </select>
            </div>
            <div class="input-block floating-field textarea">
                <label>${mode_of_application}</label>
                <textarea name="modeOfApplication" rows="3" placeholder="${mode_of_application}"
                          autocomplete="off" required></textarea>
            </div>
            <div class="input-block floating-field textarea">
                <label>${contraindications}</label>
                <textarea name="contraindications" rows="3" placeholder="${contraindications}"
                          autocomplete="off" required></textarea>
            </div>
            <div class="input-block floating-field textarea">
                <label>${side_effects}</label>
                <textarea name="sideEffects" rows="2" placeholder="${side_effects}" autocomplete="off" required></textarea>
            </div>
            <div id="dosages">
                <input type="number" name="dosage" step="1" min="1" placeholder="${dosa}" autocomplete="off" required>
                <button onclick="addDosage()">${add_dosage}</button>
            </div>
            <input type="hidden" name="csrfToken" value="${csrfToken}"/>
            <input type="submit" value="${add}" class="button1"/>
            <input type="button" value="${cancel}" onclick="hidePopUp();" class="button1"/>
        </form>
    </div>
</div>
<script src="js/sort.js"></script>
<script src="js/index.js"></script>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
</body>
</html>
