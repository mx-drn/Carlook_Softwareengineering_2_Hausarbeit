package org.gui.view;

import com.vaadin.icons.VaadinIcons;
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
import org.ui.MainUI;

public class Startseite extends VerticalLayout implements View {
    Whitespace whitespace = new Whitespace();

    public void enter (ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        this.setUp();
    }

    public void setUp () {
        //Erstellen des Hintergrundes
        setStyleName(StylesheetUtil.bg);
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

        //FormLayout für besseres Design
        FormLayout formLayoutLogin = new FormLayout();

        //E-Mail Adresse für den Login
        TextField email = new TextField("E-Mail");
        email.setIcon(VaadinIcons.ENVELOPE);
        email.setDescription("Ihre E-Mail Adresse");

        //Passwort
        TextField passwort = new TextField("Passwort");
        passwort.setIcon(VaadinIcons.EYE_SLASH);
        passwort.setDescription("Ihr Passwort");

        Button anmelden = new Button("Anmelden");
        anmelden.setId("Anmeldebutton");
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
                }catch (NullPointerException ex) {
                    Notification.show("Es scheint, als sei die Verbindung abgebrochen, bitte versuchen Sie es erneut.", Notification.Type.WARNING_MESSAGE);
                    email.setValue("");
                    passwort.setValue("");
                }

            }
        });

        formLayoutLogin.addComponent(email);
        formLayoutLogin.addComponent(passwort);

        formLayoutLogin.addComponent(anmelden);

        tabLinks.addComponent(formLayoutLogin);
        //tabLinks.addComponent(anmelden);
        tabLinks.setMargin(true);

        //FormLayout für das registrieren
        FormLayout formLayoutReg = new FormLayout();

        //E-Mail Adresse für das Registrieren
        TextField emailReg = new TextField("E-Mail");
        emailReg.setIcon(VaadinIcons.ENVELOPE);
        emailReg.setDescription("Ihre E-Mail Adresse");

        //Passwort
        TextField passwortReg = new TextField("Passwort");
        passwortReg.setIcon(VaadinIcons.EYE_SLASH);
        passwortReg.setDescription("Ihr Passwort");

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
                if(!emailReg.isEmpty() && !passwortReg.isEmpty()) {
                    RegistrierungsControl.registrierungBenutzer(reg_email, reg_passwort, rolle);
                }else{
                    Notification.show("Sie müssen alle Felder ausfüllen!", Notification.Type.ERROR_MESSAGE);
                }

                } catch (NoCarlookEmailAdresseException | RolesException e) {
                    //Fehler beim Authentifizieren
                    Notification.show("Ihre Email ist keine korrekte \"@carlook.de\" - Adresse!", Notification.Type.ERROR_MESSAGE);
                    emailReg.setValue("");
                }
            }
        });

        formLayoutReg.addComponent(emailReg);
        formLayoutReg.addComponent(passwortReg);
        formLayoutReg.addComponent(checkBoxEndnutzer);
        formLayoutReg.addComponent(checkBoxVertriebler);

        formLayoutReg.addComponent(registrieren);
        tabRechts.addComponent(formLayoutReg);

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
