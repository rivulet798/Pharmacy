package com.epam.command.impl;

import com.epam.command.Command;
import com.epam.entity.Medicament;
import com.epam.entity.User;
import com.epam.service.MedicamentService;
import com.epam.service.UserService;
import com.epam.service.exception.ServiceException;
import com.epam.service.exception.ServiceLogicException;
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
    private JspPageName jspPageName = JspPageName.ADD_PRESCRIPTION;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String userRole = session.getAttribute(RequestEnum.USER_ROLE.getValue()).toString();
        if(userRole.equals(Constants.DOCTOR)){
            MedicamentService medicamentService = serviceFactory.getMedicamentServiceImpl();
            UserService userService = serviceFactory.getUserServiceImpl();
            try {
                SecureRandom secureRandom = new SecureRandom();
                String csrfToken = String.valueOf(secureRandom.nextLong());
                csrfToken = Hasher.hashBySha1(csrfToken);
                List<User> users = userService.getAllUsersByRoleId(Constants.USER);
                List<Medicament> medicaments = medicamentService.getMedicamentsWithPrescription();
                request.setAttribute(RequestEnum.MEDICAMENTS.getValue(), medicaments);
                request.setAttribute(RequestEnum.USERS.getValue(), users);
                session.setAttribute(RequestEnum.CSRF_TOKEN.getValue(),csrfToken);
            }catch (ServiceException | ServiceLogicException e){
                logger.error(e.getMessage());
                jspPageName = JspPageName.INFORMATION;
                request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
            }
        }else{
            jspPageName = JspPageName.INFORMATION;
            request.setAttribute(RequestEnum.INFORMATION.getValue(), "Нет прав");
        }

        return jspPageName.getPath();
    }
}
