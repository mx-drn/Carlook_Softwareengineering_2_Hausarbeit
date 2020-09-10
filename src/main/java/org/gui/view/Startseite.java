package org.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.control.LoginControl;
import org.control.RegistrierungsControl;
import org.control.exception.RolesException;
import org.control.exception.NoCarlookEmailAdresseException;
import org.control.exception.NoSuchUserOrPasswordException;
import org.gui.components.Footer;
import org.gui.components.SchriftzugCarlook;
import org.services.util.CheckCarlookEmail;
import org.services.util.StylesheetUtil;
import org.services.util.Whitespace;

public class Startseite extends VerticalLayout implements View {
    Whitespace whitespace = new Whitespace();

    public void enter (ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        this.setUp();
    }

    public void setUp () {
        //Erstellen des Hintergrundes
        setStyleName("bg");
        setSizeFull();

        SchriftzugCarlook schriftzugCarlook = new SchriftzugCarlook();
        addComponent(schriftzugCarlook);
        setComponentAlignment(schriftzugCarlook, Alignment.TOP_CENTER);
        schriftzugCarlook.setStyleName(StylesheetUtil.schriftzug);

        addComponent(whitespace.getWhitespace());

        //Tabsheet für das wechseln zwischen Login und Registrierung
        TabSheet tabSheet = new TabSheet();
        addComponent(tabSheet);
        VerticalLayout tabLinks = new VerticalLayout();
        VerticalLayout tabRechts = new VerticalLayout();

        //E-Mail Adresse für den Login
        TextField email = new TextField("E-Mail");
        email.setDescription("Ihre E-Mail Adresse");

        //Passwort
        TextField passwort = new TextField("Passwort");
        passwort.setDescription("Ihr Passwort");

        Button anmelden = new Button("Anmelden");
        anmelden.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                try {
                    LoginControl.benutzerLogin(email.getValue(), passwort.getValue());
                } catch (NoSuchUserOrPasswordException e) {
                    //Fehler beim Authentifizieren
                    Notification.show("Login nicht möglich. Bitte überprüfen Sie Ihre Eingaben!", Notification.Type.ERROR_MESSAGE);
                    email.setValue("");
                    passwort.setValue("");
                }

            }
        });

        tabLinks.addComponent(email);
        tabLinks.addComponent(passwort);
        tabLinks.addComponent(anmelden);
        tabLinks.setMargin(true);

        //E-Mail Adresse für das Registrieren
        TextField emailReg = new TextField("E-Mail");
        email.setDescription("Ihre E-Mail Adresse");

        //Passwort
        TextField passwortReg = new TextField("Passwort");
        passwort.setDescription("Ihr Passwort");

        //Rolle wählen
        CheckBox checkBoxEndnutzer = new CheckBox("Ich bin Endnutzer");
        CheckBox checkBoxVertriebler = new CheckBox("Ich bin Vertriebler");

        Button registrieren = new Button("Registrieren");

        registrieren.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                String rolle = "";
                if(checkBoxEndnutzer.getValue() == true) {
                    rolle = "Endnutzer";
                }else if (checkBoxVertriebler.getValue() == true) {
                    rolle = "Vertriebler";
                }else if (checkBoxVertriebler.getValue() == true && checkBoxEndnutzer.getValue() == true) {
                    Notification.show("Sie dürfen nur eine Rolle Auswählen!", Notification.Type.ERROR_MESSAGE);
                    throw new RolesException();
                }else {
                   Notification.show("Sie müssen eine Rolle Auswählen!", Notification.Type.ERROR_MESSAGE);
                   throw new RolesException();
                }

                String reg_email = emailReg.getValue();

                //Wenn Vertriebler -> ist es eine Carlook.de E-Mail?
                if(rolle.equals("Vertriebler")) {
                    if(!CheckCarlookEmail.istCarlookEmail(reg_email)) {
                        emailReg.setValue("");
                        throw new NoCarlookEmailAdresseException();
                    }
                }

                String reg_passwort = passwortReg.getValue();
                RegistrierungsControl.registrierungBenutzer(reg_email, reg_passwort, rolle);

                } catch (NoCarlookEmailAdresseException | RolesException e) {
                    //Fehler beim Authentifizieren
                    Notification.show("Ihre Email ist keine korrekte \"@carlook.de\" - Adresse!", Notification.Type.ERROR_MESSAGE);
                    emailReg.setValue("");
                }
            }
        });

        //tabRechts.addComponent(horizontalLayoutName);
        tabRechts.addComponent(emailReg);
        tabRechts.addComponent(passwortReg);
        tabRechts.addComponent(checkBoxEndnutzer);
        tabRechts.addComponent(checkBoxVertriebler);

        tabRechts.addComponent(registrieren);
        tabLinks.setStyleName(StylesheetUtil.tabsheet);
        tabRechts.setStyleName(StylesheetUtil.tabsheet);

        tabSheet.addTab(tabLinks, "Login");
        tabSheet.addTab(tabRechts, "Registrieren");
        setComponentAlignment(tabSheet, Alignment.MIDDLE_CENTER);
        tabSheet.setWidth("25%");


        addComponent(whitespace.getWhitespace());
        addComponent(whitespace.getWhitespace());

        Footer footer = new Footer();
        addComponent(footer);
        setComponentAlignment(footer, Alignment.MIDDLE_CENTER);

    }
}
