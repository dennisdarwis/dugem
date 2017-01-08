// Dennis Darwis 216280619
// Written for SIT207 Android Programing 2nd Assignment
package com.dugem.dugem;

import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Bookmark extends AppCompatActivity {
    private List<EventModel> eventModelList = new ArrayList<>();
    private ListViewAdapterDatabase listViewAdapterDatabase;
    private ListView listView;

    DBHandler dbHandler;
    Boolean deleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        dbHandler = new DBHandler(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eventModelList = dbHandler.getAllEvents();
        listView = (ListView) findViewById(R.id.listView);
        listViewAdapterDatabase = new ListViewAdapterDatabase(this, eventModelList);
        listView.setAdapter((listViewAdapterDatabase));
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
