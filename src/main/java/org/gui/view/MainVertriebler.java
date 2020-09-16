package org.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.ItemClickListener;
import org.control.AngebotErstellenControl;
import org.gui.components.Footer;
import org.gui.components.SchriftzugCarlook;
import org.gui.components.TopPanel;
import org.model.entity.Auto;
import org.model.entity.Benutzer;
import org.services.util.StylesheetUtil;
import org.services.util.ViewUtil;
import org.services.util.Whitespace;
import org.ui.MainUI;

import java.util.ArrayList;

public class MainVertriebler extends VerticalLayout implements View {
    private Whitespace whitespace = new Whitespace();
    private Auto autoSelektiert;

    public void enter (ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        Benutzer benutzer =  ((MainUI) UI.getCurrent()).getBenutzer();

        if (benutzer.getRolle().equals("Vertriebler")) {
            this.setUp();
        }else{
            UI.getCurrent().getNavigator().navigateTo(ViewUtil.MAIN);
        }
    }

    public void setUp () {
        setStyleName(StylesheetUtil.angemeldetBg);

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

        Label label = new Label("Bieten Sie ein neues Auto zum erwerb an.");

        //Automarke
        TextField automarke = new TextField("Marke des Autos");
        automarke.setDescription("Marke");

        //Baujahr
        TextField baujahr = new TextField("Baujahr des Autos");
        baujahr.setDescription("Baujahr");

        //Beischreibung
        TextField beschreibung = new TextField("Beschreibung des Autos");
        beschreibung.setDescription("Beschreibung");

        //Button angebot erstelen
        Button autoAnbieten = new Button("Auto anbieten");

        autoAnbieten.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                if(!automarke.isEmpty() && !baujahr.isEmpty() && !beschreibung.isEmpty()) {
                    Auto auto = new Auto();
                    auto.setMarke(automarke.getValue());
                    auto.setBaujahr(Integer.parseInt(baujahr.getValue()));
                    auto.setBeschreibung(beschreibung.getValue());

                    AngebotErstellenControl.angebotErstellen(auto);
                    Notification.show("Auto erfolgreich hinzugefügt!", Notification.Type.HUMANIZED_MESSAGE);
                    automarke.setValue("");
                    baujahr.setValue("");
                    beschreibung.setValue("");

                }else{
                    Notification.show("Sie müssen alle Felder ausfüllen!", Notification.Type.ERROR_MESSAGE);
                }
            }
        });

        tabLinks.addComponent(label);
        tabLinks.addComponent(automarke);
        tabLinks.addComponent(baujahr);
        tabLinks.addComponent(beschreibung);
        tabLinks.addComponent(autoAnbieten);

        tabSheet.addTab(tabLinks, "Auto anbieten");

        //Angebot entfernen Button
        Button angebotEntfernen = new Button("Ausgewähltes Angebot entfernen.");
        angebotEntfernen.setStyleName(StylesheetUtil.reservierungAufheben);

        angebotEntfernen.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                AngebotErstellenControl.angebotLöschen(autoSelektiert);
            }
        });

        //Falls noch keine Autos erstellt wurden
        Label nochNichtsErstellt = new Label("Sie haben noch keine Autos eingestellt.");

        //Grid für Anzeige aller Angebote
        Grid<Auto> gridAlleAngebote = new Grid<>(Auto.class);
        gridAlleAngebote.setSizeFull();
        gridAlleAngebote.setWidth("62%");

        tabSheet.addSelectedTabChangeListener(new TabSheet.SelectedTabChangeListener() {
            @Override
            public void selectedTabChange(TabSheet.SelectedTabChangeEvent selectedTabChangeEvent) {
                if (tabSheet.getSelectedTab() == tabRechts) {
                    //Autos für das Alle-Angebote-Anzeigen-Grid
                    ArrayList<Auto> angebote = AngebotErstellenControl.getAlleAngebote();

                    if (!angebote.isEmpty()) {
                        tabRechts.addComponent(gridAlleAngebote);
                        tabRechts.setComponentAlignment(gridAlleAngebote, Alignment.MIDDLE_CENTER);

                        tabRechts.removeComponent(nochNichtsErstellt);

                        //Auto löschen Button
                        tabRechts.addComponent(angebotEntfernen);
                        tabRechts.setComponentAlignment(angebotEntfernen, Alignment.MIDDLE_CENTER);

                        gridAlleAngebote.removeAllColumns();
                        gridAlleAngebote.setCaption("Deine Reservierungen:");

                        gridAlleAngebote.setItems(angebote);
                        gridAlleAngebote.addColumn(Auto::getMarke).setCaption("Marke");
                        gridAlleAngebote.addColumn(Auto::getBaujahr).setCaption("Baujahr");
                        gridAlleAngebote.addColumn(Auto::getBeschreibung).setCaption("Beschreibung");

                        gridAlleAngebote.addItemClickListener(new ItemClickListener<Auto>() {
                            @Override
                            public void itemClick(Grid.ItemClick<Auto> itemClick) {
                                autoSelektiert = itemClick.getItem();
                            }
                        });
                    }

                }
            }
        });

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
