package org.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.control.LoginControl;
import org.control.exception.NoSuchUserOrPasswordException;
import org.gui.components.Footer;
import org.gui.components.TopPanel;
import org.services.util.StylesheetUtil;

import java.util.HashMap;
import java.util.Map;

public class Startseite extends VerticalLayout implements View {
    private Label whitespace = new Label("&nbsp", ContentMode.HTML);

    public void enter (ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        this.setUp();
    }

    public void setUp () {
        this.setStyleName(StylesheetUtil.grunddesign);

        HorizontalLayout horiMain = new HorizontalLayout();

        VerticalLayout verticalMid = new VerticalLayout();
        verticalMid.setWidth("62%"); //circa Goldener Schnitt

        //E-Mail Adresse für den Login
        TextField email = new TextField("E-Mail");
        email.setDescription("Ihre E-Mail Adresse");
        verticalMid.addComponent(email);

        //Passwort
        HorizontalLayout horizontalLayoutLogin = new HorizontalLayout();
        TextField passwort = new TextField("Passwort");
        passwort.setDescription("Ihr Passwort");
        horizontalLayoutLogin.addComponent(passwort);

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
        horizontalLayoutLogin.addComponent(anmelden);

        verticalMid.addComponent(horizontalLayoutLogin);
        verticalMid.setMargin(true);

        //Registrieren feld
        VerticalLayout verticalMidRight = new VerticalLayout();

        Label registriertfrage = new Label("Noch nicht registriert?");

        Button registrieren = new Button("Registrieren");

        registrieren.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                    //Verlinkung auf registrierungsseite navigateTo()
            }
        });

        verticalMidRight.addComponent(registriertfrage);
        verticalMidRight.addComponent(whitespace);
        verticalMidRight.addComponent(whitespace);
        verticalMidRight.addComponent(registrieren);

        //Layouts zusammenfügen
        verticalMid.setStyleName(StylesheetUtil.login);
        verticalMidRight.setStyleName(StylesheetUtil.login);
        horiMain.addComponent(verticalMid);
        horiMain.addComponent(verticalMidRight);

        addComponent(horiMain);
        setComponentAlignment(horiMain, Alignment.MIDDLE_CENTER);

        Footer footer = new Footer();
        addComponent(footer);
        setComponentAlignment(footer, Alignment.MIDDLE_CENTER);

    }
}
