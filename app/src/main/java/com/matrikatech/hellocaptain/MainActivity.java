package com.matrikatech.hellocaptain;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.matrikatech.hellocaptain.helpers.Aircraft;
import com.matrikatech.hellocaptain.helpers.DatabaseHelper;
import com.matrikatech.hellocaptain.helpers.FlightLog;
import com.matrikatech.hellocaptain.helpers.HourCalculator;
import com.matrikatech.hellocaptain.helpers.Pilot;
import com.matrikatech.hellocaptain.helpers.StringUtils;

import java.util.Calendar;

//Ad related imports

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    public static final String AD_UNIT_ID = "ca-app-pub-4030334335580782/3736718756";
    private EditText etDate, etMission, etRoute,
            etHr1, etMin1,
            etHr2, etMin2, etHrDual, etMinDual, etActHr,
            etActMin, etSimHr, etSimMin;
    private AutoCompleteTextView etFirstPilot, etSecondPilot, etAcName, etTailNo;
    private CheckBox cbNight, cbMultiEng, cbRotary;
    private Button btnSave;
    private AdView adView;
    private LinearLayout adLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        processAd();
    }

    private void processAd() {

        adLayout = (LinearLayout) findViewById(R.id.adLayout);

        AdView adView = new AdView(MainActivity.this);
        adView.setAdUnitId(AD_UNIT_ID);
        adView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);

        //add adview to layout
        adLayout.addView(adView);
        // Request for Ads

        adView.loadAd(new AdRequest.Builder().build());


    }

    private void init() {

        // TEMP CODE
//        Intent stats = new Intent(MainActivity.this, SearchActivity.class);
//        startActivity(stats);

        findViewByIds();
        // Set listeners;
        ArrayAdapter<String> pilotNamesAdapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                new DatabaseHelper(getApplicationContext()).getPilotNames());
        etFirstPilot.setAdapter(pilotNamesAdapter);
        etSecondPilot.setAdapter(pilotNamesAdapter);

        ArrayAdapter<String> acNamesAdapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                new DatabaseHelper(getApplicationContext()).getAcNames());
        etAcName.setAdapter(acNamesAdapter);

        ArrayAdapter<String> tailNosAdapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                new DatabaseHelper(getApplicationContext()).getTailNos());
        etTailNo.setAdapter(tailNosAdapter);


        //implement datepicker
        /* MyDatePicker class is set
        * */
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dpFragment = new MyDatePicker();
                dpFragment.show(getSupportFragmentManager(), "tag");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //populate aircraft
                Aircraft ac = new Aircraft();
                ac.setName(parseEditTextToString(etAcName));
                ac.setTailNo(parseEditTextToString(etTailNo));
                ac.setMultiEng(cbMultiEng.isChecked() ? true : false);
                ac.setRotor(cbRotary.isChecked() ? true : false);
                //populate pilot
                Pilot p1 = new Pilot("", parseEditTextToString(etFirstPilot));
                Pilot p2 = new Pilot("", parseEditTextToString(etSecondPilot));

                //populate flight record
                FlightLog fl = new FlightLog();
                fl.setAc(ac);
                fl.setDt(parseEditTextToString(etDate));
                fl.setNight(cbNight.isChecked() ? true : false);
                fl.setFirstPilot(p1);
                fl.setSecondPilot(p2);
                fl.setMission(parseEditTextToString(etMission));
                fl.setRoute(parseEditTextToString(etRoute));
                //prepare hours
                HourCalculator hr1 = new HourCalculator(parseEditTextToInt(etHr1), parseEditTextToInt(etMin1));
                HourCalculator hr2 = new HourCalculator(parseEditTextToInt(etHr2), parseEditTextToInt(etMin2));
                HourCalculator hrDual = new HourCalculator(parseEditTextToInt(etHrDual), parseEditTextToInt(etMinDual));
                HourCalculator actHr = new HourCalculator(parseEditTextToInt(etActHr), parseEditTextToInt(etActMin));
                HourCalculator simHr = new HourCalculator(parseEditTextToInt(etSimHr), parseEditTextToInt(etSimMin));
                fl.setHr1(hr1.getHourInMinutes());
                fl.setHr2(hr2.getHourInMinutes());
                fl.setHrDual(hrDual.getHourInMinutes());
                fl.setActHr(actHr.getHourInMinutes());
                fl.setSimHr(simHr.getHourInMinutes());

                //add to database
                DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());
                dbh.insertRecord(fl);
                showToast("Record saved");
                clearFields();
            }
        });
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    private void findViewByIds() {
        btnSave = (Button) findViewById(R.id.btnSave);

        etFirstPilot = (AutoCompleteTextView) findViewById(R.id.etFistPilot);
        etSecondPilot = (AutoCompleteTextView) findViewById(R.id.etSecondPilot);
        etAcName = (AutoCompleteTextView) findViewById(R.id.etAcName);
        etTailNo = (AutoCompleteTextView) findViewById(R.id.etTailNo);

        etDate = (EditText) findViewById(R.id.etDate);
        etMission = (EditText) findViewById(R.id.etMission);
        etRoute = (EditText) findViewById(R.id.etRoute);
        etHr1 = (EditText) findViewById(R.id.etHr1);
        etMin1 = (EditText) findViewById(R.id.etMin1);
        etHr2 = (EditText) findViewById(R.id.etHr2);
        etMin2 = (EditText) findViewById(R.id.etMin2);
        etHrDual = (EditText) findViewById(R.id.etHrDual);
        etMinDual = (EditText) findViewById(R.id.etMinDual);
        etActHr = (EditText) findViewById(R.id.etActHr);
        etActMin = (EditText) findViewById(R.id.etActMin);
        etSimHr = (EditText) findViewById(R.id.etSimHr);
        etSimMin = (EditText) findViewById(R.id.etSimMin);

        cbMultiEng = (CheckBox) findViewById(R.id.cbMultiEng);
        cbNight = (CheckBox) findViewById(R.id.cbNight);
        cbRotary = (CheckBox) findViewById(R.id.cbRotary);


    }

    private String parseEditTextToString(EditText v) {
        String str = new StringUtils().getNullSafeString(v.getText().toString());
        return str;
    }

    private int parseEditTextToInt(EditText v) {
        String str = v.getText().toString();
        if ((str == null) || str.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(str);
    }

    private void clearFields() {
        etAcName.setText("");
        etTailNo.setText("");
        etDate.setText("");
        etMission.setText("");
        etRoute.setText("");
        etFirstPilot.setText("");
        etSecondPilot.setText("");
        etHr1.setText("");
        etMin1.setText("");
        etHr2.setText("");
        etMin2.setText("");
        etHrDual.setText("");
        etMinDual.setText("");
        etActHr.setText("");
        etActMin.setText("");
        etSimHr.setText("");
        etSimMin.setText("");

        //clear ckboxes
        cbMultiEng.setChecked(false);
        cbRotary.setChecked(false);
        cbNight.setChecked(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_listAllLogs) {
            Intent allRecords = new Intent(this, LogListActivity.class);
            startActivity(allRecords);
        }
        if (id == R.id.action_stat) {
            Intent stats = new Intent(this, StatActivity.class);
            startActivity(stats);
        }
        if (id == R.id.action_search) {
            Intent search = new Intent(this, SearchActivity.class);
            startActivity(search);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    /*
    * To stop loading/refreshing add when the activity is closed
    * */
    @Override
    protected void onPause() {
        super.onPause();
        if (adView != null) {
            adView.pause();
        }
    }

    @SuppressLint("ValidFragment")
    public class MyDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        int day, month, year;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar cal = Calendar.getInstance();
            //use the current date as default
            day = cal.get(Calendar.DAY_OF_MONTH);
            month = cal.get(Calendar.MONTH);
            year = cal.get(Calendar.YEAR);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            etDate.setText(
                    String.valueOf(year) + "-" +
                            String.valueOf(monthOfYear + 1) + "-" +     //+1 because android & java returns month from 0-11
                            String.valueOf(dayOfMonth)
            );
        }
    }
}
