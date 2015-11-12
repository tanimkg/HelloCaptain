package com.matrikatech.hellocaptain.helpers;

/**
 * Created by TANIM on 13-Mar-15.
 */
public class StringUtils {

    public String getNullSafeString(String text) {
        return text == null ? "" : text;
    }
}
