package com.epam.command.impl;

import com.epam.command.Command;
import com.epam.entity.Medicament;
import com.epam.service.MedicamentService;
import com.epam.service.factory.ServiceFactory;
import com.epam.service.exception.ServiceException;
import com.epam.servlet.RequestEnum;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetDescSortedByPriceMedicaments implements Command {
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private static Logger logger = Logger.getLogger(GetDescSortedByPriceMedicaments.class);
    private JspPageName jspPageName = JspPageName.MEDICAMENTS;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            MedicamentService medicamentService = serviceFactory.getMedicamentServiceImpl();
            List<Medicament> medicaments = medicamentService.getDescSortedByPriceMedicaments();
            logger.info(request.getHeader("User-Agent") + " successfully getting desc sorted by price meds ");
            request.setAttribute("medicaments",medicaments);
        }catch (ServiceException  e){
            logger.error(e.getMessage());
            jspPageName = JspPageName.INFORMATION;
            request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
        }
        return jspPageName.getPath();
    }
}