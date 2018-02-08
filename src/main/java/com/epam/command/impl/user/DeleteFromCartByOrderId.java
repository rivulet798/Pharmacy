package com.epam.command.impl.user;

import com.epam.command.impl.JspPageName;
import com.epam.service.OrderService;
import com.epam.service.exception.ServiceException;
import com.epam.service.factory.ServiceFactory;
import com.epam.service.utils.Constants;
import com.epam.servlet.RequestEnum;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteFromCartByOrderId implements com.epam.command.Command {

    private static Logger logger = Logger.getLogger(DeleteFromCartByOrderId.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private JspPageName jspPageName;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        jspPageName = JspPageName.ORDERS;
        try {
            HttpSession session = request.getSession();
            String idUser = session.getAttribute(RequestEnum.ID_USER.getValue()).toString();
            OrderService orderService = serviceFactory.getOrderServiceImpl();
            String idOrder = request.getParameter(RequestEnum.ID_ORDER.getValue());
            if(!orderService.changeOrderStatus(idOrder, idUser, Constants.STATUS_DELETED)){
                jspPageName = JspPageName.INFORMATION;
                request.setAttribute(RequestEnum.INFORMATION.getValue(), "У вас нет прав для выполнения данной операции");
            }
        }catch (ServiceException e){
            logger.error(e.getMessage());
            jspPageName = JspPageName.INFORMATION;
            request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
        }
        return jspPageName.getPath();
    }
}

