package org.services.util;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;

public class Whitespace {

    public Whitespace() {

    }

    public Label getWhitespace() {
        return new Label("&nbsp", ContentMode.HTML);
    }

}
