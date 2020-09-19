package org.gui.components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import org.services.util.ViewUtil;

public class FooterTop extends HorizontalLayout {
    Button faq = new Button("FAQ");
    Button kontakt = new Button("Kontakt");
    Button impressum = new Button("Impressum");
    Button datenschutz = new Button("Datenschutz");

    public FooterTop () {
        this.addComponent(faq);
        faq.setWidth(100, Unit.PIXELS);
        faq.setHeight(40, Unit.PIXELS);

        this.addComponent(kontakt);
        kontakt.setWidth(100, Unit.PIXELS);
        kontakt.setHeight(40, Unit.PIXELS);

        this.addComponent(impressum);
        impressum.setWidth(100, Unit.PIXELS);
        impressum.setHeight(40, Unit.PIXELS);

        this.addComponent(datenschutz);
        datenschutz.setWidth(100, Unit.PIXELS);
        datenschutz.setHeight(40, Unit.PIXELS);


        faq.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().getNavigator().navigateTo(ViewUtil.FAQDUMMY);
            }
        });

        kontakt.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().getNavigator().navigateTo(ViewUtil.KONTAKTDUMMY);
            }
        });

        impressum.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().getNavigator().navigateTo(ViewUtil.IMPRESSUMDUMMY);
            }
        });

        datenschutz.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().getNavigator().navigateTo(ViewUtil.DATENSCHUTZDUMMY);
            }
        });
    }
}
