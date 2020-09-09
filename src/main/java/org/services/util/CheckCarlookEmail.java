package org.services.util;

public class CheckCarlookEmail {

    public static boolean istCarlookEmail(String email) {
        String nachAt = email.substring(email.indexOf("@")+1);

        return nachAt.equals("carlook.de");
    }

}
