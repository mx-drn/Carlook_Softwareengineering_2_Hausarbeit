package org.gui.components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;
import org.services.util.StylesheetUtil;
import org.services.util.Whitespace;

public class Footer extends VerticalLayout {

    public Footer () {
        Whitespace whitespace = new Whitespace();

        addComponent(whitespace.getWhitespace());
        addComponent(whitespace.getWhitespace());

        FooterTop footerTop = new FooterTop();
        this.addComponent(footerTop);
        this.setComponentAlignment(footerTop, Alignment.BOTTOM_CENTER);

        FooterCopyright footerCopyright = new FooterCopyright();
        this.addComponent(footerCopyright);
        footerCopyright.setStyleName(StylesheetUtil.footer);
        this.setComponentAlignment(footerCopyright, Alignment.BOTTOM_CENTER);
    }

}
