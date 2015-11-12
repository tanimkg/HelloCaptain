package com.matrikatech.hellocaptain.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.matrikatech.hellocaptain.R;

import java.util.List;

/**
 * Created by TANIM on 16-Mar-15.
 */
public class LogAdapter extends ArrayAdapter<FlightLog> {

    private LayoutInflater inflater;

    public LogAdapter(Context context, List<FlightLog> objects) {
        super(context, 0, objects);

        // 1. Create inflater (can also be set in getView method)
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        // 2. Get rowView from inflater
        if (convertView == null)    //means no cached view available
        {
            //so generate new view, else send null
            convertView = inflater.inflate(R.layout.loglist_row_view, null);
            holder = new ViewHolder();
            holder.tvRowDtD = (TextView) convertView.findViewById(R.id.tvRowDtD);
            holder.tvRowDtM = (TextView) convertView.findViewById(R.id.tvRowDtM);
            holder.tvRowDtY = (TextView) convertView.findViewById(R.id.tvRowDtY);
            holder.tvRowPilot1 = (TextView) convertView.findViewById(R.id.tvRowPilot1);
            holder.tvRowPilot2 = (TextView) convertView.findViewById(R.id.tvRowPilot2);
            holder.tvRowHr1 = (TextView) convertView.findViewById(R.id.tvRowHr1);
            holder.tvRowHr2 = (TextView) convertView.findViewById(R.id.tvRowHr2);
            holder.tvRowHrDual = (TextView) convertView.findViewById(R.id.tvRowHrDual);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        // 3. Get the text view from the rowView = view
        TextView tvRowDtD = (TextView) convertView.findViewById(R.id.tvRowDtD);
        TextView tvRowDtM = (TextView) convertView.findViewById(R.id.tvRowDtM);
        TextView tvRowDtY = (TextView) convertView.findViewById(R.id.tvRowDtY);
        TextView tvRowPilot1 = (TextView) convertView.findViewById(R.id.tvRowPilot1);
        TextView tvRowPilot2 = (TextView) convertView.findViewById(R.id.tvRowPilot2);
        TextView tvRowHr1 = (TextView) convertView.findViewById(R.id.tvRowHr1);
        TextView tvRowHr2 = (TextView) convertView.findViewById(R.id.tvRowHr2);
        TextView tvRowHrDual = (TextView) convertView.findViewById(R.id.tvRowHrDual);

        // 4. Set the text for textView
        String fullDate = getItem(position).getDt();
        //for debuging
//        System.out.println(fullDate);
        String[] dtArray = fullDate.split("-");
        tvRowDtD.setText(dtArray[2]);   //dtArray[3] is the last split of date, means day
        tvRowDtM.setText(new MyDateUtils().getMonthNameFromNo(Integer.valueOf(dtArray[1])));
        tvRowDtY.setText(dtArray[0]);
        tvRowPilot1.setText(getItem(position).getFirstPilot().getName());
        tvRowPilot2.setText(getItem(position).getSecondPilot().getName());
        tvRowHr1.setText(getItem(position).getHr1Str());
        tvRowHr2.setText(getItem(position).getHr2Str());
        tvRowHrDual.setText(getItem(position).getHrDualStr());

        //return convert view
        return convertView;

    }

    //VERY IMPORTANT! Use this viewholder strategy for professional app development
    static class ViewHolder {
        public TextView tvRowDtD;
        public TextView tvRowDtM;
        public TextView tvRowDtY;
        public TextView tvRowPilot1;
        public TextView tvRowPilot2;
        public TextView tvRowHr1;
        public TextView tvRowHr2;
        public TextView tvRowHrDual;
    }
}
