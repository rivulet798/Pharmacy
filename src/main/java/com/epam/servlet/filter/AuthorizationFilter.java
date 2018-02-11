package com.epam.servlet.filter;

import com.epam.command.CommandProvider;
import com.epam.command.impl.JspPageName;
import com.epam.service.utils.Constants;
import com.epam.servlet.Commands;
import com.epam.servlet.RequestEnum;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@WebFilter(filterName = "AuthorizationFilter",
        urlPatterns = {"*.do"})

public class AuthorizationFilter implements Filter {

    private static Logger logger = Logger.getLogger(AuthorizationFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String commandForFilter = ((HttpServletRequest)request).getServletPath();
        commandForFilter = commandForFilter.replace("/","");
        commandForFilter = commandForFilter.replace(".do","");
        HttpSession session = ((HttpServletRequest)request).getSession();
        if (session.isNew()) {
            session.setAttribute(RequestEnum.USER_ROLE.getValue(), Constants.GUEST);
            Locale locale = request.getLocale();
            session.setAttribute(RequestEnum.LOCALE.getValue(), locale );
        }
        Commands commands = Commands.getInstance();
        String roleWithAuthority = commands.checkCommand(commandForFilter);
        String currentRole = session.getAttribute(RequestEnum.USER_ROLE.getValue()).toString();
        if(!roleWithAuthority.equals(currentRole) && !roleWithAuthority.equals(Constants.GUEST)) {
            request.setAttribute(RequestEnum.INFORMATION.getValue(), "Нет прав");
            ServletContext ctx = request.getServletContext();
            RequestDispatcher dispatcher = ctx.getRequestDispatcher(JspPageName.INFORMATION.getPath());
            dispatcher.forward(request, response);
         }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
