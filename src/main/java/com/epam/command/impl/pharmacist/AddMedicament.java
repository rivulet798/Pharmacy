package com.epam.command.impl.pharmacist;

import com.epam.command.Command;

import com.epam.command.impl.JspPageName;
import com.epam.service.DosageService;
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

public class AddMedicament implements Command {

    private static Logger logger = Logger.getLogger(AddMedicament.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private JspPageName jspPageName;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        jspPageName = JspPageName.INFORMATION;
        try {
            HttpSession session = request.getSession();
            String idRole = session.getAttribute(RequestEnum.USER_ROLE.getValue()).toString();
            if(idRole.equals(Constants.PHARMACIST)) {
                String csrfToken  = session.getAttribute(RequestEnum.CSRF_TOKEN.getValue()).toString();
                session.removeAttribute(RequestEnum.CSRF_TOKEN.getValue());
                String csrfTokenFromRequest = request.getParameter(RequestEnum.CSRF_TOKEN.getValue());
                if(csrfToken.equals(csrfTokenFromRequest)) {
                    MedicamentService medicamentService = serviceFactory.getMedicamentServiceImpl();
                    DosageService dosageService = serviceFactory.getDosageServiceImpl();
                    String name = request.getParameter(RequestEnum.NAME.getValue());
                    String producer = request.getParameter(RequestEnum.PRODUCER.getValue());
                    String price = request.getParameter(RequestEnum.PRICE.getValue());
                    String prescription = request.getParameter(RequestEnum.PRESCRIPTION.getValue());
                    Part part = request.getPart(RequestEnum.IMAGE.getValue());
                    String webInfPath = request.getServletContext().getRealPath("/");
                    String availability = request.getParameter(RequestEnum.AVAILABILITY.getValue());
                    String[] dosages = request.getParameterValues("dosage");
                    int idMedicament = medicamentService.addMedicament(name, producer, price, prescription, part, webInfPath, availability);
                    dosageService.addDosage(idMedicament, dosages);
                    request.setAttribute(RequestEnum.INFORMATION.getValue(), "Medicament is added");
                    logger.debug("\"" + name + "\" added medicament");
                } else{
                    jspPageName = JspPageName.INFORMATION;
                    request.setAttribute(RequestEnum.INFORMATION.getValue(),"Возможно ваш запрос является поддельным. Заполните форму заново или обратитесь к администратору.");
                }
            }
            else{
                request.setAttribute(RequestEnum.INFORMATION.getValue(), "Нет прав");
            }
        }catch (ServiceException | IOException | ServletException e){
            logger.error(e.getMessage());
            jspPageName = JspPageName.INFORMATION;
            request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
        }
        return jspPageName.getPath();
    }
}
