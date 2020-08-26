package org.gui.components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

public class FooterTop extends HorizontalLayout {
    Button faq = new Button("FAQ");
    Button kontakt = new Button("Kontakt");
    Button impressum = new Button("Impressum");
    Button datenschutz = new Button("Datenschutz");

    public FooterTop () {
        this.addComponent(faq);
        this.addComponent(kontakt);
        this.addComponent(impressum);
        this.addComponent(datenschutz);
    }
}
