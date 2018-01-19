package com.epam.dao.factory;

import com.epam.dao.*;

public class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();
    private final UserDao iUserDao = new UserDao();
    private final MedicamentDao iMedicamentDao = new MedicamentDao();
    private final PrescriptionDao iPrescriptionDao = new PrescriptionDao();
    private final OrderDao iOrderDao = new OrderDao();
    private final RequestDao iRequestDao = new RequestDao();
    private final DosageDao iDosageDao = new DosageDao();

    private DaoFactory() {}
    public static DaoFactory getInstance() {
        return instance;
    }

    public UserDao getIUserDao() {
        return iUserDao;
    }
    public IMedicamentDao getIMedicamentDao() {
        return iMedicamentDao;
    }
    public IPrescriptionDao getIPrescriptionDao() {
        return iPrescriptionDao;
    }
    public IOrderDao getIOrderDao() {
        return iOrderDao;
    }
    public IRequestDao getIRequestDao() {
        return iRequestDao;
    }
    public IDosageDao getIDosageDao() {
        return iDosageDao;
    }

}
