package com.epam.dao.factory;

import com.epam.dao.*;
import com.epam.dao.impl.*;

public class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();
    private final UserDaoImpl iUserDaoImpl = new UserDaoImpl();
    private final MedicamentDaoImpl iMedicamentDaoImpl = new MedicamentDaoImpl();
    private final PrescriptionDaoImpl iPrescriptionDaoImpl = new PrescriptionDaoImpl();
    private final OrderDaoImpl iOrderDaoImpl = new OrderDaoImpl();
    private final RequestDaoImpl iRequestDaoImpl = new RequestDaoImpl();
    private final DosageDaoImpl iDosageDaoImpl = new DosageDaoImpl();

    private DaoFactory() {}
    public static DaoFactory getInstance() {
        return instance;
    }

    public UserDaoImpl getIUserDao() {
        return iUserDaoImpl;
    }
    public MedicamentDao getIMedicamentDao() {
        return iMedicamentDaoImpl;
    }
    public PrescriptionDao getIPrescriptionDao() {
        return iPrescriptionDaoImpl;
    }
    public OrderDao getIOrderDao() {
        return iOrderDaoImpl;
    }
    public RequestDao getIRequestDao() {
        return iRequestDaoImpl;
    }
    public DosageDao getIDosageDao() {
        return iDosageDaoImpl;
    }

}
