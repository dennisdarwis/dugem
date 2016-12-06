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
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        TextView eventNameVenue = (TextView) view.findViewById(R.id.eventNameVenue);
        TextView eventPriceDate = (TextView) view.findViewById(R.id.eventPriceDate);
        ImageButton eventImage = (ImageButton) view.findViewById(R.id.eventImage);
        /** BOOKMARK STAR
        final ImageButton bookmarkButton = (ImageButton) view.findViewById(R.id.bookmarkButton);
         */
        dbHandler = new DBHandler(activity.getApplicationContext());

        boolean bool = true;
        // each data model will be following the list_view layout.
        final EventModel eventModel = eventModelList.get(i);
        /** BOOKMARK STAR
        if(eventModel.getId()%2==0){
            bookmarkButton.setImageResource(R.drawable.ic_star_border_black_24dp);
        } else{
            bookmarkButton.setImageResource(R.drawable.ic_star_black_24dp);
        }
         */
        eventNameVenue.setText(eventModel.getEventName()+" - "+eventModel.getVenueName());
        eventPriceDate.setText(String.valueOf(eventModel.getEventPrice())+" AUD"+" - "+eventModel.getEventTimestamp().toString());
        Picasso.with(activity.getApplicationContext()).load(eventModel.getImageUrl()).into(eventImage);
        // imagebutton eventImage will go into eventDetail once it's clicked
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
        // the serialized eventModel will be put for EventDetail activity
        intent.putExtra("eventModel", eventModel);
        activity.startActivity(intent);
    }

}
