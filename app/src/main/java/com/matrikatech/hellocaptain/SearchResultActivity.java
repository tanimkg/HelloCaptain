package com.matrikatech.hellocaptain;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.matrikatech.hellocaptain.helpers.DatabaseHelper;
import com.matrikatech.hellocaptain.helpers.FlightLog;
import com.matrikatech.hellocaptain.helpers.HourCalculator;
import com.matrikatech.hellocaptain.helpers.LogAdapter;
import com.matrikatech.hellocaptain.helpers.WhereSQLBuilder;

import java.util.List;


public class SearchResultActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    WhereSQLBuilder aWhere;
    ListView lvSearchResult;
    List<FlightLog> logProvider;
    private TextView
            tvTotalHr1Day, tvTotalHr1Night, tvTotalHr1,
            tvTotalHr2Day, tvTotalHr2Night, tvTotalHr2,
            tvTotalHrDualDay, tvTotalHrDualNight, tvTotalHrDual,
            tvTotalActHr, tvTotalSimHr, tvGrandTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        findViewsByIds();
        getWhereSQLfromIntentExtras();
        fetchSearchResults();
        setTexts();
    }


    private void fetchSearchResults() {
        // 1. pass context and data to the custom adapter
        final LogAdapter adapter = new LogAdapter(getApplicationContext(), getData());

        // 2. Get ListView
        lvSearchResult = (ListView) findViewById(R.id.lvSearchResult);

        // 3. setListAdapter
        lvSearchResult.setAdapter(adapter);

        //set listener
        lvSearchResult.setOnItemClickListener(this);
    }

    private List<FlightLog> getData() {
        DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());


        logProvider = dbh.search(getWhereSQLfromIntentExtras());
        return logProvider;
    }


    private WhereSQLBuilder getWhereSQLfromIntentExtras(){
        //get params via intent extras and pass through
        Intent recParams = getIntent();
        String firstPilot = recParams.getStringExtra("firstPilot");
        String secondPilot = recParams.getStringExtra("secondPilot");
        String acName = recParams.getStringExtra("acName");
        String msn = recParams.getStringExtra("msn");
        String fromDt = recParams.getStringExtra("fromDt");
        String toDt = recParams.getStringExtra("toDt");
        boolean rotor = recParams.getBooleanExtra("rotor", false);
        boolean multi = recParams.getBooleanExtra("multi", false);
        boolean night = recParams.getBooleanExtra("night", false);

        aWhere = new WhereSQLBuilder();
        aWhere.setFirstPilot(firstPilot);
        aWhere.setSecondPilot(secondPilot);
        aWhere.setAcName(acName);
        aWhere.setFromDt(fromDt);
        aWhere.setToDt(toDt);
        aWhere.setMsn(msn);
        aWhere.setNight(night);
        aWhere.setMulti(multi);
        aWhere.setRotor(rotor);

        return aWhere;
    }

    private void findViewsByIds() {
        tvTotalActHr        = (TextView) findViewById(R.id.tvTotalActHr);
        tvTotalSimHr        = (TextView) findViewById(R.id.tvTotalSimHr);
        tvTotalHr1          = (TextView) findViewById(R.id.tvTotalHr1);
        tvTotalHr1Day       = (TextView) findViewById(R.id.tvTotalHr1Day);
        tvTotalHr1Night     = (TextView) findViewById(R.id.tvTotalHr1Night);
        tvTotalHr2          = (TextView) findViewById(R.id.tvTotalHr2);
        tvTotalHr2Day       = (TextView) findViewById(R.id.tvTotalHr2Day);
        tvTotalHr2Night     = (TextView) findViewById(R.id.tvTotalHr2Night);
        tvTotalHrDual       = (TextView) findViewById(R.id.tvTotalHrDual);
        tvTotalHrDualDay    = (TextView) findViewById(R.id.tvTotalHrDualDay);
        tvTotalHrDualNight  = (TextView) findViewById(R.id.tvTotalHrDualNight);

        tvGrandTotal        = (TextView) findViewById(R.id.tvGrandTotal);
    }

    private void setTexts() {

        DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());
        HourCalculator thr1 = new HourCalculator(dbh.getTotalHr1Day(aWhere).getHourInMinutes() + dbh.getTotalHr1Night(aWhere).getHourInMinutes());
        tvTotalHr1.setText(thr1.getMinutesInHour());
        tvTotalHr1Day.setText(dbh.getTotalHr1Day(aWhere).getMinutesInHour());
        tvTotalHr1Night.setText(dbh.getTotalHr1Night(aWhere).getMinutesInHour());
        HourCalculator thr2 = new HourCalculator(dbh.getTotalHr2Day(aWhere).getHourInMinutes() + dbh.getTotalHr2Night(aWhere).getHourInMinutes());
        tvTotalHr2.setText(thr2.getMinutesInHour());
        tvTotalHr2Day.setText(dbh.getTotalHr2Day(aWhere).getMinutesInHour());
        tvTotalHr2Night.setText(dbh.getTotalHr2Night(aWhere).getMinutesInHour());
        HourCalculator thrDual = new HourCalculator(dbh.getTotalHrDualDay(aWhere).getHourInMinutes() + dbh.getTotalHrDualNight(aWhere).getHourInMinutes());
        tvTotalHrDual.setText(thrDual.getMinutesInHour());
        tvTotalHrDualDay.setText(dbh.getTotalHrDualDay(aWhere).getMinutesInHour());
        tvTotalHrDualNight.setText(dbh.getTotalHrDualNight(aWhere).getMinutesInHour());
        tvTotalActHr.setText(dbh.getTotalActHr(aWhere).getMinutesInHour());
        tvTotalSimHr.setText(dbh.getTotalSimHr(aWhere).getMinutesInHour());

        int tempPlace = dbh.getTotalHr1Day(aWhere).getHourInMinutes() + dbh.getTotalHr1Night(aWhere).getHourInMinutes()
        + dbh.getTotalHr2Day(aWhere).getHourInMinutes() + dbh.getTotalHr2Night(aWhere).getHourInMinutes()
        + dbh.getTotalHrDualDay(aWhere).getHourInMinutes() + dbh.getTotalHrDualNight(aWhere).getHourInMinutes();
        tvGrandTotal.setText(new HourCalculator(tempPlace).getMinutesInHour());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_result, menu);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent despatch = new Intent(this, RecordDetailsActivity.class);
        FlightLog dLog = logProvider.get(position);

        despatch.putExtra("id", dLog.getId());
        despatch.putExtra("firstPilot", dLog.getFirstPilot().getName());
        despatch.putExtra("secondPilot", dLog.getSecondPilot().getName());
        despatch.putExtra("ac", dLog.getAc().getName());
        despatch.putExtra("isMulti", (dLog.getAc().isMultiEng() == 1 ? "Yes":"No"));
        despatch.putExtra("isRotor", (dLog.getAc().isRotor() == 1 ? "Yes":"No"));
        despatch.putExtra("msn", dLog.getMission() + (dLog.isNight()==1?"(Night)":""));
        despatch.putExtra("isNight", dLog.isNight()==1?true:false);
        despatch.putExtra("dt", dLog.getDt());
        despatch.putExtra("route", dLog.getRoute());
        despatch.putExtra("tail", dLog.getAc().getTailNo());
        despatch.putExtra("hr1", new HourCalculator(dLog.getHr1()).getMinutesInHour());
        despatch.putExtra("hr2", new HourCalculator(dLog.getHr2()).getMinutesInHour());
        despatch.putExtra("hrDual", new HourCalculator(dLog.getHrDual()).getMinutesInHour());
        despatch.putExtra("actHr", new HourCalculator(dLog.getActHr()).getMinutesInHour());
        despatch.putExtra("simHr", new HourCalculator(dLog.getSimHr()).getMinutesInHour());

        startActivity(despatch);
    }
}
