package org.services.util;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class Whitespace {

    public Whitespace() {

    }

    public Label getWhitespace() {
        return new Label("&nbsp", ContentMode.HTML);
    }

    public VerticalLayout getFooterWhitespace() {
        VerticalLayout verticalLayout = new VerticalLayout();

        for(int i = 0; i <= 6; i++) {
            verticalLayout.addComponent(getWhitespace());
        }

        return verticalLayout;
    }

}
