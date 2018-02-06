package com.epam.command.impl.common;

import com.epam.command.Command;
import com.epam.command.impl.JspPageName;
import com.epam.entity.Medicament;
import com.epam.service.MedicamentService;
import com.epam.service.factory.ServiceFactory;
import com.epam.service.exception.ServiceException;
import com.epam.servlet.RequestEnum;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetAscSortedByPriceMedicaments implements Command {
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private static Logger logger = Logger.getLogger(GetAscSortedByPriceMedicaments.class);
    private JspPageName jspPageName;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        jspPageName = JspPageName.MEDICAMENTS;
        try {
            MedicamentService medicamentService = serviceFactory.getMedicamentServiceImpl();
            List<Medicament> medicaments = medicamentService.getAscSortedByPriceMedicaments();
            logger.info(request.getHeader("User-Agent") + " successfully getting asc sorted by price meds ");
            request.setAttribute("medicaments",medicaments);
        }catch (ServiceException e){
            logger.error(e.getMessage());
            jspPageName = JspPageName.INFORMATION;
            request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
        }
        return jspPageName.getPath();
    }
}