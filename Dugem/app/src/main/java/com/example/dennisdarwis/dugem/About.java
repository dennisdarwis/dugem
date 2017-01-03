package com.example.dennisdarwis.dugem;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;

import android.widget.TextView;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView textAbout = (TextView) findViewById(R.id.textAbout);
        textAbout.setText(Html.fromHtml("<b>Dugem</b>(/duːgəm/; doog'em or duug'em in english pronunciation)<br>is an abbreviation of <i>Dunia</i>(World) and <i>Gemerlap</i>(Sparlking) for <i>Dunia Gemerlap</i>(Sparkling World) which is a slang term for nightclub parties/events in Bahasa Indonesia."));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home ) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
