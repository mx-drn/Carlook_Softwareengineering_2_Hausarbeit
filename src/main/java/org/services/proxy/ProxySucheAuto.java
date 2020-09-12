package org.services.proxy;

import org.control.exception.DataBaseException;
import org.model.dao.AutoDao;
import org.model.dao.DaoFactory;
import org.model.entity.Auto;

import java.util.ArrayList;

public class ProxySucheAuto implements ProxySucheAutoAPI {
    private AutoDao autoDao = null;

    public ProxySucheAuto() throws DataBaseException {
        this.autoDao = DaoFactory.getInstance().getAutoDao();
    }

    @Override
    public ArrayList<Auto> searchAuto(String[] request) throws DataBaseException {
        return this.autoDao.searchAuto(request);
    }

    @Override
    public void closeConnection() throws DataBaseException {
        this.autoDao.closeConnection();
    }
}
