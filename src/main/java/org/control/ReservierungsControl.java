package org.control;

import com.vaadin.ui.UI;
import org.control.exception.DataBaseException;
import org.control.exception.NoSuchAutoException;
import org.control.exception.NoSuchReservierungException;
import org.model.dao.AutoDao;
import org.model.dao.DaoFactory;
import org.model.dao.ReservierungDao;
import org.model.entity.Auto;
import org.model.entity.Benutzer;
import org.model.entity.Endnutzer;
import org.model.entity.Reservierung;
import org.ui.MainUI;

import java.util.ArrayList;

public class ReservierungsControl {

    public static void reserviereAuto (Auto auto) {
        try {
            ReservierungDao reservierungDao = DaoFactory.getInstance().getReservierungDao();
            AutoDao autoDao = DaoFactory.getInstance().getAutoDao();

            Benutzer benutzer =  ((MainUI) UI.getCurrent()).getBenutzer();

            Reservierung reservierung = new Reservierung();
            reservierung.setAutoId(autoDao.getAuto(auto.getMarke(), auto.getBaujahr(), auto.getBeschreibung()).getId());
            reservierung.setEndId(benutzer.getId());
            reservierungDao.createReservierung(reservierung);

        } catch (DataBaseException | NoSuchAutoException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Auto> getAllReservierungen () {
        ArrayList<Reservierung> reservierungen = new ArrayList<>();
        ArrayList<Auto> reservierteAutos = new ArrayList<>();

        try {
            ReservierungDao reservierungDao = DaoFactory.getInstance().getReservierungDao();
            AutoDao autoDao = DaoFactory.getInstance().getAutoDao();

            Benutzer benutzer =  ((MainUI) UI.getCurrent()).getBenutzer();

            reservierungen = reservierungDao.getReservierungen((Endnutzer) benutzer);

            //Umwandeln der AutoId in Reservierungen in das tatsächliche AUto Objekt

            for (Reservierung res : reservierungen) {
                Auto auto = autoDao.getAuto(res.getAutoId());
                reservierteAutos.add(auto);
            }

        } catch (DataBaseException | NoSuchReservierungException | NoSuchAutoException throwables) {
            throwables.printStackTrace();
        }

        return reservierteAutos;
    }

}
