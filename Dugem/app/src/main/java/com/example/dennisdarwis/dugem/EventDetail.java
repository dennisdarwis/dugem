// Dennis Darwis 216280619
// Written for SIT207 Android Programing 2nd Assignment
package com.example.dennisdarwis.dugem;


import android.content.ClipData;
import android.content.ClipboardManager;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.sql.Date;
import java.text.DateFormatSymbols;
import java.util.Calendar;

public class EventDetail extends AppCompatActivity {
    DBHandler dbHandler;
    EventModel eventModel;
    Boolean isBookmark;
    private ClipboardManager myClipboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        Bundle bundle = getIntent().getExtras();
        // if it came from Bookmark activity that use ListViewAdapterDatabase, the Bookmark activity will put boolean isBookmark=true
        eventModel = (EventModel) bundle.getSerializable("eventModel");
        isBookmark = bundle.getBoolean("isBookmark", false);
        dbHandler = new DBHandler(getApplicationContext());

        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ImageView eventImage = (ImageView) findViewById(R.id.eventImage);
        Picasso.with(this.getApplicationContext()).load(eventModel.getImageUrl()).into(eventImage);
        TextView textEventName = (TextView) findViewById(R.id.textEventName);
        textEventName.setText(eventModel.getEventName());
        TextView textEventPrice = (TextView) findViewById(R.id.textEventPrice);
        if(eventModel.getEventPrice() == 0){
            textEventPrice.setText("Free Entry");
        } else{
            textEventPrice.setText("Ticket starts from "+eventModel.getEventPrice()+" AUD");
        }
        TextView textDate = (TextView) findViewById(R.id.textDate);
        Date timestamp = eventModel.getEventTimestamp();
        Calendar cal = Calendar.getInstance();
        cal.setTime(timestamp);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        textDate.setText(String.valueOf(date)+" "+new DateFormatSymbols().getMonths()[month]+" "+String.valueOf(year));
        TextView textEventVenue = (TextView) findViewById(R.id.textEventVenue);
        textEventVenue.setText(eventModel.getVenueName());
        TextView textEventAddress = (TextView) findViewById(R.id.textEventAddress);
        textEventAddress.setText(eventModel.getAddress());
        TextView textEventUrl = (TextView) findViewById(R.id.textEventUrl);
        textEventUrl.setText(eventModel.getEventUrl());
        TextView textEventDetails = (TextView) findViewById(R.id.textEventDetails);
        textEventDetails.setText(eventModel.getEventDetails());
        TextView textEventContact = (TextView) findViewById(R.id.textEventContact);
        textEventContact.setText(eventModel.getContact());

        LinearLayout buttonToMap1 = (LinearLayout) findViewById(R.id.buttonToMap1);
        buttonToMap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapIntent(eventModel.getAddress());
            }
        });
        buttonToMap1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                ClipData myClip = ClipData.newPlainText("address", eventModel.getAddress());
                myClipboard.setPrimaryClip(myClip);
                return true;
            }
        });
        LinearLayout buttonToWebsite1 = (LinearLayout) findViewById(R.id.buttonToWebsite1);
        buttonToWebsite1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                ClipData myClip = ClipData.newPlainText("web address", eventModel.getEventUrl());
                myClipboard.setPrimaryClip(myClip);
                return true;
            }
        });
        buttonToWebsite1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                websiteIntent(eventModel.getEventUrl());
            }
        });
        Button buttonToMap2 = (Button) findViewById(R.id.buttonToMap2);
        buttonToMap2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapIntent(eventModel.getAddress());
            }
        });
        Button buttonToWebsite2 = (Button) findViewById(R.id.buttonToWebsite2);
        buttonToWebsite2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                websiteIntent(eventModel.getEventUrl());
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
        if (item.getItemId() == android.R.id.home ) {
            finish();
            return true;
        }
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
