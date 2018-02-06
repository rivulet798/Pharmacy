package com.epam.command.impl;

import com.epam.command.Command;
import com.epam.entity.User;
import com.epam.service.UserService;
import com.epam.service.exception.ServiceException;
import com.epam.service.factory.ServiceFactory;
import com.epam.servlet.RequestEnum;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignIn implements Command {
    private static Logger logger = Logger.getLogger(SignIn.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private JspPageName jspPageName;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        jspPageName = JspPageName.INDEX;
        logger.debug(request.getHeader("User-Agent") + " try to sign in account");
        String login = request.getParameter(RequestEnum.LOGLOGIN.getValue());
        String password = request.getParameter(RequestEnum.LOGPASSWORD.getValue());

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            request.setAttribute("errorData", "введите логин или пароль");
            jspPageName = JspPageName.INFORMATION;
            return jspPageName.getPath();
        }
        try {
            UserService userService = serviceFactory.getUserServiceImpl();
            User user = userService.signIn(login, password);
            logger.info(user);
            if (user!=null && user.getIdRole()>0 && user.getIdRole()<5) {
                HttpSession session = request.getSession();
                session.setAttribute(RequestEnum.ID_USER.getValue(), user.getId());
                session.setAttribute(RequestEnum.USER_LOGIN.getValue(), user.getLogin());
                session.setAttribute(RequestEnum.USER_ROLE.getValue(), user.getIdRole());
                logger.info("Successfull sign in account as " + login);
                response.sendRedirect("/index.do");
            } else {
                logger.info(request.getHeader("User-Agent") + " unsuccessfully sign in account.");
                jspPageName = JspPageName.INFORMATION;
                request.setAttribute(RequestEnum.INFORMATION.getValue(), "Uncorrect login or password");
            }
        }catch (ServiceException | IOException e){
            logger.error(e.getMessage());
            jspPageName = JspPageName.INFORMATION;
            request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
        }
        return jspPageName.getPath();
    }
}

