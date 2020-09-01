package org.gui.components;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import org.services.util.StylesheetUtil;

public class SchriftzugCarlook extends HorizontalLayout {

    public SchriftzugCarlook() {

        Label carlook = new Label("Carlook", ContentMode.HTML);
        carlook.addStyleName(StylesheetUtil.schriftzug);

    }
}
