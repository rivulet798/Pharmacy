package com.epam.command.impl;

public enum JspPageName {
    INDEX("pages/user/index.jsp"),
    MEDICAMENTS("/pages/pharmacist/index.jsp"),
    MEDICAMENT("/pages/common/medicament.jsp"),
    INFORMATION("/pages/common/information.jsp"),
    ADD_PRESCRIPTION("/pages/doctor/addPrescription.jsp"),
    USERS("/pages/admin/users.jsp"),
    PRESCRIPTIONS("/pages/user/prescriptions.jsp"),
    ORDERS("/pages/user/orders.jsp"),
    REQUESTS("/pages/doctor/requests.jsp"),
    PAYMENT("/pages/user/payment.jsp"),
    MEDICAMENTS_FOR_PHARMACIST("/pages/pharmacist/medicament.jsp");


    private String path;

    JspPageName(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
