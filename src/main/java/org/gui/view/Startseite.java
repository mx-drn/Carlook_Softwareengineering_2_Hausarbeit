package org.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.control.LoginControl;
import org.control.exception.NoSuchUserOrPasswordException;
import org.gui.components.Footer;
import org.gui.components.SchriftzugCarlook;
import org.gui.components.TopPanel;
import org.services.util.StylesheetUtil;
import org.services.util.Whitespace;

import java.util.HashMap;
import java.util.Map;

public class Startseite extends VerticalLayout implements View {
    Whitespace whitespace = new Whitespace();

    public void enter (ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        this.setUp();
    }

    public void setUp () {
        //Erstellen des Hintergrundes
        //StylesheetUtil.loadBackground(this, "startseite_bg");
        setStyleName("bg");
        setSizeFull();

        SchriftzugCarlook schriftzugCarlook = new SchriftzugCarlook();
        addComponent(schriftzugCarlook);
        setComponentAlignment(schriftzugCarlook, Alignment.TOP_CENTER);
        schriftzugCarlook.setStyleName(StylesheetUtil.schriftzug);

        addComponent(whitespace.getWhitespace());
/*
        //Erstellen Grunddesign
        HorizontalLayout horiMain = new HorizontalLayout();
        horiMain.setStyleName(StylesheetUtil.grunddesign);

*/

        //Tabsheet für das wechseln zwischen Login und Registrierung

        TabSheet tabSheet = new TabSheet();
        addComponent(tabSheet);
        VerticalLayout tabLinks = new VerticalLayout();
        VerticalLayout tabRechts = new VerticalLayout();

        //E-Mail Adresse für den Login
        TextField email = new TextField("E-Mail");
        email.setDescription("Ihre E-Mail Adresse");


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

        tabLinks.addComponent(email);
        tabLinks.addComponent(horizontalLayoutLogin);
        tabLinks.setMargin(true);

        Label registriertfrage = new Label("Noch nicht registriert?");

        Button registrieren = new Button("Registrieren");

        registrieren.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                    //Verlinkung auf registrierungsseite navigateTo()
            }
        });

        tabRechts.addComponent(registriertfrage);
        tabRechts.addComponent(registrieren);
        tabLinks.setStyleName(StylesheetUtil.tabsheet);
        tabRechts.setStyleName(StylesheetUtil.tabsheet);

        tabSheet.addTab(tabLinks, "Login");
        tabSheet.addTab(tabRechts, "Registrieren");
        setComponentAlignment(tabSheet, Alignment.MIDDLE_CENTER);


        addComponent(whitespace.getWhitespace());
        addComponent(whitespace.getWhitespace());

        Footer footer = new Footer();
        addComponent(footer);
        setComponentAlignment(footer, Alignment.MIDDLE_CENTER);

    }
}
