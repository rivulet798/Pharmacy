package com.epam.command.impl;

import com.epam.command.Command;
import com.epam.service.UserService;
import com.epam.service.exception.ServiceException;
import com.epam.service.exception.ServiceLogicException;
import com.epam.service.factory.ServiceFactory;
import com.epam.service.utils.Constants;
import com.epam.servlet.RequestEnum;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddUser implements Command {
    private static Logger logger = Logger.getLogger(AddUser.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private JspPageName jspPageName;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        jspPageName = JspPageName.INFORMATION;
        try {
            HttpSession session = request.getSession();
            String idRole = session.getAttribute(RequestEnum.USER_ROLE.getValue()).toString();
            if(idRole.equals(Constants.ADMIN)) {
                String login = request.getParameter(RequestEnum.LOGIN.getValue());
                String password = request.getParameter(RequestEnum.PASSWORD.getValue());

                if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
                    request.setAttribute("errorData", "введите логин или пароль");
                    return jspPageName.getPath();
                }

                UserService userService = serviceFactory.getUserServiceImpl();
                String name = request.getParameter(RequestEnum.NAME.getValue());
                String surname = request.getParameter(RequestEnum.SURNAME.getValue());
                String address = request.getParameter(RequestEnum.ADDRESS.getValue());
                String email = request.getParameter(RequestEnum.EMAIL.getValue());
                String role = "";
                if (request.getParameter(RequestEnum.USER_ROLE.getValue()).equals(Constants.PHARMACIST)) {
                    role = Constants.PHARMACIST;

                } else if (request.getParameter(RequestEnum.USER_ROLE.getValue()).equals(Constants.DOCTOR))
                    role = Constants.DOCTOR;
                if (userService.signUp(role, login, password, name, surname, address, email)) {
                    request.setAttribute(RequestEnum.INFORMATION.getValue(), "User " + login + " is added");
                } else {
                    request.setAttribute(RequestEnum.INFORMATION.getValue(), "Can't add user " + login);
                }
            }
            else {
                request.setAttribute(RequestEnum.INFORMATION.getValue(), "Нет прав");
            }
        }catch (ServiceException | ServiceLogicException e){
            logger.error(e.getMessage());
            request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
        }
        return jspPageName.getPath();
    }
}
