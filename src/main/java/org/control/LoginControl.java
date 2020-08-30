package org.control;

import com.vaadin.ui.*;

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

                if(((CheckBox)userData.get(TextFieldUtil.BENUTZER_ERINNERN)).getValue())
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

}
