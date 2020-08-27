package org.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.gui.components.Footer;
import org.gui.components.TopPanel;

public class Startseite extends VerticalLayout implements View {
    private Label whitespace = new Label("&nbsp", ContentMode.HTML);

    public void enter (ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        this.setUp();
    }

    public void setUp () {
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

        horiMain.addComponent(verticalMid);
        horiMain.addComponent(verticalMidRight);

        addComponent(horiMain);
        setComponentAlignment(horiMain, Alignment.MIDDLE_CENTER);

        Footer footer = new Footer();
        addComponent(footer);
        setComponentAlignment(footer, Alignment.MIDDLE_CENTER);

    }
}
