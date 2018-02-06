package com.epam.command.impl;

import com.epam.command.Command;
import com.epam.dto.PrescriptionDto;
import com.epam.entity.Medicament;
import com.epam.service.MedicamentService;
import com.epam.service.OrderService;
import com.epam.service.PrescriptionService;
import com.epam.service.exception.ServiceException;
import com.epam.service.exception.ServiceLogicException;
import com.epam.service.factory.ServiceFactory;
import com.epam.service.utils.Constants;
import com.epam.servlet.RequestEnum;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AddToCart implements Command {

    private static Logger logger = Logger.getLogger(AddToCart.class);
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private JspPageName jspPageName;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        jspPageName = JspPageName.INFORMATION;
        try {
            int idOrder = 0;
            if(checkRole(request)){
                MedicamentService medicamentService = serviceFactory.getMedicamentServiceImpl();
                String idMedicament = request.getParameter(RequestEnum.ID_MEDICAMENT.getValue());
                Medicament medicament = medicamentService.getMedicamentById(idMedicament);
                if(medicament.isPrescription()) {
                    idOrder = addToCardMedWithPrescription(request);
                } else if(!medicament.isPrescription()){
                    idOrder = addToCartMedWithoutPrescription(request);
                }
                request.setAttribute(RequestEnum.ID_ORDER.getValue(),idOrder);
                jspPageName = JspPageName.PAYMENT;
            }
        }catch (ServiceException | ServiceLogicException e){
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

    private int addToCardMedWithPrescription(HttpServletRequest request) throws ServiceException, ServiceLogicException{
        OrderService orderService = serviceFactory.getOrderServiceImpl();
        String idPrescription = request.getParameter(RequestEnum.ID_PRESCRIPTION.getValue());
        String idUser = request.getSession().getAttribute(RequestEnum.ID_USER.getValue()).toString();
        String idMedicament = request.getParameter(RequestEnum.ID_MEDICAMENT.getValue());
        int prescriptionId = Integer.parseInt(idPrescription);
        PrescriptionService prescriptionService = serviceFactory.getPrescriptionServiceImpl();
        List<PrescriptionDto> prescriptions = prescriptionService.getPrescriptionsByUserIdAndMedId(idUser,idMedicament);
        for(PrescriptionDto p : prescriptions){
            if((prescriptionId==p.getIdPrescription()) && p.isValid()){
                return orderService.addToCartMedWithPrescription(idUser,idMedicament,String.valueOf(p.getNumber()),String.valueOf(p.getIdDosage()),idPrescription);
            }
        }
        return 0;
    }

    private int addToCartMedWithoutPrescription(HttpServletRequest request)throws ServiceException, ServiceLogicException{
        OrderService orderService = serviceFactory.getOrderServiceImpl();
        String idUser = request.getSession().getAttribute(RequestEnum.ID_USER.getValue()).toString();
        String idMedicament = request.getParameter(RequestEnum.ID_MEDICAMENT.getValue());
        String number = request.getParameter(RequestEnum.NUMBER.getValue());
        String idDosage = request.getParameter(RequestEnum.ID_DOSAGE.getValue());
        return orderService.addToCartMedWithoutPrescription(idUser, idMedicament, number, idDosage);
    }
}
