package com.matrikatech.hellocaptain;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.matrikatech.hellocaptain.helpers.DatabaseHelper;
import com.matrikatech.hellocaptain.helpers.HourCalculator;


public class StatActivity extends ActionBarActivity {

    private TextView
            tvTotalHr1Day, tvTotalHr1Night, tvTotalHr1,
            tvTotalHr2Day, tvTotalHr2Night, tvTotalHr2,
            tvTotalHrDualDay, tvTotalHrDualNight, tvTotalHrDual,
            tvTotalActHr, tvTotalSimHr, tvGrandTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        findViewsByIds();
        setTexts();
    }

    private void setTexts() {

        DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());
            HourCalculator thr1 = new HourCalculator(dbh.getTotalHr1Day(null).getHourInMinutes() + dbh.getTotalHr1Night(null).getHourInMinutes());
        tvTotalHr1.setText(thr1.getMinutesInHour());
        tvTotalHr1Day.setText(dbh.getTotalHr1Day(null).getMinutesInHour());
        tvTotalHr1Night.setText(dbh.getTotalHr1Night(null).getMinutesInHour());
            HourCalculator thr2 = new HourCalculator(dbh.getTotalHr2Day(null).getHourInMinutes() + dbh.getTotalHr2Night(null).getHourInMinutes());
        tvTotalHr2.setText(thr2.getMinutesInHour());
        tvTotalHr2Day.setText(dbh.getTotalHr2Day(null).getMinutesInHour());
        tvTotalHr2Night.setText(dbh.getTotalHr2Night(null).getMinutesInHour());
            HourCalculator thrDual = new HourCalculator(dbh.getTotalHrDualDay(null).getHourInMinutes() + dbh.getTotalHrDualNight(null).getHourInMinutes());
        tvTotalHrDual.setText(thrDual.getMinutesInHour());
        tvTotalHrDualDay.setText(dbh.getTotalHrDualDay(null).getMinutesInHour());
        tvTotalHrDualNight.setText(dbh.getTotalHrDualNight(null).getMinutesInHour());
        tvTotalActHr.setText(dbh.getTotalActHr(null).getMinutesInHour());
        tvTotalSimHr.setText(dbh.getTotalSimHr(null).getMinutesInHour());

        int tempPlace = dbh.getTotalHr1Day(null).getHourInMinutes() + dbh.getTotalHr1Night(null).getHourInMinutes();
        tempPlace += dbh.getTotalHr2Day(null).getHourInMinutes() + dbh.getTotalHr2Night(null).getHourInMinutes();
        tempPlace += dbh.getTotalHrDualDay(null).getHourInMinutes() + dbh.getTotalHrDualNight(null).getHourInMinutes();
        tvGrandTotal.setText(new HourCalculator(tempPlace).getMinutesInHour());
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stat, menu);
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
}
