package com.epam.command.impl.user;

import com.epam.command.Command;
import com.epam.command.impl.JspPageName;
import com.epam.service.OrderService;
import com.epam.service.PrescriptionService;
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
    private JspPageName jspPageName;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        jspPageName = JspPageName.INFORMATION;
        try {
            if(checkRole(request)) {
                OrderService orderService = serviceFactory.getOrderServiceImpl();
                PrescriptionService prescriptionService = serviceFactory.getPrescriptionServiceImpl();
                String idOrder = request.getParameter(RequestEnum.ID_ORDER.getValue());
                if(prescriptionService.isPrescriptionValid(idOrder)){
                    orderService.changeOrderStatus(idOrder, Constants.STATUS_PAID);
                    prescriptionService.setPrescriptionInvalidByOrderId(idOrder);
                    request.setAttribute(RequestEnum.INFORMATION.getValue(), "Заказ успешно оплачен");
                }
                else {
                    orderService.changeOrderStatus(idOrder, Constants.STATUS_DELETED);
                    request.setAttribute(RequestEnum.INFORMATION.getValue(), "Вы уже использовали электронный рецепт на данный препарат. Товар будет удален из вашей корзины.");
                }
            }
        }catch (ServiceException e){
            logger.error(e.getMessage());
            request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
        }
        return jspPageName.getPath();
    }

    private boolean checkRole(HttpServletRequest request){
        HttpSession session = request.getSession();
        String idRole = session.getAttribute(RequestEnum.USER_ROLE.getValue()).toString();
        if(idRole.equals(Constants.USER)){
            return true;
        } else{
            request.setAttribute(RequestEnum.INFORMATION.getValue(), "Нет прав");
            return false;
        }
    }
}
