package org.control;

import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.control.exception.DataBaseException;
import org.control.exception.NoSuchAutoException;
import org.control.exception.NoSuchReservierungException;
import org.control.exception.ReservierungAlreadyExistsException;
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
            int autoId = autoDao.getAuto(auto.getMarke(), auto.getBaujahr(), auto.getBeschreibung()).getId();

            //Fehlerbehandlung:Benutzer hat fahrzeug bereits reserviert
            if (reservierungDao.istReserviert(autoId, benutzer.getId())) {
                throw new ReservierungAlreadyExistsException();
            }

            Reservierung reservierung = new Reservierung();
            reservierung.setAutoId(autoId);
            reservierung.setEndId(benutzer.getId());
            reservierungDao.createReservierung(reservierung);

            Notification.show("Das ausgewählte Auto wurde Reserviert.", Notification.Type.HUMANIZED_MESSAGE);

        } catch (DataBaseException e) {
            Notification.show(e.getReason(), Notification.Type.ERROR_MESSAGE);
        }catch (ReservierungAlreadyExistsException ex) {
            Notification.show("Dieses Auto wurde bereits Reserviert.", Notification.Type.WARNING_MESSAGE);
        } catch (NoSuchAutoException e) {
            Notification.show("Dieses Auto existiert nicht mehr. Vielleicht wurde es in der Zwischenzeit von dem Vertriebler gelöscht.", Notification.Type.ERROR_MESSAGE);
        }
    }

    public static ArrayList<Auto> getAlleReservierteAutos() {
        ArrayList<Reservierung> reservierungen = new ArrayList<>();
        ArrayList<Auto> reservierteAutos = new ArrayList<>();

        try {
            ReservierungDao reservierungDao = DaoFactory.getInstance().getReservierungDao();
            AutoDao autoDao = DaoFactory.getInstance().getAutoDao();

            Benutzer benutzer =  ((MainUI) UI.getCurrent()).getBenutzer();

            reservierungen = reservierungDao.getReservierungen((Endnutzer) benutzer);

            //Umwandeln der AutoId in Reservierungen in das tatsächliche Auto Objekt
            for (Reservierung res : reservierungen) {
                Auto auto = autoDao.getAuto(res.getAutoId());
                reservierteAutos.add(auto);
            }

        } catch (DataBaseException | NoSuchAutoException throwables) {
            throwables.printStackTrace();
        }

        return reservierteAutos;
    }

    public static void reservierungAufheben (int autoId) {

        try {
            ReservierungDao reservierungDao = DaoFactory.getInstance().getReservierungDao();

            Benutzer benutzer =  ((MainUI) UI.getCurrent()).getBenutzer();
            reservierungDao.delete(reservierungDao.getReservierung(autoId, benutzer.getId()).getResId());

            Notification.show("Reservierung wurde aufgehoben", Notification.Type.HUMANIZED_MESSAGE);
        } catch (DataBaseException | NoSuchReservierungException throwables) {
            throwables.printStackTrace();
        }

    }

}
