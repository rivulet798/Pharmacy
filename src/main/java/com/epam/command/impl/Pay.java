package com.epam.command.impl;

import com.epam.command.Command;
import com.epam.service.OrderService;
import com.epam.service.exception.ServiceException;
import com.epam.service.factory.ServiceFactory;
import com.epam.service.utils.Constants;
import com.epam.servlet.RequestEnum;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Pay implements Command {
    private static Logger logger = Logger.getLogger(Pay.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private JspPageName jspPageName = JspPageName.INFORMATION;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            String idRole = session.getAttribute(RequestEnum.USER_ROLE.getValue()).toString();
            if(idRole.equals(Constants.USER)) {
                OrderService orderService = serviceFactory.getOrderServiceImpl();
                String idOrder = request.getParameter(RequestEnum.ID_ORDER.getValue());
                logger.info("/////////////"+idOrder);
                if(orderService.changeOrderStatus(idOrder, Constants.STATUS_PAID)){
                    request.setAttribute(RequestEnum.INFORMATION.getValue(), "Заказ успешно оплачен");
                }
                else {
                    request.setAttribute(RequestEnum.INFORMATION.getValue(), "Не удалось произвести оплату");
                }
            }
            else{
                jspPageName = JspPageName.INFORMATION;
                request.setAttribute(RequestEnum.INFORMATION.getValue(), "Нет прав");
            }
        }catch (ServiceException e){
            logger.error(e.getMessage());
            jspPageName = JspPageName.INFORMATION;
            request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
        }
        return jspPageName.getPath();
    }
}
