package com.epam.command.impl;

import com.epam.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SetLocale implements com.epam.command.Command {
    private static Logger logger = Logger.getLogger(SetLocale.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private JspPageName jspPageName = JspPageName.INFORMATION;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String jsp = request.getRequestURI().toString();
        String jsp1= request.getHeader("Referer");
        logger.info("/////////////////LOCALE//////"+jsp1);
        String locale = request.getParameter("locale");
        request.getSession().setAttribute("locale",locale);
        try {
            response.sendRedirect(jsp1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jspPageName.getPath();
    }
}
