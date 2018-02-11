package com.epam.command.impl.common;

import com.epam.command.impl.JspPageName;
import com.epam.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SetLocale implements com.epam.command.Command {
    private JspPageName jspPageName;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        jspPageName = JspPageName.INFORMATION;
        String jsp= request.getHeader("Referer");
        String locale = request.getParameter("locale");
        request.getSession().setAttribute("locale",locale);
        try {
            response.sendRedirect(jsp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jspPageName.getPath();
    }
}
