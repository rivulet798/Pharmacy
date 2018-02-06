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

public class GetMedicamentsByName implements Command {
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private static Logger logger = Logger.getLogger(GetMedicamentsByProducer.class);
    private JspPageName jspPageName;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        jspPageName = JspPageName.INDEX;
        String name = request.getParameter(RequestEnum.NAME.getValue());
        try {
            MedicamentService medicamentService = serviceFactory.getMedicamentServiceImpl();
            List<Medicament> medicaments = medicamentService.getMedicamentByName(name);
            logger.info(request.getHeader("Successfully getting medicaments by name"));
            request.setAttribute("medicaments",medicaments);
        }catch (ServiceException e){
            logger.error(e.getMessage());
            jspPageName = JspPageName.INFORMATION;
            request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
        }
        return jspPageName.getPath();
    }
}
