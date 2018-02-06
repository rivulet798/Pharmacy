package com.epam.command.impl.doctor;

import com.epam.command.impl.JspPageName;
import com.epam.service.PrescriptionService;
import com.epam.service.RequestService;
import com.epam.service.exception.ServiceException;
import com.epam.service.factory.ServiceFactory;
import com.epam.service.utils.Constants;
import com.epam.servlet.RequestEnum;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExtendRequest implements com.epam.command.Command {
    private static Logger logger = Logger.getLogger(ExtendRequest.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private JspPageName jspPageName;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        jspPageName = JspPageName.INFORMATION;
        try {
            HttpSession session = request.getSession();
            String idRole = session.getAttribute(RequestEnum.USER_ROLE.getValue()).toString();
            String idDoctor = session.getAttribute(RequestEnum.ID_USER.getValue()).toString();
            if(Constants.DOCTOR.equals(idRole)) {
                RequestService requestService = serviceFactory.getRequestServiceImpl();
                PrescriptionService prescriptionService = serviceFactory.getPrescriptionServiceImpl();
                String idRequest = request.getParameter(RequestEnum.ID_REQUEST.getValue());
                prescriptionService.extendPrescription(idRequest, idDoctor);
                requestService.changeRequestStatus(idRequest, Constants.ACCEPTED_REQUEST);
                request.setAttribute(RequestEnum.INFORMATION.getValue(), "Запрос на продление электронного рецепта принят");
            }else{
                request.setAttribute(RequestEnum.INFORMATION.getValue(), "Нет прав");
            }
        }catch (ServiceException e){
            logger.error(e.getMessage());
            request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
        }
        return jspPageName.getPath();
    }
}
