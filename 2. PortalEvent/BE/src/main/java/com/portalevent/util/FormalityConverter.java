package com.portalevent.util;

import com.portalevent.infrastructure.constant.Formality;

/**
 * @author SonPT
 */
public class FormalityConverter {

    public static String ConvertToString(Formality formality) {
        String formalityStr = "";
        switch (formality) {
            case ONLINE -> formalityStr = "Online";
            case OFFLINE -> formalityStr = "Offline";
        }
        return formalityStr;
    }

}
