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

public class Datenschutz extends VerticalLayout implements View {
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

                Label datenschutz = new Label("<p>Lorem ipsum dolor sit amet, consectetur adipisici elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua.</p>\n" +
                        "<p>&nbsp;Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquid ex ea commodi consequat. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.&nbsp;</p>\n" +
                        "<p>Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.&nbsp;</p>" , ContentMode.HTML);

                Panel panel = new Panel("Datenschutz");
                panel.setContent(datenschutz);

                addComponent(panel);
                panel.setWidth("60%");
                setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

                addComponent(whitespace.getWhitespace());

                Footer footer = new Footer();
                addComponent(footer);
                setComponentAlignment(footer, Alignment.MIDDLE_CENTER);
        }
}
