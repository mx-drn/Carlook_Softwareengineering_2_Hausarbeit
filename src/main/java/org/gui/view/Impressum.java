package org.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import org.gui.components.Footer;
import org.gui.components.SchriftzugCarlook;
import org.services.util.StylesheetUtil;
import org.services.util.Whitespace;

public class Impressum extends VerticalLayout implements View {
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

                Label impressum = new Label("<h3>Zentrale Adresse</h3>\n" +
                        "<p>Carlook<br /> Carlook-Stra&szlig;e 20<br /> 53639 K&ouml;nigswinter (Germany)<br /> Tel +49 2244 12345(interne Durchwahl -678)<br /> Fax +49 2244 12334455</p>\n" +
                        "<p>Umsatzsteueridentnummer von Carlook gem&auml;&szlig; &sect; 27a Umsatzsteuergesetz: DE 123456789</p>\n" +
                        "<p>Die&nbsp;<strong>Carlook GmbH</strong>&nbsp;ist eine K&ouml;rperschaft des &ouml;ffentlichen Rechts. Sie wird durch Tim Fuhrmann und Max D&uuml;ren gesetzlich vertreten. E-Mail: geschaeftsfuehrung(at)carlook.de</p>\n" +
                        "<h3>Aufsichtsbeh&ouml;rde</h3>\n" +
                        "<p>Ministerium f&uuml;r Autos und weitere KfZ des Landes Nordrhein-Westfalen (MAKfZ), Auto Stra&szlig;e 8, 50667 K&ouml;ln</p>\n" +
                        "<p>Inhaltlich Verantwortlicher gem&auml;&szlig; &sect; 5 des Telemediengesetz (TMG) und &sect; 55 Abs, 2 des Rundfunkstaatsvertrages (RStV): Ekelhardt Ekel&nbsp;<strong>Leitung Kommunikation und Marketing</strong>.</p>", ContentMode.HTML);
                Panel panel = new Panel("Impressum");
                panel.setContent(impressum);

                addComponent(panel);
                panel.setWidth("60%");
                setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

                addComponent(whitespace.getWhitespace());

                Footer footer = new Footer();
                addComponent(footer);
                setComponentAlignment(footer, Alignment.BOTTOM_CENTER);

        }
}
