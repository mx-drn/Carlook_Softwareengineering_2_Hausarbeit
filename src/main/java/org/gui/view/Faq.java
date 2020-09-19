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

public class Faq extends VerticalLayout implements View {
        Whitespace whitespace = new Whitespace();

        public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
            this.setUp();
        }

        public void setUp() {
                //Erstellen des Hintergrundes
                setStyleName("bg");

                SchriftzugCarlook schriftzugCarlook = new SchriftzugCarlook();
                addComponent(schriftzugCarlook);
                setComponentAlignment(schriftzugCarlook, Alignment.TOP_CENTER);
                schriftzugCarlook.setStyleName(StylesheetUtil.schriftzug);

                addComponent(whitespace.getWhitespace());
                addComponent(whitespace.getWhitespace());

                Label faq = new Label("<p>Hier wird bald ein ausf&uuml;hrlicher FAQ f&uuml;r Sie bereit stehen.</p>\n" +
                        "<p>Das Carlook.de Team arbeitet auf Hochtouren um diesen schnellstm&ouml;glich bereit zu stellen!</p>\n" +
                        "<p>Vielen Dank f&uuml;r Ihre Geduld.</p>\n" +
                        "<p>&nbsp;</p>\n" +
                        "<p>Das Carlook.de - Deutschland Team :)</p>" , ContentMode.HTML);
                Panel panel = new Panel("FAQ");
                panel.setContent(faq);

                addComponent(panel);
                panel.setWidth(800, Unit.PIXELS);
                panel.setHeight(400, Unit.PIXELS);
                setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

                addComponent(whitespace.getWhitespace());

                Footer footer = new Footer();
                addComponent(footer);
                setComponentAlignment(footer, Alignment.BOTTOM_CENTER);
        }
}
