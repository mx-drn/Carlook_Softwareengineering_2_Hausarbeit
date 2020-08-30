package org.control;

import com.vaadin.ui.*;
import org.control.exception.DataBaseException;
import org.control.exception.NoSuchUserOrPasswordException;
import org.control.exception.RegistrierungException;
import org.model.entity.Benutzer;
import org.ui.MainUI;

import java.util.ArrayList;
import java.util.Map;

import static org.services.util.FieldControl.checkEmptyFields;

public class LoginControl {

    public static void benutzerLogin (Map<String, AbstractField> userData){
        try {
            checkEmptyFields(userData);
            TextField usernameTextField = (TextField) userData.get("Benutzername");
            TextField passwordTextField = (TextField) userData.get("Passwort");
            try {
                //LoginControl.navigate(
                LoginControl.checkAuthentication(usernameTextField.getValue(),
                        passwordTextField.getValue());

                if(((CheckBox)userData.get("cbxBenutzerErinnern")).getValue())
                {
                    JavascriptUtil.setCookieUser(usernameTextField.getValue());
                }
                Notification.show("Login erfolgreich", Notification.Type.HUMANIZED_MESSAGE);
            }
            //Datenbankfehler
            catch (DataBaseException ex) {
                Notification.show(ex.getReason(), Notification.Type.ERROR_MESSAGE);
            }
            //Fehler beim Authentifizieren
            catch (NoSuchUserOrPasswordException ex) {
                Notification.show("Login nicht möglich. Bitte überprüfen Sie Ihre Eingaben!", Notification.Type.ERROR_MESSAGE);
                usernameTextField.setValue("");
                passwordTextField.setValue("");
            }

        } catch (RegistrierungException ex) {
            Notification.show(ex.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }

    public static void checkAuthentication(String username, String password) throws NoSuchUserOrPasswordException, DataBaseException {

        GenericDao<Benutzer> benutzerDao = DaoFactory.getInstance().getBenutzerDao(false, false);
        String[] columns = { "benutzername", "passwort" };
        String[] values = { username, password };
        ArrayList<Benutzer> benutzerList = benutzerDao.getByAttributs(columns, values, false);
        // Benutzer gefunden
        if(benutzerList != null && benutzerList.size() > 0) {
            Benutzer benutzer = benutzerList.get(0);
            // Benutzer ist Student
            if(benutzer.getRolle().equals(Rolle.STUDENT)) {
                benutzer = DaoFactory.getInstance().getStudentenDao(benutzerDao, true).getById(benutzer.getBenutzerid());
                ((MainUI) UI.getCurrent()).setBenutzer(benutzer);
                UI.getCurrent().getNavigator().navigateTo(ViewUtil.MAIN_STUDENT);
            }
            else if(benutzer.getRolle().equals(Rolle.UNTERNEHMEN)) {
                benutzer = DaoFactory.getInstance().getUnternehmensDao(benutzerDao, true).getById(benutzer.getBenutzerid());
                ((MainUI) UI.getCurrent()).setBenutzer(benutzer);
                UI.getCurrent().getNavigator().navigateTo(ViewUtil.MAIN_UNTERNEHMEN);
            }
            else if(benutzer.getRolle().equals(Rolle.ADMIN)) {
                benutzerDao.closeConnection();
                ((MainUI) UI.getCurrent()).setBenutzer(benutzer);
                UI.getCurrent().getNavigator().navigateTo(ViewUtil.ADMIN);
            }
        }
        else {
            throw new NoSuchUserOrPasswordException();
        }
    }

}
