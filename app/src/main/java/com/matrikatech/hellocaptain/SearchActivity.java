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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;


public class SearchActivity extends ActionBarActivity implements View.OnClickListener {

    private AutoCompleteTextView etSearchFirstPilot, etSearchSecondPilot, etSearchAc;
    private TextView etSearchMsn, etSearchFromDt, etSearchToDt;
    private CheckBox cbSearchIsNight, cbSearchIsMulti, cbSearchIsRotor;
    private Button btnSearch, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        findViews();
        setListeners();
    }

    private void setListeners() {
        btnSearch.setOnClickListener(this);
        btnReset.setOnClickListener(this);

        etSearchFromDt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dfFrom = new FromDatePicker();
                dfFrom.show(getSupportFragmentManager(), "from");
            }
        });

        etSearchToDt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dfFrom = new ToDatePicker();
                dfFrom.show(getSupportFragmentManager(), "to");
            }
        });


    }

    private void findViews() {
        etSearchAc = (AutoCompleteTextView) findViewById(R.id.etSearchAc);
        etSearchFirstPilot = (AutoCompleteTextView) findViewById(R.id.etSearchFirstPilot);
        etSearchSecondPilot = (AutoCompleteTextView) findViewById(R.id.etSearchSecondPilot);

        etSearchFromDt = (EditText) findViewById(R.id.etSearchFromDt);
        etSearchToDt = (EditText) findViewById(R.id.etSearchToDt);
        etSearchMsn = (EditText) findViewById(R.id.etSearchMsn);

        cbSearchIsMulti = (CheckBox) findViewById(R.id.cbSearchIsMulti);
        cbSearchIsRotor = (CheckBox) findViewById(R.id.cbSearchIsRotor);
        cbSearchIsNight = (CheckBox) findViewById(R.id.cbSearchIsNight);

        btnReset = (Button) findViewById(R.id.btnReset);
        btnSearch = (Button) findViewById(R.id.btnSearch);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReset:
                //reset fields
                etSearchAc.setText("");
                etSearchFirstPilot.setText("");
                etSearchSecondPilot.setText("");
                etSearchFromDt.setText("");
                etSearchToDt.setText("");
                etSearchMsn.setText("");
                cbSearchIsMulti.setChecked(false);
                cbSearchIsRotor.setChecked(false);
                cbSearchIsNight.setChecked(false);

                break;
            case R.id.btnSearch:
                //Start intent and send extras to search result activity
                Intent search = new Intent(SearchActivity.this, SearchResultActivity.class);

                search.putExtra("firstPilot", etSearchFirstPilot.getText().toString());
                search.putExtra("secondPilot", etSearchSecondPilot.getText().toString());
                search.putExtra("acName", etSearchAc.getText().toString());
                search.putExtra("msn", etSearchMsn.getText().toString());
                search.putExtra("fromDt", etSearchFromDt.getText().toString());
                search.putExtra("toDt", etSearchToDt.getText().toString());
                search.putExtra("rotor", cbSearchIsRotor.isChecked() ? true : false);
                search.putExtra("multi", cbSearchIsMulti.isChecked() ? true : false);
                search.putExtra("night", cbSearchIsNight.isChecked() ? true : false);

                startActivity(search);
                break;
        }
    }


    @SuppressLint("ValidFragment")
    public class FromDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

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

            etSearchFromDt.setText(
                    String.valueOf(year) + "-" +
                            String.valueOf(monthOfYear + 1) + "-" +     //+1 because android & java returns month from 0-11
                            String.valueOf(dayOfMonth)
            );

        }

    }

    @SuppressLint("ValidFragment")
    public class ToDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

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

            etSearchToDt.setText(
                    String.valueOf(year) + "-" +
                            String.valueOf(monthOfYear + 1) + "-" +     //+1 because android & java returns month from 0-11
                            String.valueOf(dayOfMonth)
            );
        }

    }
}
