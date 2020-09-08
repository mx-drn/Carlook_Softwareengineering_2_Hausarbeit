package org.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.model.entity.Benutzer;
import org.services.util.ViewUtil;
import org.services.util.Whitespace;
import org.ui.MainUI;

public class MainVertriebler extends VerticalLayout implements View {
    Whitespace whitespace = new Whitespace();

    public void enter (ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        Benutzer benutzer =  ((MainUI) UI.getCurrent()).getBenutzer();

        if (benutzer.getRolle().equals("Vertriebler")) {
            this.setUp();
        }else{
            UI.getCurrent().getNavigator().navigateTo(ViewUtil.MAIN);
        }
    }

    public void setUp () {
        Label label = new Label("Hi Tim, do you like violence? YEah Yeah Yeah!!!!!");
        addComponent(label);
    }
}
