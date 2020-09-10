package org.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.gui.components.Footer;
import org.gui.components.SchriftzugCarlook;
import org.gui.components.TopPanel;
import org.model.entity.Benutzer;
import org.services.util.StylesheetUtil;
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
        //Logo und Titel einbinden
        SchriftzugCarlook schriftzugCarlook = new SchriftzugCarlook();
        addComponent(schriftzugCarlook);
        setComponentAlignment(schriftzugCarlook, Alignment.TOP_CENTER);
        schriftzugCarlook.setStyleName(StylesheetUtil.schriftzug);

        TopPanel topPanel = new TopPanel();
        addComponent(topPanel);
        setComponentAlignment(topPanel, Alignment.MIDDLE_CENTER);

        //Tabsheet zur Auswahl von Autosuche/Reservierungen verwalten
        TabSheet tabSheet = new TabSheet();
        addComponent(tabSheet);

        VerticalLayout tabLinks = new VerticalLayout();
        VerticalLayout tabRechts = new VerticalLayout();



        tabSheet.addTab(tabLinks, "Auto anbieten");
        tabSheet.addTab(tabRechts, "Meine Angebote");
        setComponentAlignment(tabSheet, Alignment.MIDDLE_CENTER);
        tabSheet.setWidth("60%");

        addComponent(whitespace.getWhitespace());
        addComponent(whitespace.getWhitespace());

        Footer footer = new Footer();
        addComponent(footer);
        setComponentAlignment(footer, Alignment.MIDDLE_CENTER);

    }
}
