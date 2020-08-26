package org.gui.components;

import com.vaadin.event.MouseEvents;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.model.entity.Benutzer;
import org.services.util.Rolle;
import org.services.util.ViewUtil;
import org.ui.MainUI;

public class TopPanel extends HorizontalLayout {

    public TopPanel() {

        Benutzer benutzer = ((MainUI) UI.getCurrent()).getBenutzer();

        this.setMargin(true);
        //this.addStyleName(StylesheetUtil.BLA);
        this.setWidth("100%");


        HorizontalLayout mainHorLayout = new HorizontalLayout();
        HorizontalLayout menuBarHorLayout = new HorizontalLayout();

        //Logo und Titel einbinden
        ThemeResource resource = new ThemeResource("img/HBRS_logo.jpg");
        Image logo = new Image(null, resource);
        logo.setWidth("150px");
        logo.addClickListener(new MouseEvents.ClickListener() {
            @Override
            public void click(MouseEvents.ClickEvent clickEvent) {
                if (benutzer.getRolle().equals(Rolle.ENDNUTZER)) {
                    UI.getCurrent().getNavigator().navigateTo(ViewUtil.VIEWENDNNUTZER);
                }else if (benutzer.getRolle().equals(Rolle.VERTRIEBLER)) {
                    UI.getCurrent().getNavigator().navigateTo(ViewUtil.VIEWVERTRIEBLER);
                }
            }
        });

        Label carlook = new Label("Carlook", ContentMode.HTML);
        carlook.addStyleName("carlook");

        mainHorLayout.addComponent(logo);
        mainHorLayout.addComponent(carlook);mainHorLayout.setComponentAlignment(logo, Alignment.MIDDLE_LEFT);
        mainHorLayout.setComponentAlignment(carlook, Alignment.MIDDLE_LEFT);
        this.addComponent(mainHorLayout);

        // Menubar erzeugen
        MenuBar menuBar = new MenuBar();

        MenuBar.MenuItem logout = menuBar.addItem("Logout", FontAwesome.SIGN_OUT, new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                // Navigation zu MainView
                UI.getCurrent().close();
                UI.getCurrent().getNavigator().navigateTo(ViewUtil.MAIN);
                UI.getCurrent().getPage().reload();
            }
        });

        addComponent(menuBarHorLayout);
        setComponentAlignment(menuBarHorLayout, Alignment.MIDDLE_RIGHT);
    }

}
