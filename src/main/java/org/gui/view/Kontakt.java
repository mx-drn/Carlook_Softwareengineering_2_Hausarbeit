package org.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.gui.components.Footer;
import org.gui.components.SchriftzugCarlook;
import org.services.util.StylesheetUtil;
import org.services.util.Whitespace;

public class Kontakt extends VerticalLayout implements View {
        Whitespace whitespace = new Whitespace();

        public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
            this.setUp();
        }

        public void setUp() {
            //Erstellen des Hintergrundes
            setStyleName("bg");
            setSizeFull();

            SchriftzugCarlook schriftzugCarlook = new SchriftzugCarlook();
            addComponent(schriftzugCarlook);
            setComponentAlignment(schriftzugCarlook, Alignment.TOP_CENTER);
            schriftzugCarlook.setStyleName(StylesheetUtil.schriftzug);

            addComponent(whitespace.getWhitespace());
            addComponent(whitespace.getWhitespace());

            Label kontakt = new Label("<p><strong>Zentrale Adresse</strong></p>\n" +
                    "<p>Carlook<br /> Carlook-Stra&szlig;e 20<br /> 53639 K&ouml;nigswinter (Germany)<br /> Tel +49 2244 12345(interne Durchwahl -678)<br /> Fax +49 2244 12334455</p>", ContentMode.HTML);
            Panel panel = new Panel("Kontakt");
            panel.setContent(kontakt);

            addComponent(panel);
            panel.setWidth("60%");
            setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

            addComponent(whitespace.getWhitespace());


            Footer footer = new Footer();
            addComponent(footer);
            setComponentAlignment(footer, Alignment.MIDDLE_CENTER);
        }
}
