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
import java.io.IOException;

public class SignUp implements Command {
    private static Logger logger = Logger.getLogger(SignUp.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private JspPageName jspPageName;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        jspPageName = JspPageName.INDEX;
        try {
            UserService userService = serviceFactory.getUserServiceImpl();
            String login = request.getParameter(RequestEnum.LOGIN.getValue());
            String password = request.getParameter(RequestEnum.PASSWORD.getValue());
            String name = request.getParameter(RequestEnum.NAME.getValue());
            String surname = request.getParameter(RequestEnum.SURNAME.getValue());
            String address = request.getParameter(RequestEnum.ADDRESS.getValue());
            String email = request.getParameter(RequestEnum.EMAIL.getValue());
            userService.signUp(Constants.USER, login, password, name, surname, address, email);
            response.sendRedirect("/index.do");
        }catch (ServiceException | ServiceLogicException | IOException e){
            logger.error(e.getMessage());
            jspPageName = JspPageName.INFORMATION;
            request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
        }
        return jspPageName.getPath();
    }
}
