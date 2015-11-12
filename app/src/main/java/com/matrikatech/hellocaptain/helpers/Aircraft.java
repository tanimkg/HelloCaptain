package com.matrikatech.hellocaptain.helpers;

/**
 * Created by TANIM on 12-Mar-15.
 */
public class Aircraft {

    private String name, tailNo;
    private boolean isRotor, isMultiEng;

    public int isRotor() {
        return isRotor ? 1 : 0;
    }

    public void setRotor(boolean isRotor) {
        this.isRotor = isRotor;
    }

    public int isMultiEng() {
        return isMultiEng ? 1 : 0;
    }

    public void setMultiEng(boolean isMultiEng) {
        this.isMultiEng = isMultiEng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTailNo() {
        return tailNo;
    }

    public void setTailNo(String tailNo) {
        this.tailNo = tailNo;
    }


}
