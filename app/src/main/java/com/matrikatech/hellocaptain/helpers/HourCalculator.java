package com.matrikatech.hellocaptain.helpers;

/**
 * Created by TANIM on 13-Mar-15.
 */
public class HourCalculator {

    private int h;
    private int m;
    private int hourInMinutes;

    public HourCalculator(int h, int m) {
        this.h = h;
        this.m = m;
        this.hourInMinutes = h * 60 + m;
    }

    public HourCalculator(int total) {
        this.hourInMinutes = total;
        this.h = (int) Math.floor(total/60);
        this.m = (int) total % 60;

    }

    public int getH() {
        return h;
    }

    public int getM() {
        return m;
    }

    public int getHourInMinutes() {
        return hourInMinutes;
    }

    public String getMinutesInHour() { return getH() + ":" + getM();}
}
