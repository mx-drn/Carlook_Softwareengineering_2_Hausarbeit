package org.gui.components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

public class Footer extends VerticalLayout {

    public Footer () {
        FooterTop footerTop = new FooterTop();
        this.addComponent(footerTop);
        this.setComponentAlignment(footerTop, Alignment.MIDDLE_CENTER);

        FooterCopyright footerCopyright = new FooterCopyright();
        this.addComponent(footerCopyright);
        this.setComponentAlignment(footerCopyright, Alignment.MIDDLE_CENTER);
    }

}
