package com.epam.command.impl.doctor;

import com.epam.command.Command;
import com.epam.command.impl.JspPageName;
import com.epam.dto.RequestForRenewalDto;
import com.epam.service.RequestService;
import com.epam.service.exception.ServiceException;
import com.epam.service.factory.ServiceFactory;
import com.epam.service.utils.Constants;
import com.epam.servlet.RequestEnum;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetRequestsByDoctorId implements Command {
    private static Logger logger = Logger.getLogger(GetRequestsByDoctorId.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private JspPageName jspPageName;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        jspPageName = JspPageName.REQUESTS;
        try {
            HttpSession session = request.getSession();
            String idDoctor = session.getAttribute(RequestEnum.ID_USER.getValue()).toString();
            RequestService requestService = serviceFactory.getRequestServiceImpl();
            List<RequestForRenewalDto> requestForRenewalList = requestService.getRequestsDtoByDoctorId(idDoctor);
            logger.info("Successfully getting requests by doctor id");
            request.setAttribute("requestForRenewalList", requestForRenewalList);
        }catch (ServiceException e){
            logger.error(e.getMessage());
            jspPageName = JspPageName.INFORMATION;
            request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
        }
        return jspPageName.getPath();
    }
}

