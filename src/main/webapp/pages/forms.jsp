<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale scope="session" value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization.locale" scope="session" var="loc"/>
<fmt:message bundle="${loc}" key="local.word.sign_up" var="sign_up"/>
<fmt:message bundle="${loc}" key="local.sentence.enter_login" var="enter_login"/>
<fmt:message bundle="${loc}" key="local.sentence.hint_login" var="hint_login"/>
<fmt:message bundle="${loc}" key="local.sentence.enter_password" var="enter_password"/>
<fmt:message bundle="${loc}" key="local.sentence.hint_password" var="hint_password"/>
<fmt:message bundle="${loc}" key="local.sentence.confirm_password" var="confirm_password"/>
<fmt:message bundle="${loc}" key="local.sentence.enter_name" var="enter_name"/>
<fmt:message bundle="${loc}" key="local.sentence.enter_surname" var="enter_surname"/>
<fmt:message bundle="${loc}" key="local.sentence.enter_email" var="enter_email"/>
<fmt:message bundle="${loc}" key="local.sentence.hint_email" var="hint_email"/>
<fmt:message bundle="${loc}" key="local.sentence.enter_address" var="enter_address"/>
<fmt:message bundle="${loc}" key="local.button.sign_up" var="button_sign_up"/>
<fmt:message bundle="${loc}" key="local.button.cancel" var="cancel"/>
<fmt:message bundle="${loc}" key="local.word.sign_in" var="sign_in"/>
<fmt:message bundle="${loc}" key="local.word.login" var="login"/>
<fmt:message bundle="${loc}" key="local.word.password" var="password"/>
<fmt:message bundle="${loc}" key="local.button.sign_in" var="button_sign_in"/>

<div id="signup" class="popup">
    <div class="card">
        <div class="signup">
            <form name="Reviews" method="POST" id="form" action="/sign_up.do" onsubmit="return valid(this)">
                <h2>${sign_up}</h2>
                <div class="tooltip">
                    <input class="signup" type="text" name="login" placeholder="${enter_login}" autocomplete="off">
                    <span class="tooltiptext">${hint_login}</span>
                </div>
                <div class="tooltip">
                    <input class="signup" type="password" name="password" placeholder="${enter_password}" autocomplete="off" >
                    <span class="tooltiptext">${hint_password}.</span>
                </div>
                <input class="signup" type="password" name="rePassword" placeholder="${confirm_password}" autocomplete="off">
                <input class="signup" type="text" name="name" placeholder="${enter_name}" />
                <input class="signup" type="text" name="surname" placeholder="${enter_surname}" />
                <div class="tooltip">
                    <input class="signup" type="text" name="email" placeholder="${enter_email}" autocomplete="off" />
                    <span class="tooltiptext">${hint_email}.</span>
                </div>
                <input class="signup" type="text" name="address" placeholder="${enter_address}" >
                <input type="submit" value="${button_sign_up}" class="button1">
                <input type="button" value="${cancel}" onclick="hideSignUp();" class="button1">
            </form>
        </div>
    </div>
</div>
<div id="login" class="popup">
    <div class="card">
        <div class="signup">
            <form name="Reviews" method="POST" id="form" action="/sign_in.do" onsubmit="return valid(this)">
                <h2>${sign_in}</h2>
                <input class="signup" type="text" name="loglogin" placeholder="${login}" autocomplete="off" />
                <input class="signup" type="password" name="logpassword" placeholder="${password}" autocomplete="off">
                <input type="submit" value="${button_sign_in}" class="button1">
                <input type="button" value="${cancel}" onclick="hideSignIn();" class="button1">
            </form>
        </div>
    </div>
</div>