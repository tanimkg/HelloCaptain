package com.matrikatech.hellocaptain;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class EditActivity extends ActionBarActivity {

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
        setContentView(R.layout.activity_edit);

        findViews();
    }

    private void findViews() {
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


    private void setTexts() {
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
        getMenuInflater().inflate(R.menu.menu_edit, menu);
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
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    private void processAd() {

        adLayout = (LinearLayout) findViewById(R.id.adLayout);

        AdView adView = new AdView(this);
        adView.setAdUnitId(MainActivity.AD_UNIT_ID);
        adView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);

        //add adview to layout
        adLayout.addView(adView);
        // Request for Ads

        adView.loadAd(new AdRequest.Builder().build());


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
}
