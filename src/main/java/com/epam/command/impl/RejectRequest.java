package com.epam.command.impl;

import com.epam.service.RequestService;
import com.epam.service.exception.ServiceException;
import com.epam.service.factory.ServiceFactory;
import com.epam.service.utils.Constants;
import com.epam.servlet.RequestEnum;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RejectRequest implements com.epam.command.Command {
    private static Logger logger = Logger.getLogger(RejectRequest.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private JspPageName jspPageName = JspPageName.INFORMATION;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            String idRole = session.getAttribute(RequestEnum.USER_ROLE.getValue()).toString();
            if(Constants.DOCTOR.equals(idRole)) {
                RequestService requestService = serviceFactory.getRequestServiceImpl();
                String idRequest = request.getParameter(RequestEnum.ID_REQUEST.getValue());
                requestService.changeRequestStatus(idRequest, Constants.REJECTED_REQUEST);
                request.setAttribute(RequestEnum.INFORMATION.getValue(), "Запрос на продление электронного рецепта отклонен");
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
