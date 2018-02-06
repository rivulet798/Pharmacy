package com.epam.command.impl.common;

import com.epam.command.Command;
import com.epam.command.impl.JspPageName;
import com.epam.dto.PrescriptionDto;
import com.epam.entity.Dosage;
import com.epam.entity.Medicament;
import com.epam.service.DosageService;
import com.epam.service.MedicamentService;
import com.epam.service.PrescriptionService;
import com.epam.service.exception.ServiceException;
import com.epam.service.factory.ServiceFactory;
import com.epam.service.utils.Constants;
import com.epam.servlet.RequestEnum;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetMedicamentById implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private static Logger logger = Logger.getLogger(GetMedicamentById.class);
    private JspPageName jspPageName;


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        jspPageName = JspPageName.MEDICAMENT;
        String idMedicament = request.getParameter(RequestEnum.ID_MEDICAMENT.getValue());
            try {
                HttpSession session = request.getSession();
                String idRole="";
                if(session.getAttribute(RequestEnum.USER_ROLE.getValue()).toString()!=null) {
                    idRole = session.getAttribute(RequestEnum.USER_ROLE.getValue()).toString();
                }
                    MedicamentService medicamentService = serviceFactory.getMedicamentServiceImpl();
                    Medicament medicament = medicamentService.getMedicamentById(idMedicament);
                    logger.info(request.getHeader("Successfully getting medicament by id"));
                    request.setAttribute("med", medicament);
                if(idRole.equals(Constants.USER)) {
                    DosageService dosageService = serviceFactory.getDosageServiceImpl();
                    List<Dosage> dosages = dosageService.getDosagesByMedicamentId(idMedicament);
                    request.setAttribute("dosages", dosages);
                    if (medicament.isPrescription()) {
                        if (session.getAttribute(RequestEnum.ID_USER.getValue()).toString() != null) {
                            String idUser = session.getAttribute(RequestEnum.ID_USER.getValue()).toString();
                            if (idRole.equals(Constants.USER)) {
                                PrescriptionService prescriptionService = serviceFactory.getPrescriptionServiceImpl();
                                List<PrescriptionDto> prescriptionList = prescriptionService.getPrescriptionsByUserIdAndMedId(idUser, String.valueOf(medicament.getId()));
                                request.setAttribute("prescriptions", prescriptionList);
                            }
                        }
                    }
                }
            }catch (ServiceException e){
                logger.error(e.getMessage());
                jspPageName = JspPageName.INFORMATION;
                request.setAttribute(RequestEnum.INFORMATION.getValue(), e.getMessage());
            }
            return jspPageName.getPath();
    }
 }
