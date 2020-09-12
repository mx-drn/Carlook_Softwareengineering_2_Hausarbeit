package org.services.proxy;

import org.control.exception.DataBaseException;
import org.model.entity.Auto;

import java.util.ArrayList;

public interface ProxySucheAutoAPI {

    public ArrayList<Auto> searchAuto(String[] request) throws DataBaseException;

    public void closeConnection() throws DataBaseException;

}
