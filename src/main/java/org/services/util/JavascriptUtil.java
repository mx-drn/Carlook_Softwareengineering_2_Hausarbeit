package org.services.util;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;

public class JavascriptUtil {
    private JavascriptUtil(){}
    /**
     *
     * @param id Element das fokusiert wird (funktioniert zumindest bei Textfeldern)
     */
    public static void auswahl ( String id) {
        JavaScript.getCurrent().execute(
                "let input = document.getElementById('"+id+"');" +
                        "input.focus();" +
                        "input.click();"
        );
    }

    public static void setCookieUser ( String benutzername) {
        JavaScript.getCurrent().execute(
                "document.cookie = \"usernamealdanativlos="+benutzername+"\";"
        );
    }

    //Javascript getCookie funktioniert nicht...
    public static void getCookieUser(){
        JavaScript.getCurrent().execute(
                "var x = document.cookie;" +
                        "var i= x.indexOf('usernamealdanativlos')+21;" +
                        "if (i>21) {"+
                        "document.getElementById('Benutzername').value=x.substr(i).split(';')[0];"+
                        "}"
        );
    }

}
