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
    private JspPageName jspPageName = JspPageName.INFORMATION;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            String idRole="";
            int idOrder = 0;
            if(session.getAttribute(RequestEnum.USER_ROLE.getValue()).toString()!=null) {
                idRole = session.getAttribute(RequestEnum.USER_ROLE.getValue()).toString();
            }
            if(idRole.equals(Constants.USER)) {
                OrderService orderService = serviceFactory.getOrderServiceImpl();
                String idUser = request.getSession().getAttribute(RequestEnum.ID_USER.getValue()).toString();
                String idMedicament = request.getParameter(RequestEnum.ID_MEDICAMENT.getValue());
                MedicamentService medicamentService = serviceFactory.getMedicamentServiceImpl();
                Medicament medicament = medicamentService.getMedicamentById(idMedicament);
                if(medicament.isPrescription()) {
                    String idPrescription = request.getParameter(RequestEnum.ID_PRESCRIPTION.getValue());
                    int id = Integer.parseInt(idPrescription);
                    logger.info(idPrescription);
                    PrescriptionService prescriptionService = serviceFactory.getPrescriptionServiceImpl();
                    List<PrescriptionDto> prescriptions = prescriptionService.getPrescriptionsByUserIdAndMedId(idUser,idMedicament);
                    for(PrescriptionDto p : prescriptions){
                        if((id==p.getIdPrescription()) && p.isValid()){
                            idOrder = orderService.addToCartMedWithPrescription(idUser,idMedicament,String.valueOf(p.getNumber()),String.valueOf(p.getIdDosage()),idPrescription);
                        }
                    }
                } else if(!medicament.isPrescription()){
                    String number = request.getParameter(RequestEnum.NUMBER.getValue());
                    String idDosage = request.getParameter(RequestEnum.ID_DOSAGE.getValue());
                    idOrder = orderService.addToCartMedWithoutPrescription(idUser, idMedicament, number, idDosage);
                }
                request.setAttribute("idOrder",idOrder);
                jspPageName = JspPageName.PAYMENT;
            }
            else{
                request.setAttribute(RequestEnum.INFORMATION.getValue(), "Нет прав");
            }
        }catch (ServiceException | ServiceLogicException e){
            logger.error(e.getMessage());
            jspPageName = JspPageName.INFORMATION;
            request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
        }
        return jspPageName.getPath();
    }
}
