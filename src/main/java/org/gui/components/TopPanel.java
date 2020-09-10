package org.gui.components;

import com.vaadin.event.MouseEvents;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.model.entity.Benutzer;
import org.services.util.Rolle;
import org.services.util.StylesheetUtil;
import org.services.util.ViewUtil;
import org.ui.MainUI;

public class TopPanel extends HorizontalLayout {

    public TopPanel() {

        Benutzer benutzer = ((MainUI) UI.getCurrent()).getBenutzer();

        this.setMargin(true);
        this.addStyleName(StylesheetUtil.toppanel);
        this.setWidth("100%");

        HorizontalLayout menuBarHorLayout = new HorizontalLayout();

        // Menubar erzeugen

        if(benutzer != null) {

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

            menuBarHorLayout.addComponent(menuBar);
            addComponent(menuBarHorLayout);
            setComponentAlignment(menuBarHorLayout, Alignment.MIDDLE_RIGHT);
        }
    }

}
