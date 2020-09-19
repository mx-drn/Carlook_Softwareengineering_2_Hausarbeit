package org.ui;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import org.gui.view.*;
import org.services.util.ViewUtil;
import org.model.entity.Benutzer;


/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Title("Carlook")
@PreserveOnRefresh
public class MainUI extends UI {

    private Benutzer benutzer = null;

    public MainUI getMainUI() {
        return (MainUI) UI.getCurrent();
    }

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
    }

    public Benutzer getBenutzer() {
        return benutzer;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Navigator navi = new Navigator(this, this);
        navi.addView(ViewUtil.MAIN, Startseite.class);
        navi.addView(ViewUtil.VIEWENDNNUTZER, MainEndnutzer.class);
        navi.addView(ViewUtil.VIEWVERTRIEBLER, MainVertriebler.class);
        navi.addView(ViewUtil.KONTAKTDUMMY, Kontakt.class);
        navi.addView(ViewUtil.IMPRESSUMDUMMY, Impressum.class);
        navi.addView(ViewUtil.DATENSCHUTZDUMMY, Datenschutz.class);
        navi.addView(ViewUtil.FAQDUMMY, Faq.class);

        UI.getCurrent().getNavigator().navigateTo(ViewUtil.MAIN);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
