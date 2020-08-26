package org.gui.components;

import com.vaadin.ui.VerticalLayout;

public class Footer extends VerticalLayout {

    public Footer () {
        this.addComponent(new FooterTop());
        this.addComponent(new FooterCopyright());
    }

}
