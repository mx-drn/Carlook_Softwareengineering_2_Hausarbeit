package org.services.proxy;

import org.control.exception.DataBaseException;
import org.model.dto.AutoDTO;

import java.util.ArrayList;

public interface ProxySucheAutoAPI {

    public ArrayList<AutoDTO> searchAuto(String[] request) throws DataBaseException;

    public void closeConnection() throws DataBaseException;

}
