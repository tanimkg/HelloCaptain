package com.matrikatech.hellocaptain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class RecordDetailsActivity extends ActionBarActivity {

    private TextView
            tvDetailsFirstPilot,
            tvDetailsSecondPilot,
            tvDetailsAc,
            tvDetailsIsMulti,
            tvDetailsIsRotary,
            tvDetailsMsn,
            tvDetailsRoute,
            tvDetailsDt,
            tvDetailsTail,
            tvDetailsHr1,
            tvDetailsHr2,
            tvDetailsHrDual,
            tvDetailsActHr,
            tvDetailsSimHr;

    private AdView adView;
    private LinearLayout adLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);

        findViewsByIds();
        getIntentExtras();
        processAd();
    }

    private void getIntentExtras() {
        Intent recIn = getIntent();

        String firstPilot = recIn.getStringExtra("firstPilot");
        String secondPilot = recIn.getStringExtra("secondPilot");
        String ac = recIn.getStringExtra("ac");
        String isMulti = recIn.getStringExtra("isMulti");
        String isRotor = recIn.getStringExtra("isRotor");
        String dt = recIn.getStringExtra("dt");
        String msn = recIn.getStringExtra("msn");
        String route = recIn.getStringExtra("route");
        String tail = recIn.getStringExtra("tail");
        String hr1 = recIn.getStringExtra("hr1");
        String hr2 = recIn.getStringExtra("hr2");
        String hrDual = recIn.getStringExtra("hrDual");
        String actHr = recIn.getStringExtra("actHr");
        String simHr = recIn.getStringExtra("simHr");

        boolean isNight = recIn.getBooleanExtra("isNight", false);

        tvDetailsFirstPilot.setText(firstPilot);
        tvDetailsSecondPilot.setText(secondPilot);
        tvDetailsDt.setText(dt);
        tvDetailsAc.setText(ac);
        tvDetailsIsRotary.setText(isRotor);
        tvDetailsIsMulti.setText(isMulti);
        tvDetailsMsn.setText(msn);
        tvDetailsTail.setText(tail);
        tvDetailsRoute.setText(route);
        tvDetailsHr1.setText(hr1);
        tvDetailsHr2.setText(hr2);
        tvDetailsHrDual.setText(hrDual);
        tvDetailsActHr.setText(actHr);
        tvDetailsSimHr.setText(simHr);
    }

    private void findViewsByIds() {
        tvDetailsFirstPilot = (TextView) findViewById(R.id.tvDetailsFirstPilot);
        tvDetailsSecondPilot = (TextView) findViewById(R.id.tvDetailsSecondPilot);
        tvDetailsAc = (TextView) findViewById(R.id.tvDetailsAc);
        tvDetailsIsMulti = (TextView) findViewById(R.id.tvDetailsIsMulti);
        tvDetailsIsRotary = (TextView) findViewById(R.id.tvDetailsIsRotary);
        tvDetailsMsn = (TextView) findViewById(R.id.tvDetailsMsn);
        tvDetailsRoute = (TextView) findViewById(R.id.tvDetailsRoute);
        tvDetailsDt = (TextView) findViewById(R.id.tvDetailsDt);
        tvDetailsTail = (TextView) findViewById(R.id.tvDetailsTailNo);
        tvDetailsHr1 = (TextView) findViewById(R.id.tvDetailsHr1);
        tvDetailsHr2 = (TextView) findViewById(R.id.tvDetailsHr2);
        tvDetailsHrDual = (TextView) findViewById(R.id.tvDetailsHrDual);
        tvDetailsActHr = (TextView) findViewById(R.id.tvDetailsActHr);
        tvDetailsSimHr = (TextView) findViewById(R.id.tvDetailsSimHr);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_record_details, menu);
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
