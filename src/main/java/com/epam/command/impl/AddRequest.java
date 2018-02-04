package com.epam.command.impl;

import com.epam.command.Command;
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

public class AddRequest implements Command {
    private static Logger logger = Logger.getLogger(AddRequest.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private JspPageName jspPageName = JspPageName.INFORMATION;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            RequestService requestService = serviceFactory.getRequestServiceImpl();
            String idPrescription = request.getParameter(RequestEnum.ID_PRESCRIPTION.getValue());
            if(checkRole(request) && isExpiredPrescription(request) && !checkExistOfRequest(request) ){
                requestService.addRequest(idPrescription, Constants.NEW_REQUEST);
                request.setAttribute(RequestEnum.INFORMATION.getValue(), "The request for a recipe extension " +
                        "was successfully sent. In the near future it will be reviewed by your doctor.");
             }
        }catch (ServiceException e){
            logger.error(e.getMessage());
            request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
        }
        return jspPageName.getPath();
    }

    private boolean checkRole(HttpServletRequest request){
        HttpSession session = request.getSession();
        String idRole = session.getAttribute(RequestEnum.USER_ROLE.getValue()).toString();
        if(idRole.equals(Constants.USER)){
            return true;
        } else{
            request.setAttribute(RequestEnum.INFORMATION.getValue(), "Нет прав");
            return false;
        }
    }

    private boolean checkExistOfRequest(HttpServletRequest request) throws ServiceException {
        String idPrescription = request.getParameter(RequestEnum.ID_PRESCRIPTION.getValue());
        RequestService requestService = serviceFactory.getRequestServiceImpl();
        if (requestService.checkExistOfRequest(idPrescription)) {
            request.setAttribute(RequestEnum.INFORMATION.getValue(), "You have already " +
                    "requested a renewal. In the near future it will be reviewed by your doctor.");
            return true;
        } else {
            return false;
        }
    }

    private boolean isExpiredPrescription(HttpServletRequest request) throws ServiceException{
        String idPrescription = request.getParameter(RequestEnum.ID_PRESCRIPTION.getValue());
        PrescriptionService prescriptionService = serviceFactory.getPrescriptionServiceImpl();
        if(prescriptionService.isExpiredPrescription(idPrescription)){
            return true;
        }else {
            request.setAttribute(RequestEnum.INFORMATION.getValue(), "Your electronic prescription has not expired");
            return false;
        }
    }

}
