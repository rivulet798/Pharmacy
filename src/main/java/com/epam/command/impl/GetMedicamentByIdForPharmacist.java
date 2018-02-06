package com.epam.command.impl;

import com.epam.entity.Medicament;
import com.epam.service.MedicamentService;
import com.epam.service.exception.ServiceException;
import com.epam.service.factory.ServiceFactory;
import com.epam.servlet.RequestEnum;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GetMedicamentByIdForPharmacist implements com.epam.command.Command {
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private static Logger logger = Logger.getLogger(GetMedicamentByIdForPharmacist.class);
    private JspPageName jspPageName;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        jspPageName = JspPageName.MEDICAMENTS_FOR_PHARMACIST;
        try {
            String idMedicament = request.getParameter(RequestEnum.ID_MEDICAMENT.getValue());
            MedicamentService medicamentService = serviceFactory.getMedicamentServiceImpl();
            Medicament medicament = medicamentService.getMedicamentById(idMedicament);
            request.setAttribute(RequestEnum.MEDICAMENT.getValue(),medicament);
        }catch (ServiceException e){
            logger.error(e.getMessage());
            jspPageName = JspPageName.INFORMATION;
            request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
        }
        return jspPageName.getPath();
    }
}
