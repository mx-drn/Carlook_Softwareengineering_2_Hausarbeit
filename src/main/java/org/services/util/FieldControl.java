package org.services.util;

import com.vaadin.ui.AbstractField;
import org.control.exception.EmptyFieldsException;
import org.control.exception.RegistrierungException;

import java.util.Map;
import java.util.regex.Pattern;

public class FieldControl {

    public static void   checkEmptyFields (Map<String, AbstractField> textFields) throws RegistrierungException {
        String fields = textFields.entrySet().stream()
                .map (x-> x.getValue())
                .peek(x -> {
                    if(x.getValue().toString().length()>0)
                        x.removeStyleName(StylesheetUtil.STYLE_BACKGROUND_RED);
                    else
                        x.addStyleName(StylesheetUtil.STYLE_BACKGROUND_RED);
                })
                .filter(x-> x.getValue().toString().length()==0)
                .map(x -> x.getId()+"\n")
                .reduce("",String::concat);
        // System.out.println(" leere Felder:"+fields);
        if(fields.length()>0) {
            String reason =fields + " sind nicht ausgefüllt!";
            throw new EmptyFieldsException(reason);
        }
    }

}
