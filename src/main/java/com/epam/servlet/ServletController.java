package com.epam.servlet;

import com.epam.command.Command;
import com.epam.command.CommandProvider;
import com.epam.service.utils.Constants;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

@WebServlet(name = "Controller",
        urlPatterns = {"*.do"},
        initParams = {@WebInitParam(name = "init_log4j", value = "/WEB-INF/log4j.properties")})
@MultipartConfig

public class ServletController extends HttpServlet {
    private static final String LOG4J_PARAM = "log4j";
    private static final long serialVersionUID = 1L;

    private CommandProvider commandProvider = CommandProvider.getInstance();


    @Override
    public void init() throws ServletException {
        try {
            String prefix = getServletContext().getRealPath("");

            String filename = getInitParameter(LOG4J_PARAM);
            if (filename == null) {
                BasicConfigurator.configure();
            } else {
                PropertyConfigurator.configure(prefix + File.separator + filename);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = commandProvider.getCommand(request);
        String page = command.execute(request, response);
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        if (dispatcher != null && !response.isCommitted()) {
            dispatcher.forward(request, response);
        } else {
            errorMessageDirectlyFromResponse(request, response);
        }
    }

    @Override
    public void destroy() {
        com.epam.command.CloseDBCommand command = new CloseDBCommand();
        command.closeDB();
    }

    private void errorMessageDirectlyFromResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().println(" UNKNOWN ERROR");
    }

    private class CloseDBCommand implements com.epam.command.CloseDBCommand {
        @Override
        public void closeDB() {

        }
    }
}
