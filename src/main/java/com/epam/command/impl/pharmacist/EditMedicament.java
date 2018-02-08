package com.epam.command.impl.pharmacist;

import com.epam.command.Command;
import com.epam.command.impl.JspPageName;
import com.epam.service.MedicamentService;
import com.epam.service.factory.ServiceFactory;
import com.epam.service.exception.ServiceException;
import com.epam.service.utils.Constants;
import com.epam.servlet.RequestEnum;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;

public class EditMedicament implements Command {
    private static Logger logger = Logger.getLogger(EditMedicament.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private JspPageName jspPageName;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        jspPageName = JspPageName.INDEX;
        MedicamentService medicamentService = serviceFactory.getMedicamentServiceImpl();
        try {
            String idMedicament = request.getParameter(RequestEnum.ID_MEDICAMENT.getValue());
            String name = request.getParameter(RequestEnum.NAME.getValue());
            String producer = request.getParameter(RequestEnum.PRODUCER.getValue());
            String price = request.getParameter(RequestEnum.PRICE.getValue());
            String prescription = request.getParameter(RequestEnum.PRESCRIPTION.getValue());
            Part part = request.getPart(RequestEnum.IMAGE.getValue());
            String webInfPath = request.getServletContext().getRealPath("/");
            String availability = request.getParameter(RequestEnum.AVAILABILITY.getValue());
            String modeOfApplication = request.getParameter(RequestEnum.MODE_OF_APPLICATION.getValue());
            String contraindications = request.getParameter(RequestEnum.CONTRAINDICATIONS.getValue());
            String sideEffects = request.getParameter(RequestEnum.SIDE_EFFECTS.getValue());

            medicamentService.editMedicament(idMedicament, name, producer, price,
                    prescription, part, webInfPath, availability, modeOfApplication,
                    contraindications, sideEffects);

            jspPageName = JspPageName.INFORMATION;
            request.setAttribute(RequestEnum.INFORMATION.getValue(), "Medicament is changed");
        }catch (ServiceException | IOException | ServletException e){
            logger.error(e.getMessage());
            jspPageName = JspPageName.INFORMATION;
            request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
        }
        return jspPageName.getPath();
    }
}
