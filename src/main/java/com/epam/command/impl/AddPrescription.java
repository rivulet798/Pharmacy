package com.epam.command.impl;

import com.epam.command.Command;
import com.epam.service.PrescriptionService;
import com.epam.service.factory.ServiceFactory;
import com.epam.service.exception.ServiceException;
import com.epam.service.exception.ServiceLogicException;
import com.epam.service.utils.Constants;
import com.epam.servlet.RequestEnum;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddPrescription implements Command {
    private static Logger logger = Logger.getLogger(AddPrescription.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private JspPageName jspPageName = JspPageName.INFORMATION;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            String idRole = session.getAttribute(RequestEnum.USER_ROLE.getValue()).toString();
            if(idRole.equals(Constants.DOCTOR)) {
                String csrfToken  = session.getAttribute(RequestEnum.CSRF_TOKEN.getValue()).toString();
                session.removeAttribute(RequestEnum.CSRF_TOKEN.getValue());
                String csrfTokenFromRequest = request.getParameter(RequestEnum.CSRF_TOKEN.getValue());
                if(csrfToken.equals(csrfTokenFromRequest)) {
                    PrescriptionService prescriptionService = serviceFactory.getPrescriptionServiceImpl();
                    String idDoctor = session.getAttribute(RequestEnum.ID_USER.getValue()).toString();
                    String idUser = request.getParameter(RequestEnum.USER.getValue());
                    String idMedicament = request.getParameter(RequestEnum.MEDICAMENT.getValue());
                    String dateOfCompletion = request.getParameter(RequestEnum.DATE_OF_COMPLETION.getValue());
                    int idDosage = Integer.parseInt(request.getParameter(RequestEnum.ID_DOSAGE.getValue()));
                    int number = Integer.parseInt(request.getParameter(RequestEnum.NUMBER.getValue()));
                    prescriptionService.addPrescription(idDoctor, idUser, idMedicament, dateOfCompletion,idDosage, number);

                    request.setAttribute(RequestEnum.INFORMATION.getValue(), "Prescription is added");
                } else{
                    jspPageName = JspPageName.INFORMATION;
                    request.setAttribute(RequestEnum.INFORMATION.getValue(),"Возможно ваш запрос является поддельным. Заполните форму заново или обратитесь к администратору.");
                }
            } else{
                jspPageName = JspPageName.INFORMATION;
                request.setAttribute(RequestEnum.INFORMATION.getValue(), "Нет прав");
            }
        }catch (ServiceException | ServiceLogicException e){
            logger.error(e.getMessage());
            jspPageName = JspPageName.INFORMATION;
            request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
    }
        return jspPageName.getPath();
    }
}

