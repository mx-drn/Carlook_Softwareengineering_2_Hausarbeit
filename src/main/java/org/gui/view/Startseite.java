package org.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class Startseite implements View {

    public void enter (ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        this.setUp();
    }

    public void setUp () {
        HorizontalLayout horiMain = new HorizontalLayout();
        VerticalLayout vertMain = new VerticalLayout();

        VerticalLayout verticalMid = new VerticalLayout();
        verticalMid.setWidth("62%"); //circa Goldener Schnitt

        //E-Mail Adresse für den Login
        TextField email = new TextField("E-Mail");
        email.setValue("Ihre E-Mail Adresse");
        verticalMid.addComponent(email);

        //Passwort
        TextField passwort = new TextField("Passwort");
        passwort.setValue("Ihr Passwort");
        verticalMid.addComponent(passwort);




    }
}
