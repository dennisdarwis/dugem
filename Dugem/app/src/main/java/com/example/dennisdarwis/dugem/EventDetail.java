// Dennis Darwis 216280619
// Written for SIT207 Android Programing 2nd Assignment
package com.example.dennisdarwis.dugem;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class EventDetail extends AppCompatActivity {
    DBHandler dbHandler;
    EventModel eventModel;
    Boolean isBookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        Bundle bundle = getIntent().getExtras();
        // if it came from Bookmark activity that use ListViewAdapterDatabase, the Bookmark activity will put boolean isBookmark=true
        eventModel = (EventModel) bundle.getSerializable("eventModel");
        isBookmark = bundle.getBoolean("isBookmark", false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(eventModel.getEventName());
        setSupportActionBar(toolbar);
        ImageView eventImage = (ImageView) findViewById(R.id.eventImage);
        Picasso.with(this.getApplicationContext()).load(eventModel.getImageUrl()).into(eventImage);
        TextView eventName = (TextView) findViewById(R.id.eventNameVenue);
        eventName.setText(eventModel.getEventName());
        TextView eventVenue = (TextView) findViewById(R.id.eventVenue);
        eventVenue.setText(eventModel.getVenueName() + "  -  " + eventModel.getEventTimestamp().toString());
        TextView eventAddress = (TextView) findViewById(R.id.eventAddress);
        eventAddress.setText(eventModel.getAddress());

        eventAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapIntent(eventModel.getAddress());
            }
        });
        TextView eventPrice = (TextView) findViewById(R.id.eventPrice);
        eventPrice.setText(String.valueOf(eventModel.getEventPrice()) + " AUD");
        TextView eventContact = (TextView) findViewById(R.id.eventContact);
        eventContact.setText(eventModel.getContact());
        TextView eventDetails = (TextView) findViewById(R.id.eventDetails);
        eventDetails.setText(eventModel.getEventDetails());
        // Button to open browser, to open the event website
        Button buttonToWebsite = (Button) findViewById(R.id.buttonToWebsite);
        buttonToWebsite.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                websiteIntent(eventModel.getEventUrl());
            }
        });
        // Button to open google map, to open the event address
        Button buttonToMap = (Button) findViewById(R.id.buttonToMap);
        buttonToMap.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mapIntent(eventModel.getAddress());
            }
        });
    }

    private void websiteIntent(String eventUrl) {
        String uri = eventUrl;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    private void mapIntent(String map) {
        String uri = "http://maps.google.co.in/maps?q=" + map;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Checking the boolean isBookmark from the intent bundle.
        if(!isBookmark){
            getMenuInflater().inflate(R.menu.menu_eventdetail, menu);
        } else{
            getMenuInflater().inflate(R.menu.menu_bookmark, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==R.id.addtobookmark){
            Log.d("TOT", eventModel.getEventName());
            // adding the datamodel into the phone's DBHandler local database
            dbHandler = new DBHandler(getApplicationContext());
            dbHandler.addEvent(eventModel);
            // snackbar to indicate that the event is added to bookmark
            Snackbar.make(findViewById(android.R.id.content), "Added to Bookmark", Snackbar.LENGTH_SHORT)
                    .setActionTextColor(Color.BLACK)
                    .show();
        }
        if(id==R.id.deletebookmark){
            Log.d("TOT", eventModel.getEventName());
            dbHandler = new DBHandler(getApplicationContext());
            // to delete event data from bookmark
            dbHandler.deleteEvent(eventModel);
            toBookmark();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void toBookmark() {
        Intent intent = new Intent(this, Bookmark.class);
        startActivity(intent);
    }

}
