package com.dugem.dugem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class SortPreferences extends AppCompatActivity {
    String sortPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_preferences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Sort by");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final SharedPreferences prefs = this.getSharedPreferences(
                "prefs", getApplicationContext().MODE_PRIVATE);
        final RadioButton radioButtonDateAscending = (RadioButton) findViewById(R.id.radioButtonDateAscending);
        final RadioButton radioButtonDateDescending = (RadioButton) findViewById(R.id.radioButtonDateDescending);
        final RadioButton radioButtonPriceLoHi = (RadioButton) findViewById(R.id.radioButtonPriceLoHi);
        final RadioButton radioButtonPriceHiLo = (RadioButton) findViewById(R.id.radioButtonPriceHiLo);
        radioButtonDateAscending.setChecked(prefs.getBoolean("dateAsc", false));
        radioButtonDateDescending.setChecked(prefs.getBoolean("dateDesc", false));
        radioButtonPriceLoHi.setChecked(prefs.getBoolean("priceAsc", false));
        radioButtonPriceHiLo.setChecked(prefs.getBoolean("priceDesc", false));
        Button buttonApplySort = (Button) findViewById(R.id.buttonApplySort);
        buttonApplySort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioButtonDateAscending.isChecked()){
                    sortPreferences = "&order=eventTimestamp%20ASC";
                    prefs.edit().putBoolean("dateAsc", true).apply();
                    prefs.edit().putBoolean("dateDesc", false).apply();
                    prefs.edit().putBoolean("priceAsc", false).apply();
                    prefs.edit().putBoolean("priceDesc", false).apply();
                    prefs.edit().putString("sortPreferences", sortPreferences).apply();
                } else if(radioButtonDateDescending.isChecked()){
                    sortPreferences = "&order=eventTimestamp%20DESC";
                    prefs.edit().putBoolean("dateAsc", false).apply();
                    prefs.edit().putBoolean("dateDesc", true).apply();
                    prefs.edit().putBoolean("priceAsc", false).apply();
                    prefs.edit().putBoolean("priceDesc", false).apply();
                    prefs.edit().putString("sortPreferences", sortPreferences).apply();
                } else if(radioButtonPriceLoHi.isChecked()){
                    sortPreferences = "&order=eventPrice%20ASC";
                    prefs.edit().putBoolean("dateAsc", false).apply();
                    prefs.edit().putBoolean("dateDesc", false).apply();
                    prefs.edit().putBoolean("priceAsc", true).apply();
                    prefs.edit().putBoolean("priceDesc", false).apply();
                    prefs.edit().putString("sortPreferences", sortPreferences).apply();
                } else if(radioButtonPriceHiLo.isChecked()){
                    sortPreferences = "&order=eventPrice%20DESC";
                    prefs.edit().putBoolean("dateAsc", false).apply();
                    prefs.edit().putBoolean("dateDesc", false).apply();
                    prefs.edit().putBoolean("priceAsc", false).apply();
                    prefs.edit().putBoolean("priceDesc", true).apply();
                    prefs.edit().putString("sortPreferences", sortPreferences).apply();
                }
                //Toast.makeText(getApplicationContext(), sortPreferences, Toast.LENGTH_LONG).show();
                toHome();
            }
        });

    }

    private void toHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fromSortPrefs", true);
        startActivity(intent);
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
