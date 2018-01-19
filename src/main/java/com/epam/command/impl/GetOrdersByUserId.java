package com.epam.command.impl;

import com.epam.command.Command;
import com.epam.dto.OrderDto;
import com.epam.service.OrderService;
import com.epam.service.exception.ServiceException;
import com.epam.service.factory.ServiceFactory;
import com.epam.service.utils.Constants;
import com.epam.servlet.RequestEnum;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetOrdersByUserId implements Command {
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private static Logger logger = Logger.getLogger(GetOrdersByUserId.class);
    private JspPageName jspPageName = JspPageName.ORDERS;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            String idUser = session.getAttribute(RequestEnum.ID_USER.getValue()).toString();
            String idRole = session.getAttribute(RequestEnum.USER_ROLE.getValue()).toString();
            if(Constants.USER.equals(idRole)) {
                OrderService orderService = serviceFactory.getOrderServiceImpl();
                List<OrderDto> orders = orderService.getOrdersDtoByUserIdAndStatus(idUser, Constants.STATUS_PAID);
                logger.info("Successfully getting orders by user id");
                request.setAttribute("orders", orders);
            }else{
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
