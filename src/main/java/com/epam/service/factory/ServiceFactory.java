package com.epam.service.factory;

import com.epam.service.*;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private final UserServiceImpl userService = new UserServiceImpl();
    private final MedicamentServiceImpl medicamentService = new MedicamentServiceImpl();
    private final PrescriptionServiceImpl prescriptionService = new PrescriptionServiceImpl();
    private final OrderServiceImpl orderService = new OrderServiceImpl();
    private final RequestServiceImpl requestService = new RequestServiceImpl();
    private final DosageServiceImpl dosageService = new DosageServiceImpl();


    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserServiceImpl getUserServiceImpl() {
        return userService;
    }
    public MedicamentServiceImpl getMedicamentServiceImpl() {
        return medicamentService;
    }
    public PrescriptionServiceImpl getPrescriptionServiceImpl() {
        return prescriptionService;
    }
    public OrderServiceImpl getOrderServiceImpl() {
        return orderService;
    }
    public RequestServiceImpl getRequestServiceImpl() {
        return requestService;
    }
    public DosageServiceImpl getDosageServiceImpl() {
        return dosageService;
    }

}
