package com.epam.command.impl;

import com.epam.entity.Dosage;
import com.epam.service.DosageService;
import com.epam.service.exception.ServiceException;
import com.epam.service.factory.ServiceFactory;
import com.epam.servlet.RequestEnum;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetDosagesByMedicamentId implements com.epam.command.Command {
    private static Logger logger = Logger.getLogger(GetDosagesByMedicamentId.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private JspPageName jspPageName = JspPageName.ADD_PRESCRIPTION;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String idMedicament = request.getParameter(RequestEnum.ID_MEDICAMENT.getValue());
        try {
            DosageService dosageService = serviceFactory.getDosageServiceImpl();
            List<Dosage> dosages = dosageService.getDosagesByMedicamentId(idMedicament);
            logger.info(request.getHeader("Successfully getting dosagess by idMedicament"));
            request.setAttribute("dosages",dosages);
        }catch (ServiceException e){
            logger.error(e.getMessage());
            jspPageName = JspPageName.INFORMATION;
            request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
        }
        return jspPageName.getPath();
    }
}
