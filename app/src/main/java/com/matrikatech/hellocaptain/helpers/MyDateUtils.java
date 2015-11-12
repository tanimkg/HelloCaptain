package com.matrikatech.hellocaptain.helpers;

/**
 * Created by TANIM on 17-Mar-15.
 */
public class MyDateUtils {

    public static final int JAN = 1;
    public static final int FEB = 2;
    public static final int MAR = 3;
    public static final int APR = 4;
    public static final int MAY = 5;
    public static final int JUN = 6;
    public static final int JUL = 7;
    public static final int AUG = 8;
    public static final int SEP = 9;
    public static final int OCT = 10;
    public static final int NOV = 11;
    public static final int DEC = 12;

    public String getMonthNameFromNo(int monthNo) {
        String retStr;
        switch (monthNo) {
            case JAN:
                retStr = "Jan";
                break;
            case FEB:
                retStr = "Feb";
                break;
            case MAR:
                retStr = "Mar";
                break;
            case APR:
                retStr = "Apr";
                break;
            case MAY:
                retStr = "May";
                break;
            case JUN:
                retStr = "Jun";
                break;
            case JUL:
                retStr = "Jul";
                break;
            case AUG:
                retStr = "Aug";
                break;
            case SEP:
                retStr = "Sep";
                break;
            case OCT:
                retStr = "Oct";
                break;
            case NOV:
                retStr = "Nov";
                break;
            case DEC:
                retStr = "Dec";
                break;
            default:
                retStr = null;
                break;
        }

        return retStr;
    }
}
