package org.control;

import com.vaadin.ui.Notification;
import org.control.exception.DataBaseException;
import org.model.dao.BenutzerDao;
import org.model.dao.DaoFactory;
import org.model.entity.Benutzer;
import org.model.entity.Endnutzer;
import org.model.entity.Vertriebler;

public class RegistrierungsControl {

    public static void registrierungBenutzer(String email, String passwort, String rolle) {
        try{
            BenutzerDao benutzerDao = DaoFactory.getInstance().getBenutzerDao();

            //Zu registrierendes NUtzerobjekt erstellen, ohne id
            if (rolle.equals("Endnutzer")) {
                Endnutzer endnutzer = new Endnutzer();
                endnutzer.setEmail(email);
                endnutzer.setPasswort(passwort);
                endnutzer.setRolle(rolle);

                //Benutzer in DB einfügen
                benutzerDao.saveUser(endnutzer);
            }else if (rolle.equals("Vertriebler")){
                Vertriebler vertriebler = new Vertriebler();
                vertriebler.setEmail(email);
                vertriebler.setPasswort(passwort);
                vertriebler.setRolle(rolle);

                //Benutzer in DB einfügen
                benutzerDao.saveUser(vertriebler);
            }

            Notification.show("Registrierung erfolgreich", Notification.Type.HUMANIZED_MESSAGE);

        } catch (DataBaseException e) {
            Notification.show(e.getReason(), Notification.Type.ERROR_MESSAGE);
        }
    }

}
