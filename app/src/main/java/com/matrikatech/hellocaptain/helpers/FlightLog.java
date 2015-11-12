package com.matrikatech.hellocaptain.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by TANIM on 12-Mar-15.
 */
public class FlightLog {

    public final static int YES = 1;
    public final static int NO = 0;

    public long id;
    private Pilot firstPilot, secondPilot, passenger;
    private Aircraft ac;
    private Date dt;
    private int hr1, hr2, hrDual, actHr, simHr;
    private boolean isNight;
    private String mission, route, description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDt() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        String date = sdf.format(dt);
        return date;
    }

    public void setDt(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        Date dt = null;
        try {
            dt = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.dt = dt;
    }

    public Pilot getFirstPilot() {
        return firstPilot;
    }

    public void setFirstPilot(Pilot firstPilot) {
        this.firstPilot = firstPilot;
    }

    public Pilot getSecondPilot() {
        return secondPilot;
    }

    public void setSecondPilot(Pilot secondPilot) {
        this.secondPilot = secondPilot;
    }

    public Pilot getPassenger() {
        return passenger;
    }

    public void setPassenger(Pilot passenger) {
        this.passenger = passenger;
    }

    public Aircraft getAc() {
        return ac;
    }

    public void setAc(Aircraft ac) {
        this.ac = ac;
    }

    public int isNight() {
        return isNight ? YES : NO;
    }

    public void setNight(boolean isNight) {
        this.isNight = isNight;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHr1() {
        return hr1;
    }

    public void setHr1(int hr1) {
        this.hr1 = hr1;
    }

    public String getHr1Str() {
        HourCalculator hc = new HourCalculator(hr1);
        return hc.getMinutesInHour();
    }

    public String getHr2Str() {
        HourCalculator hc = new HourCalculator(hr2);
        return hc.getMinutesInHour();
    }

    public String getHrDualStr() {
        HourCalculator hc = new HourCalculator(hrDual);
        return hc.getMinutesInHour();
    }

    public int getHr2() {
        return hr2;
    }

    public void setHr2(int hr2) {
        this.hr2 = hr2;
    }

    public int getHrDual() {
        return hrDual;
    }

    public void setHrDual(int hrDual) {
        this.hrDual = hrDual;
    }

    public int getActHr() {
        return actHr;
    }

    public void setActHr(int actHr) {
        this.actHr = actHr;
    }

    public int getSimHr() {
        return simHr;
    }

    public void setSimHr(int simHr) {
        this.simHr = simHr;
    }
}