package com.epam.command.impl.admin;

import com.epam.command.Command;
import com.epam.command.impl.JspPageName;
import com.epam.entity.User;
import com.epam.service.UserService;
import com.epam.service.exception.ServiceException;
import com.epam.service.factory.ServiceFactory;
import com.epam.service.utils.Constants;
import com.epam.servlet.RequestEnum;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetUsers implements Command {
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private static Logger logger = Logger.getLogger(GetUsers.class);
    private JspPageName jspPageName;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        jspPageName = JspPageName.USERS;
        try {
            String usersRole = request.getParameter(RequestEnum.USERS_ROLE.getValue());
            UserService userService = serviceFactory.getUserServiceImpl();
            List<User> users = userService.getAllUsersByRoleId(usersRole);
            request.setAttribute("users", users);
            request.setAttribute("newUserRole",usersRole);
        }catch (ServiceException e){
            logger.error(e.getMessage());
            jspPageName = JspPageName.INFORMATION;
            request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
        }
        return jspPageName.getPath();
    }

}
