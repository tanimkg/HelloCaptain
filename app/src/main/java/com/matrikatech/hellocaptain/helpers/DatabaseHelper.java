package com.matrikatech.hellocaptain.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TANIM on 12-Mar-15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public final static String DB_NAME = "captain_db";
    public final static int VERSION = 2;

    public final static String MAIN_TABLE = "main_logs";
    public final static String USERS_TABLE = "users";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_MAIN_TABLE = "CREATE TABLE '" + MAIN_TABLE + "' " +
                "('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "'dt' DATETIME, 'pilot_1' TEXT, 'pilot_2' TEXT, 'ac_name' TEXT, " +
                "'ac_num' TEXT,'rotor' INTEGER,'multi_eng' INTEGER, 'mission' TEXT, " +
                "'route' TEXT,'night' INTEGER, 'hr_1' INTEGER, " +
                "'hr_2' INTEGER, 'hr_dual' INTEGER, 'act_hr' INTEGER, " +
                "'sim_hr' INTEGER)";

        db.execSQL(SQL_CREATE_MAIN_TABLE);

        String SQL_CREATE_USERS_TABLE = "CREATE TABLE '" + USERS_TABLE + "' (" +
                "    'id' INTEGER NOT NULL," +
                "    'username' VARCHAR(50) NOT NULL," +
                "    'password' VARCHAR(128) NOT NULL," +
                "    'name' TEXT," +
                "    'hint' TEXT" +
                ")";
        db.execSQL(SQL_CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + MAIN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        // Create tables again
        onCreate(db);
    }


    public void insertRecord(FlightLog fl) {
        SQLiteDatabase db = super.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("pilot_1", fl.getFirstPilot().getName());
        cv.put("pilot_2", fl.getSecondPilot().getName());
        cv.put("dt", fl.getDt());
        cv.put("ac_name", fl.getAc().getName());
        cv.put("ac_num", fl.getAc().getTailNo());
        cv.put("rotor", fl.getAc().isRotor());
        cv.put("multi_eng", fl.getAc().isMultiEng());
        cv.put("mission", fl.getMission());
        cv.put("route", fl.getRoute());
        cv.put("night", fl.isNight());
        cv.put("hr_1", fl.getHr1());
        cv.put("hr_2", fl.getHr2());
        cv.put("hr_dual", fl.getHrDual());
        cv.put("act_hr", fl.getActHr());
        cv.put("sim_hr", fl.getSimHr());

        db.insert(MAIN_TABLE, null, cv);
        db.close();

    }

    public HourCalculator getTotalHr1Day(WhereSQLBuilder where){
        if (where != null) { where.setNight(false); }
        String query = "SELECT SUM(hr_1) AS total_hr1_day FROM " + MAIN_TABLE
                + (where == null ? " WHERE night=0" : where.toString());
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            HourCalculator total = new HourCalculator(c.getInt(c.getColumnIndex("total_hr1_day")));
            db.close();
            return total;
        }
        return null;
    }

    public HourCalculator getTotalHr1Night(WhereSQLBuilder where){
        if (where != null) { where.setNight(true); }
        String query = "SELECT SUM(hr_1) AS total_hr1_night FROM " + MAIN_TABLE
                + (where == null ? " WHERE night=1" : where.toString())
                ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            HourCalculator total = new HourCalculator(c.getInt(c.getColumnIndex("total_hr1_night")));
            db.close();
            return total;
        }
        return null;
    }

    public HourCalculator getTotalHr2Day(WhereSQLBuilder where){
        if (where != null) { where.setNight(false); }
        String query = "SELECT SUM(hr_2) AS total_hr2_day FROM " + MAIN_TABLE
                + (where == null ? " WHERE night=0 " : where.toString());
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            HourCalculator total = new HourCalculator(c.getInt(c.getColumnIndex("total_hr2_day")));
            db.close();
            return total;
        }
        return null;
    }

    public HourCalculator getTotalHr2Night(WhereSQLBuilder where){
        if (where != null) { where.setNight(true); }
        String query = "SELECT SUM(hr_2) AS total_hr2_night FROM " + MAIN_TABLE
                + (where == null ? " WHERE night=1 " : where.toString());
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            HourCalculator total = new HourCalculator(c.getInt(c.getColumnIndex("total_hr2_night")));
            db.close();
            return total;
        }
        return null;
    }

    public HourCalculator getTotalHrDualDay(WhereSQLBuilder where){
        if (where != null) { where.setNight(false); }
        String query = "SELECT SUM(hr_dual) AS total_hrdual_day FROM " + MAIN_TABLE
                + (where == null ? " WHERE night=0 ": where.toString());
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            HourCalculator total = new HourCalculator(c.getInt(c.getColumnIndex("total_hrdual_day")));
            db.close();
            return total;
        }
        return null;
    }

    public HourCalculator getTotalHrDualNight(WhereSQLBuilder where){
        if (where != null) { where.setNight(true); }
        String query = "SELECT SUM(hr_dual) AS total_hrdual_night FROM " + MAIN_TABLE
                + (where == null ? " WHERE night=1" : where.toString());
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            HourCalculator total = new HourCalculator(c.getInt(c.getColumnIndex("total_hrdual_night")));
            db.close();
            return total;
        }
        return null;
    }

    public HourCalculator getTotalActHr(WhereSQLBuilder where){
        String query = "SELECT SUM(act_hr) AS total_act_hr FROM " + MAIN_TABLE
                + (where == null ? "" : where.toString());
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            HourCalculator total = new HourCalculator(c.getInt(c.getColumnIndex("total_act_hr")));
            db.close();
            return total;
        }
        return null;
    }

    public HourCalculator getTotalSimHr(WhereSQLBuilder where){
        String query = "SELECT SUM(sim_hr) AS total_sim_hr FROM " + MAIN_TABLE
                + (where == null ? "" : where.toString())
                ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst())
        {
            HourCalculator total = new HourCalculator(c.getInt(c.getColumnIndex("total_sim_hr")));
            db.close();
            return total;
        }
        return null;
    }


    public List<FlightLog> search(WhereSQLBuilder where)

    {
        List<FlightLog> allLogs = new ArrayList<FlightLog>();
        String queryStr = "SELECT * FROM " + MAIN_TABLE + where.toString();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(queryStr, null);

        //loop through all rows
        if (c.moveToFirst()) {  //if there ARE records inside cursor
            do {
                FlightLog fl = new FlightLog();
                fl.setMission(c.getString(c.getColumnIndex("mission")));
                fl.setRoute(c.getString(c.getColumnIndex("route")));
                //fl.setDescription(c.getString(c.getColumnIndex("description")));
                fl.setNight(c.getInt(c.getColumnIndex("night")) == fl.YES);
                fl.setDt(c.getString(c.getColumnIndex("dt")));
                fl.setHr1(c.getInt(c.getColumnIndex("hr_1")));
                fl.setHr2(c.getInt(c.getColumnIndex("hr_2")));
                fl.setHrDual(c.getInt(c.getColumnIndex("hr_dual")));
                fl.setActHr(c.getInt(c.getColumnIndex("act_hr")));
                fl.setSimHr(c.getInt(c.getColumnIndex("sim_hr")));

                //Aircraft
                Aircraft ac = new Aircraft();
                boolean isRotor = c.getInt(c.getColumnIndex("rotor")) == 1 ? true:false;
                boolean isMulti = (c.getInt(c.getColumnIndex("multi_eng")) == 1 ? true : false);
                ac.setName(c.getString(c.getColumnIndex("ac_name")));
                ac.setTailNo(c.getString(c.getColumnIndex("ac_num")));
                ac.setMultiEng(isMulti);
                ac.setRotor(isRotor);

                fl.setAc(ac);

                //Pilot
                String p1Name = c.getString(c.getColumnIndex("pilot_1"));
                String p2Name = c.getString(c.getColumnIndex("pilot_2"));
                Pilot p1 = new Pilot(p1Name);
                Pilot p2 = new Pilot(p2Name);

                fl.setFirstPilot(p1);
                fl.setSecondPilot(p2);

                //finally add flight log to list
                allLogs.add(fl);

            } while (c.moveToNext());
        }
        db.close();

        return allLogs;
    }

    public List<FlightLog> fetchAllRecord() {

        List<FlightLog> allLogs = new ArrayList<FlightLog>();
        String selectQuery = "SELECT * FROM " + MAIN_TABLE + " order by dt desc";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //loop through all rows
        if (c.moveToFirst()) {  //if there ARE records inside curson
            do {
                FlightLog fl = new FlightLog();
                fl.setMission(c.getString(c.getColumnIndex("mission")));
                fl.setRoute(c.getString(c.getColumnIndex("route")));
                //fl.setDescription(c.getString(c.getColumnIndex("description")));
                fl.setNight(c.getInt(c.getColumnIndex("night")) == fl.YES);
                fl.setDt(c.getString(c.getColumnIndex("dt")));
                fl.setHr1(c.getInt(c.getColumnIndex("hr_1")));
                fl.setHr2(c.getInt(c.getColumnIndex("hr_2")));
                fl.setHrDual(c.getInt(c.getColumnIndex("hr_dual")));
                fl.setActHr(c.getInt(c.getColumnIndex("act_hr")));
                fl.setSimHr(c.getInt(c.getColumnIndex("sim_hr")));

                //Aircraft
                Aircraft ac = new Aircraft();
                    boolean isRotor = c.getInt(c.getColumnIndex("rotor")) == 1 ? true:false;
                    boolean isMulti = (c.getInt(c.getColumnIndex("multi_eng")) == 1 ? true : false);
                ac.setName(c.getString(c.getColumnIndex("ac_name")));
                ac.setTailNo(c.getString(c.getColumnIndex("ac_num")));
                ac.setMultiEng(isMulti);
                ac.setRotor(isRotor);

                fl.setAc(ac);

                //Pilot
                String p1Name = c.getString(c.getColumnIndex("pilot_1"));
                String p2Name = c.getString(c.getColumnIndex("pilot_2"));
                Pilot p1 = new Pilot(p1Name);
                Pilot p2 = new Pilot(p2Name);

                fl.setFirstPilot(p1);
                fl.setSecondPilot(p2);

                //finally add flight log to list
                allLogs.add(fl);

            } while (c.moveToNext());
        }
        db.close();
        return allLogs;

    }

    public void deleteRecord(FlightLog aRecord)
    {
        long id = aRecord.getId();
        SQLiteDatabase db = super.getWritableDatabase();

        //TODO Incomplete
        db.delete(MAIN_TABLE, "id='"+id+"'", null);
        db.close();
    }


    public ArrayList<String> getPilotNames(){
        ArrayList<String> pilotNames = new ArrayList<String>();
        String queryStr = "SELECT DISTINCT pilot_1 FROM " + MAIN_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(queryStr, null);

        //loop through all rows
        if (c.moveToFirst()) {  //if there ARE records inside cursor
            do {
                String name = c.getString(c.getColumnIndex("pilot_1"));
                pilotNames.add(name);
            } while (c.moveToNext());
        }
        db.close();
        return pilotNames;
    }

    public ArrayList<String> getAcNames(){
        ArrayList<String> acNames = new ArrayList<String>();
        String queryStr = "SELECT DISTINCT ac_name FROM " + MAIN_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(queryStr, null);

        //loop through all rows
        if (c.moveToFirst()) {  //if there ARE records inside cursor
            do {
                String name = c.getString(c.getColumnIndex("ac_name"));
                acNames.add(name);
            } while (c.moveToNext());
        }
        db.close();
        return acNames;
    }

    public ArrayList<String> getTailNos(){
        ArrayList<String> tailNos = new ArrayList<String>();
        String queryStr = "SELECT DISTINCT ac_num FROM " + MAIN_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(queryStr, null);

        //loop through all rows
        if (c.moveToFirst()) {  //if there ARE records inside cursor
            do {
                String acNum = c.getString(c.getColumnIndex("ac_num"));
                tailNos.add(acNum);
            } while (c.moveToNext());
        }
        db.close();
        return tailNos;
    }

}
