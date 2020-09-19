package org.gui.components;

import com.vaadin.event.MouseEvents;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.model.entity.Benutzer;
import org.services.util.Rolle;
import org.services.util.StylesheetUtil;
import org.services.util.ViewUtil;
import org.services.util.Whitespace;
import org.ui.MainUI;

import java.io.File;

public class SchriftzugCarlook extends HorizontalLayout {

    public SchriftzugCarlook() {
        Benutzer benutzer = ((MainUI) UI.getCurrent()).getBenutzer();

        Label carlook = new Label("arlook", ContentMode.HTML);
        carlook.addStyleName(StylesheetUtil.schriftzug);

        ThemeResource resource = new ThemeResource("img/carlook_logo.png");
        Image logo = new Image(null, resource);
        logo.setWidth("150px");
        logo.addClickListener(new MouseEvents.ClickListener() {
            @Override
            public void click(MouseEvents.ClickEvent clickEvent) {
                if(benutzer != null) {
                    if (benutzer.getRolle().equals(Rolle.ENDNUTZER)) {
                       UI.getCurrent().getNavigator().navigateTo(ViewUtil.VIEWENDNNUTZER);
                    }else if(benutzer.getRolle().equals(Rolle.VERTRIEBLER)) {
                        UI.getCurrent().getNavigator().navigateTo(ViewUtil.VIEWVERTRIEBLER);
                    }
                }else UI.getCurrent().getNavigator().navigateTo(ViewUtil.MAIN);
            }
        });

        addComponent(logo);
        addComponent(carlook);
    }
}
