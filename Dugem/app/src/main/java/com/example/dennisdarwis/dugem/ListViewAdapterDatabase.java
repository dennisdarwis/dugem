// Dennis Darwis 216280619
// Written for SIT207 Android Programing 2nd Assignment
package com.example.dennisdarwis.dugem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dennisdarwis on 10/9/16.
 */
public class ListViewAdapterDatabase extends BaseAdapter {
    //Every code is same like ListViewAdapter except for toEventDetail
    private Activity activity;
    private List<EventModel> eventModelList;
    private LayoutInflater inflater;

    public ListViewAdapterDatabase(Activity activity, List<EventModel> eventModelList){
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
            view = inflater.inflate(R.layout.list_view, null);
        }
        TextView eventNameVenue = (TextView) view.findViewById(R.id.eventNameVenue);
        TextView eventPriceDate = (TextView) view.findViewById(R.id.eventPriceDate);
        ImageButton eventImage = (ImageButton) view.findViewById(R.id.eventImage);

        final EventModel eventModel = eventModelList.get(i);
        eventNameVenue.setText(eventModel.getEventName()+" - "+eventModel.getVenueName());
        eventPriceDate.setText(String.valueOf(eventModel.getEventPrice())+" AUD"+" - "+eventModel.getEventTimestamp().toString());
        Picasso.with(activity.getApplicationContext()).load(eventModel.getImageUrl()).into(eventImage);
        eventImage.setOnClickListener(new View.OnClickListener(){

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
        intent.putExtra("eventModel", eventModel);
        // boolean isBookmark is needed for EventDetail, to ensure that the action button for EventButton is for deleting the bookmark.
        intent.putExtra("isBookmark", true);
        activity.startActivity(intent);
    }

}
