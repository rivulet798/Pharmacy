package com.epam.command.impl.doctor;

import com.epam.command.Command;
import com.epam.command.impl.JspPageName;
import com.epam.command.impl.pharmacist.AddMedicament;
import com.epam.entity.Dosage;
import com.epam.entity.Medicament;
import com.epam.entity.User;
import com.epam.service.DosageService;
import com.epam.service.MedicamentService;
import com.epam.service.UserService;
import com.epam.service.exception.ServiceException;
import com.epam.service.factory.ServiceFactory;
import com.epam.service.utils.Constants;
import com.epam.service.utils.Hasher;
import com.epam.servlet.RequestEnum;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.List;

public class NewPrescription implements Command {
    private static Logger logger = Logger.getLogger(AddMedicament.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private JspPageName jspPageName;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        jspPageName = JspPageName.ADD_PRESCRIPTION;
        HttpSession session = request.getSession();
        if(checkRole(request)){
            MedicamentService medicamentService = serviceFactory.getMedicamentServiceImpl();
            UserService userService = serviceFactory.getUserServiceImpl();
            DosageService dosageService = serviceFactory.getDosageServiceImpl();
            try {
                SecureRandom secureRandom = new SecureRandom();
                String csrfToken = String.valueOf(secureRandom.nextLong());
                csrfToken = Hasher.hashBySha1(csrfToken);
                String idMedicament = request.getParameter(RequestEnum.ID_MEDICAMENT.getValue());
                Medicament medicament = medicamentService.getMedicamentById(idMedicament);
                if(medicament.isPrescription()) {
                    List<User> users = userService.getAllUsersByRoleId(Constants.USER);
                    List<Dosage> dosages = dosageService.getDosagesByMedicamentId(idMedicament);
                    request.setAttribute(RequestEnum.ID_MEDICAMENT.getValue(),idMedicament);
                    request.setAttribute(RequestEnum.USERS.getValue(), users);
                    request.setAttribute(RequestEnum.DOSAGES.getValue(), dosages);
                    session.setAttribute(RequestEnum.CSRF_TOKEN.getValue(), csrfToken);
                }else{
                    jspPageName = JspPageName.INFORMATION;
                    request.setAttribute(RequestEnum.INFORMATION.getValue(),"Данный медикамент не нуждается в электронном рецепте");
                }
            }catch (ServiceException e){
                logger.error(e.getMessage());
                jspPageName = JspPageName.INFORMATION;
                request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
            }
        }
        return jspPageName.getPath();
    }

    private boolean checkRole(HttpServletRequest request){
        HttpSession session = request.getSession();
        String userRole = session.getAttribute(RequestEnum.USER_ROLE.getValue()).toString();
        if(userRole.equals(Constants.DOCTOR)){
            return true;
        } else{
            request.setAttribute(RequestEnum.INFORMATION.getValue(), "Нет прав");
            return false;
        }
    }
}
