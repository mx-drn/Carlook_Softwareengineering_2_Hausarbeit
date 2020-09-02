package org.services.util;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import org.gui.components.SchriftzugCarlook;

public class StylesheetUtil {
    public static final String toppanel = "topPanel";
    public static final String login = "login";
    public static final String grunddesign = "grunddesign";
    public static final String STYLE_BACKGROUND_RED = "error-field ";
    public static final String schriftzug = "schriftzug_carlook";
    public static final String tabsheet = "tabsheet";

    public static void loadBackground(Layout layout, String name) {
        layout.addComponent(new Label("<link rel=\"stylesheet\" href=\"./VAADIN/myStyles/background.css\">",
                ContentMode.HTML));
        layout.setId(name);
    }

}
