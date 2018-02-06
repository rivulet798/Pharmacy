package com.epam.command.impl;

import com.epam.command.impl.common.GetMedicamentsByProducer;
import com.epam.entity.Medicament;
import com.epam.service.MedicamentService;
import com.epam.service.exception.ServiceException;
import com.epam.service.factory.ServiceFactory;
import com.epam.service.utils.Constants;
import com.epam.servlet.RequestEnum;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetMedicamentsWithPrescription implements com.epam.command.Command {
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private static Logger logger = Logger.getLogger(GetMedicamentsByProducer.class);
    private JspPageName jspPageName;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        jspPageName = JspPageName.MEDICAMENTS;
        try {
            HttpSession session = request.getSession();
            String idRole = session.getAttribute(RequestEnum.USER_ROLE.getValue()).toString();
            if(Constants.DOCTOR.equals(idRole)) {
                MedicamentService medicamentService = serviceFactory.getMedicamentServiceImpl();
                List<Medicament> medicaments = medicamentService.getMedicamentsWithPrescription();
                request.setAttribute("medicaments", medicaments);
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
