package com.epam.servlet;

public enum RequestEnum {
    USER_LOGIN("loginUser"),
    USER_ROLE("roleUser"),
    ID_USER("idUser"),

    INFORMATION("information"),
    MEDICAMENTS("medicaments"),
    DOSAGES("dosages"),

    MEDICAMENT("medicament"),
    LOGLOGIN("loglogin"),
    LOGPASSWORD("logpassword"),
    LOGIN("login"),
    PASSWORD("password"),
    SURNAME("surname"),
    NAME("name"),
    ADDRESS("address"),
    EMAIL("email"),

    PRODUCER("producer"),
    PRICE("price"),
    PRESCRIPTION("prescription"),
    IMAGE("image"),
    AVAILABILITY("availability"),
    MODE_OF_APPLICATION("modeOfApplication"),
    CONTRAINDICATIONS("contraindications"),
    SIDE_EFFECTS("sideEffects"),
    J_SESSION_ID("jsessionid"),

    USER("user"),
    DATE_OF_COMPLETION("dateOfCompletion"),
    USERS("users"),
    USERS_ROLE("usersRole"),
    NUMBER("number"),
    ID_DOSAGE("idDosage"),
    ID_PRESCRIPTION("idPrescription"),
    ID_ORDER("idOrder"),
    ID_MEDICAMENT("idMedicament"),
    ID_REQUEST("idRequest"),

    CSRF_TOKEN("csrfToken"),
    LOCALE("locale");

    private String value;

    RequestEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

