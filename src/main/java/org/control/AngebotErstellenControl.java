package org.control;

import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.control.exception.DataBaseException;
import org.model.dao.AutoDao;
import org.model.dao.DaoFactory;
import org.model.entity.Auto;
import org.model.entity.Benutzer;
import org.model.entity.Vertriebler;
import org.ui.MainUI;

import java.util.ArrayList;

import static org.model.dao.DaoFactory.getInstance;

public class AngebotErstellenControl {

    public static void angebotErstellen(Auto auto) {
        try {
            AutoDao autoDao = DaoFactory.getInstance().getAutoDao();
            autoDao.createAuto(auto);

            Notification.show("Angebot erfolgreich erstellt.", Notification.Type.HUMANIZED_MESSAGE);

        } catch (DataBaseException e) {
            Notification.show(e.getReason(), Notification.Type.ERROR_MESSAGE);
        }

    }

    public static void angebotLöschen(Auto auto) {
        try {
            AutoDao autoDao = DaoFactory.getInstance().getAutoDao();
            autoDao.delete(auto.getId());

            Notification.show("Das von Ihnen angebotene Auto wurde erfolgreich entfernt.", Notification.Type.HUMANIZED_MESSAGE);
        } catch (DataBaseException throwables) {
            throwables.printStackTrace();
        }
    }

    public static ArrayList<Auto> getAlleAngebote () {
        ArrayList<Auto> alleAutos = null;

        try {
            AutoDao autoDao = DaoFactory.getInstance().getAutoDao();

            Benutzer benutzer = ((MainUI) UI.getCurrent()).getBenutzer();

            alleAutos = autoDao.getAlleAngebotenenAutos((Vertriebler) benutzer);
        } catch (DataBaseException throwables) {
            throwables.printStackTrace();
        }

        return alleAutos;
    }

}
