package org.gui.components;

import com.vaadin.ui.*;

public class FooterCopyright extends HorizontalLayout {
    Label copyright = new Label("© 2020 Carlook All Rights Reserved");

    public FooterCopyright () {
        this.addComponent(copyright);
        copyright.setWidth("250%");
    }

}
