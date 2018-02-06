package com.epam.command.impl.user;

import com.epam.command.Command;
import com.epam.command.impl.JspPageName;
import com.epam.dto.PrescriptionDto;
import com.epam.service.PrescriptionService;
import com.epam.service.exception.ServiceException;
import com.epam.service.factory.ServiceFactory;
import com.epam.service.utils.Constants;
import com.epam.servlet.RequestEnum;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetPrescriptionsByUserId implements Command {
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private static Logger logger = Logger.getLogger(GetPrescriptionsByUserId.class);
    private JspPageName jspPageName;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        jspPageName = JspPageName.PRESCRIPTIONS;
        try {
            HttpSession session = request.getSession();
            String idUser = session.getAttribute(RequestEnum.ID_USER.getValue()).toString();
            String idRole = session.getAttribute(RequestEnum.USER_ROLE.getValue()).toString();
            if(Constants.USER.equals(idRole)) {
                PrescriptionService prescriptionService = serviceFactory.getPrescriptionServiceImpl();
                List<PrescriptionDto> prescriptions = prescriptionService.getPrescriptionsDtoByUserId(idUser);
                logger.info("Successfully getting prescriptions by user id");
                request.setAttribute("prescriptions", prescriptions);
            }else{
                jspPageName = JspPageName.INFORMATION;
                request.setAttribute(RequestEnum.INFORMATION.getValue(), "Нет прав");
            }
        }catch (ServiceException e){
            logger.error(e.getMessage());
            jspPageName = JspPageName.INFORMATION;
            request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
        }
        return jspPageName.getPath();
    }
}
