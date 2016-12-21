// Dennis Darwis 216280619
// Written for SIT207 Android Programing 2nd Assignment
package com.example.dennisdarwis.dugem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;

/**
 * Created by dennisdarwis on 10/3/16.
 */
public class ListViewAdapter extends BaseAdapter {
    private Activity activity;
    private List<EventModel> eventModelList;
    private LayoutInflater inflater;
    DBHandler dbHandler;

    public ListViewAdapter(Activity activity, List<EventModel> eventModelList){
        this.activity = activity;
        this.eventModelList = eventModelList;
    }

    @Override
    public int getCount() {
        return eventModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return eventModelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null) {
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }if(view == null){
            // ListViewAdapter is using list_view layout.
            view = inflater.inflate(R.layout.list_view, null);
        }
        LinearLayout event = (LinearLayout) view.findViewById(R.id.event);
        TextView textDate = (TextView) view.findViewById(R.id.textDate);
        TextView textEventName = (TextView) view.findViewById(R.id.textEventName);
        TextView textEventVenue = (TextView) view.findViewById(R.id.textEventVenue);
        TextView textEventPrice = (TextView) view.findViewById(R.id.textEventPrice);
        ImageButton eventImage = (ImageButton) view.findViewById(R.id.eventImage);

        final EventModel eventModel = eventModelList.get(i);
        Date timestamp = eventModel.getEventTimestamp();
        Calendar cal = Calendar.getInstance();
        cal.setTime(timestamp);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        textDate.setText(String.valueOf(date)+" "+new DateFormatSymbols().getMonths()[month]+" "+String.valueOf(year));
        textEventName.setText(eventModel.getEventName());
        textEventVenue.setText(eventModel.getVenueName());

        if(eventModel.getEventPrice() == 0){
            String price = "FREE ENTRY";
            textEventPrice.setText(price);
        } else{
            String price = String.valueOf(eventModel.getEventPrice())+" AUD";
            textEventPrice.setText(price);
        }
        Picasso.with(activity.getApplicationContext()).load(eventModel.getImageUrl()).into(eventImage);
        // imagebutton eventImage will go into eventDetail once it's clicked
        eventImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Log.d("tot", eventModel.getEventDetails());
                toEventDetail(eventModel);
            }
        });
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("tot", eventModel.getEventDetails());
                toEventDetail(eventModel);
            }
        });
        return view;
    }


    private void toEventDetail(EventModel eventModel) {
        Intent intent = new Intent(activity, EventDetail.class);
        // the serialized eventModel will be put for EventDetail activity
        intent.putExtra("eventModel", (Serializable) eventModel);
        activity.startActivity(intent);
    }

}
