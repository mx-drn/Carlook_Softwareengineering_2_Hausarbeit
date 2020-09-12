package org.control;

import com.vaadin.ui.UI;
import org.control.exception.DataBaseException;
import org.model.dao.DaoFactory;
import org.model.dao.ReservierungDao;
import org.model.entity.Auto;
import org.model.entity.Benutzer;
import org.model.entity.Reservierung;
import org.ui.MainUI;

public class ReservierungsControl {

    public static void reserviereAuto (Auto auto) {
        try {
            ReservierungDao reservierungDao = DaoFactory.getInstance().getReservierungDao();

            Benutzer benutzer =  ((MainUI) UI.getCurrent()).getBenutzer();

            Reservierung reservierung = new Reservierung();
            reservierung.setAutoId(auto.getId());
            reservierung.setEndId(benutzer.getId());
            reservierungDao.createReservierung(reservierung);

        } catch (DataBaseException e) {
            e.printStackTrace();
        }
    }

}
