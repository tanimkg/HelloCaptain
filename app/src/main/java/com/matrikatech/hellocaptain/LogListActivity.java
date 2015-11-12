package com.matrikatech.hellocaptain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.matrikatech.hellocaptain.helpers.DatabaseHelper;
import com.matrikatech.hellocaptain.helpers.FlightLog;
import com.matrikatech.hellocaptain.helpers.HourCalculator;
import com.matrikatech.hellocaptain.helpers.LogAdapter;

import java.util.List;


public class LogListActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    ListView lvLogs;
    List<FlightLog> logProvider;
    LogAdapter adapter;

    private AdView adView;
    private LinearLayout adLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_list);

        fetchAllRecords();
        processAd();
    }

    private void fetchAllRecords() {
        // 1. pass context and data to the custom adapter
        adapter = new LogAdapter(getApplicationContext(), getData());

        // 2. Get ListView
        lvLogs = (ListView) findViewById(R.id.lvLogs);

        // 3. setListAdapter
        lvLogs.setAdapter(adapter);

        //set listener
        lvLogs.setOnItemClickListener(this);

        //register for long click context menu popup
        registerForContextMenu(lvLogs);
    }

    private List<FlightLog> getData() {
        DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());

        logProvider = dbh.fetchAllRecord();
        return logProvider;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_list, menu);
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
        if (id == R.id.action_add_record) {
            Intent addRecord = new Intent(this, MainActivity.class);
            startActivity(addRecord);
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
        despatch.putExtra("isMulti", (dLog.getAc().isMultiEng() == 1 ? "Yes" : "No"));
        despatch.putExtra("isRotor", (dLog.getAc().isRotor() == 1 ? "Yes" : "No"));
        despatch.putExtra("msn", dLog.getMission() + (dLog.isNight() == 1 ? "(Night)" : ""));
        despatch.putExtra("isNight", dLog.isNight() == 1 ? true : false);
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


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        //go to xml file for enabling edit menu
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        AdapterViewCompat.AdapterContextMenuInfo info = (AdapterViewCompat.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
//            case R.id.editRecord:
//                editRecord(info.position);
//                return true;
            case R.id.deleteRecord:
                deleteRecord(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void editRecord(int position) {
        FlightLog dLog = logProvider.get(position);
        //TODO implement edit
        //send extras to edit activity and arrange update
    }

    private void deleteRecord(int position) {
        long id = logProvider.get(position).getId();

        DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());
        dbh.deleteRecord(id);

        //refresh list
        logProvider.remove(position); //otherwise notifydata change doesn't work
        adapter.notifyDataSetChanged();

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
