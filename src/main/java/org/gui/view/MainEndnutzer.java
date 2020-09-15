package org.gui.view;

import com.vaadin.data.HasValue;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.ItemClickListener;
import org.control.RegistrierungsControl;
import org.control.ReservierungsControl;
import org.control.SucheControl;
import org.control.exception.DataBaseException;
import org.gui.components.Footer;
import org.gui.components.SchriftzugCarlook;
import org.gui.components.TopPanel;
import org.model.entity.Auto;
import org.model.entity.Benutzer;
import org.model.entity.Reservierung;
import org.services.util.Rolle;
import org.services.util.StylesheetUtil;
import org.services.util.ViewUtil;
import org.services.util.Whitespace;
import org.ui.MainUI;

import java.util.ArrayList;

public class MainEndnutzer extends VerticalLayout implements View {
    Whitespace whitespace = new Whitespace();
    private String request;
    private SucheControl sucheControl = new SucheControl();
    private org.model.entity.Auto autoSelektiert;

    public void enter (ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        Benutzer benutzer =  ((MainUI) UI.getCurrent()).getBenutzer();

        if (benutzer.getRolle().equals("Endnutzer")) {
            this.setUp();
        }else{
            UI.getCurrent().getNavigator().navigateTo(ViewUtil.MAIN);
        }

    }

    public void setUp () {
        setStyleName(StylesheetUtil.angemeldetBg);

        Benutzer benutzer = ((MainUI) UI.getCurrent()).getBenutzer();

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

        HorizontalLayout sucheHori = new HorizontalLayout();

        // Textfield für Suche
        TextField suchfeld = new TextField();
        suchfeld.setSizeFull();
        suchfeld.setWidth(800, Unit.PIXELS);
        suchfeld.setHeight(60, Unit.PIXELS);
        suchfeld.setPlaceholder("Suche...");
        sucheHori.addComponents(suchfeld, new Label("&nbsp", ContentMode.HTML));

        //Grid
        Grid<Auto> grid = new Grid<>(Auto.class);
        grid.setSizeFull();
        grid.setWidth("62%");

        //Suchebutton
        Button sucheButton = new Button(FontAwesome.SEARCH);
        //butSucheStudent.addStyleName(StyleSheetUtil.STYLE_SELECTED);
        sucheButton.setWidth(100, Unit.PIXELS);
        sucheButton.setHeight(60, Unit.PIXELS);

        sucheHori.addComponent(sucheButton);
        sucheHori.setComponentAlignment(suchfeld, Alignment.MIDDLE_CENTER);
        sucheHori.setComponentAlignment(sucheButton, Alignment.MIDDLE_CENTER);

        //Reservierungsbutton
        Button reservieren = new Button("Reservieren");

        Button.ClickListener reservierungsListener = new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if(autoSelektiert != null) {
                    ReservierungsControl.reserviereAuto(autoSelektiert);
                }else{
                    Notification.show("Sie müssen ein Auto auswählen um es reservieren zu können.", Notification.Type.WARNING_MESSAGE);
                }
            }
        };

        addComponent(sucheHori);
        setComponentAlignment(sucheHori, Alignment.MIDDLE_CENTER);

        //Anzeigen der Reservierungen und Löschen
        Grid<Auto> gridReservierung = new Grid<>(Auto.class);
        grid.setSizeFull();
        grid.setWidth("62%");

        ArrayList<Auto> reservierungen = new ArrayList<>();
        reservierungen = ReservierungsControl.getAllReservierungen();
        grid.setCaption("Deine Reservierungen:");
        grid.setItems(reservierungen);
        grid.addColumn(org.model.entity.Auto::getMarke).setCaption("Marke");
        grid.addColumn(org.model.entity.Auto::getBaujahr).setCaption("Baujahr");
        grid.addColumn(org.model.entity.Auto::getBeschreibung).setCaption("Beschreibung");


        tabLinks.addComponent(sucheHori);
        tabSheet.addTab(tabLinks, "Autosuche");
        tabSheet.addTab(tabRechts, "Meine Reservierungen");
        setComponentAlignment(tabSheet, Alignment.MIDDLE_CENTER);
        tabSheet.setWidth("60%");

        addComponent(whitespace.getWhitespace());
        addComponent(whitespace.getWhitespace());

        Footer footer = new Footer();
        addComponent(footer);
        setComponentAlignment(footer, Alignment.MIDDLE_CENTER);


        suchfeld.addBlurListener(new FieldEvents.BlurListener() {
            @Override
            public void blur(FieldEvents.BlurEvent blurEvent) {
                try {
                    sucheControl.closeConAuto();
                } catch (DataBaseException e) {
                    e.printStackTrace();
                }
            }
        });

        suchfeld.addValueChangeListener(new HasValue.ValueChangeListener<String>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<String> valueChangeEvent) {
                if (suchfeld.getValue().equals("")) {
                    Notification.show("Sie haben keinen Suchbegriff eingegeben!");
                    grid.removeAllColumns();
                }else{
                    request = suchfeld.getValue();

                    //Footer entfernen für richtige Theming
                    removeComponent(footer);
                    addComponent(footer);

                    grid.setCaption("Deine Suchanfrage hat folgende Ergebnisse geliefert:");

                    removeComponent(grid);
                    addComponent(grid);
                    setComponentAlignment(grid, Alignment.MIDDLE_CENTER);

                    ArrayList<Auto> daten = null;

                    //Proxy Zugriff
                    try {
                        daten = sucheControl.getAutoBySuche(request);
                    } catch (DataBaseException e) {
                        e.printStackTrace();
                    }

                    grid.removeAllColumns();

                    if(daten==null || daten.size() == 0){
                        Notification.show("Suchanfrage hat keine Autos geliefert.", Notification.Type.HUMANIZED_MESSAGE);
                    }else{
                        //Grid befüllen
                        grid.setItems(daten);
                        grid.addColumn(org.model.entity.Auto::getMarke).setCaption("Marke");
                        grid.addColumn(org.model.entity.Auto::getBaujahr).setCaption("Baujahr");
                        grid.addColumn(org.model.entity.Auto::getBeschreibung).setCaption("Beschreibung");
                    }

                    grid.addItemClickListener(new ItemClickListener<org.model.entity.Auto>() {
                        @Override
                        public void itemClick(Grid.ItemClick<org.model.entity.Auto> itemClick) {
                            autoSelektiert = itemClick.getItem();
                        }
                    });

                    if(benutzer.getRolle().equals(Rolle.ENDNUTZER) ) {
                        reservieren.removeClickListener(reservierungsListener);
                        reservieren.addClickListener(reservierungsListener);
                        removeComponent(reservieren);
                        addComponents(reservieren);
                        setComponentAlignment(reservieren, Alignment.MIDDLE_CENTER);
                    }


                    //Footer wieder hinzufügen
                    addComponent(footer);
                    setComponentAlignment(footer, Alignment.BOTTOM_CENTER);
                }
            }
        });


        tabSheet.addSelectedTabChangeListener(new TabSheet.SelectedTabChangeListener() {
            @Override
            public void selectedTabChange(TabSheet.SelectedTabChangeEvent selectedTabChangeEvent) {
                removeComponent(grid);
                removeComponent(reservieren);
                addComponent(footer);
            }
        });
    }

}
