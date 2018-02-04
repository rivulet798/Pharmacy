<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale scope="session" value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.locale" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.word.main_page" var="main_page"/>
<fmt:message bundle="${loc}" key="local.word.sign_in" var="sign_in"/>
<fmt:message bundle="${loc}" key="local.word.sign_up" var="sign_up"/>
<fmt:message bundle="${loc}" key="local.sentence.my_recipes" var="my_recipes"/>
<fmt:message bundle="${loc}" key="local.word.medicines" var="medicines"/>
<fmt:message bundle="${loc}" key="local.word.queries" var="queries"/>
<fmt:message bundle="${loc}" key="local.button.writing_the_recipe" var="writing_the_recipe"/>
<fmt:message bundle="${loc}" key="local.word.exit" var="exit"/>
<fmt:message bundle="${loc}" key="local.word.pharmacists" var="pharmacists"/>
<fmt:message bundle="${loc}" key="local.word.doctors" var="doctors"/>
<fmt:message bundle="${loc}" key="local.word.search" var="search"/>

<header>
    <nav>
        <ul id="nav">
            <a><li id="icon-nav" onclick="openMenu();"><img src="images/menu.png" class="icon"></li></a>
            <a href="index.do"><li>${main_page}</li></a>
            <c:choose>
                <c:when test="${roleUser==1}">
                    <a href="/get_my_prescriptions.do"><li>${my_recipes}</li></a>
                </c:when>
                <c:when test="${roleUser==2}">
                    <a href="/get_medicaments.do"><li>${medicines}</li></a>
                </c:when>
                <c:when test="${roleUser==3}">
                    <a href="/get_my_requests.do"><li>${queries}</li></a>
                    <a href="/new_prescription.do"><li>${writing_the_recipe}</li></a>
                </c:when>
                <c:when test="${roleUser==4}">
                    <a href="/get_users.do?usersRole=2"><li>${pharmacists}</li></a>
                    <a href="/get_users.do?usersRole=3"><li>${doctors}</li></a>
                </c:when>
            </c:choose>
            <li id="search">
                <form id="searchForm" method="POST" action="/medicaments_by_name.do" onsubmit="return checkSearch()">
                    <input onkeyup="checkSearch()"  type="text" name="name" placeholder="${search}" id="searchInput" required>
                </form>
            </li>
        </ul>

        <c:choose>
            <c:when test="${loginUser!=null}">
                <ul id="auth">
                    <c:choose>
                        <c:when test="${roleUser==1}">
                            <a href="/get_my_cart.do"><li>${loginUser}</li></a>
                        </c:when>
                        <c:otherwise>
                            <li>${loginUser}</li>
                        </c:otherwise>
                    </c:choose>
                    <a href="/sign_out.do"><li>${exit}</li></a>
                </ul>
            </c:when>
            <c:otherwise>
                <ul id="auth">
                    <a onclick="showSignUp();" id="registration"><li>${sign_up}</li></a>
                    <a onclick="showSignIn();"><li class="last">${sign_in}</li></a>
                </ul>
            </c:otherwise>
        </c:choose>
    </nav>
</header>