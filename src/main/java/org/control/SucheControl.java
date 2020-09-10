package org.control;

import org.control.exception.DataBaseException;
import org.model.dto.AutoDTO;
import org.services.proxy.ProxySucheAuto;

import java.util.ArrayList;

public class SucheControl {
    private ProxySucheAuto proxySucheAuto;

    public ArrayList<AutoDTO> getAutoBySuche(String request) throws DataBaseException {

        if(this.proxySucheAuto == null) {
            this.proxySucheAuto = new ProxySucheAuto();
        }

        String[] requestSplit = request.split(" ");

        return this.proxySucheAuto.searchAuto(requestSplit);
    }

    public void closeConAuto() throws DataBaseException {
        this.proxySucheAuto.closeConnection();
    }

}