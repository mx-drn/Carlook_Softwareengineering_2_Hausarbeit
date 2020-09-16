package org.control;

import com.vaadin.ui.*;
import org.control.exception.DataBaseException;
import org.control.exception.NoSuchUserOrPasswordException;
import org.control.exception.RegistrierungException;
import org.model.dao.BenutzerDao;
import org.model.dao.DaoFactory;
import org.model.entity.Benutzer;
import org.services.util.JavascriptUtil;
import org.services.util.Rolle;
import org.services.util.ViewUtil;
import org.ui.MainUI;

import java.util.ArrayList;
import java.util.Map;

import static org.services.util.FieldControl.checkEmptyFields;

public class LoginControl {

    public static void benutzerLogin (String email, String passwort) throws NoSuchUserOrPasswordException {
        try {
            //LoginControl.navigate(
            LoginControl.checkAuthentication(email, passwort);
            Notification.show("Login erfolgreich", Notification.Type.HUMANIZED_MESSAGE);
        }
        //Datenbankfehler
        catch (DataBaseException ex) {
            Notification.show(ex.getReason(), Notification.Type.ERROR_MESSAGE);
        }


    }

    public static void checkAuthentication(String email, String passwort) throws NoSuchUserOrPasswordException, DataBaseException {

        BenutzerDao benutzerDao = DaoFactory.getInstance().getBenutzerDao();
        Benutzer benutzer = benutzerDao.getBenutzer(email, passwort);
        // Benutzer gefunden
        if(benutzer != null) {
            // Benutzer ist Student
            if(benutzer.getRolle().equals(Rolle.ENDNUTZER)) {
                ((MainUI) UI.getCurrent()).setBenutzer(benutzer);
                UI.getCurrent().getNavigator().navigateTo(ViewUtil.VIEWENDNNUTZER);
                benutzerDao.closeConnection();
            }
            //Benutzer ist Vertriebler
            else if(benutzer.getRolle().equals(Rolle.VERTRIEBLER)) {
                ((MainUI) UI.getCurrent()).setBenutzer(benutzer);
                UI.getCurrent().getNavigator().navigateTo(ViewUtil.VIEWVERTRIEBLER);
                benutzerDao.closeConnection();
            }
        }
        else {
            throw new NoSuchUserOrPasswordException();
        }
    }

}
