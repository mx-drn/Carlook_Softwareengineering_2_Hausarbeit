package org.model.dao;

import org.control.exception.DataBaseException;
import org.model.dao.BenutzerDao;

public class DaoFactory {
    private static DaoFactory instance = null;

    private DaoFactory() {}

    /**
     * Returns an instance of the singleton DaoFactory.
     * @return An instance of DaoFactory.
     */
    public static DaoFactory getInstance() {
        if(DaoFactory.instance == null) {
            DaoFactory.instance = new DaoFactory();
        }
        return DaoFactory.instance;
    }

    public BenutzerDao getBenutzerDao() {
        return BenutzerDao.getInstance();
    }

    public AutoDao getAutoDao() {
        return AutoDao.getInstance();
    }

    public ReservierungDao getReservierungDao() {
        return ReservierungDao.getInstance();
    }

}
