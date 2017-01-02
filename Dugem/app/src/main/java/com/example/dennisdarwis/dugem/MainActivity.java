// Dennis Darwis 216280619
// Written for SIT207 Android Programing 2nd Assignment

package com.example.dennisdarwis.dugem;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity{


    private TabLayout tabLayout;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
        SharedPreferences prefs = this.getSharedPreferences(
                "prefs", getApplicationContext().MODE_PRIVATE);
        Boolean responsible = prefs.getBoolean("responsible",false);
        Log.d("RESPONSIBLE", responsible.toString());
        if(!responsible){
            // Every the app is launched, the alert dialog pops up to verify if user is 18+ or not
            alertDialog.setTitle("Are you responsible yet?");

            alertDialog.setMessage("Are you aged 18+?");
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    onBackPressed();
                }
            });
            alertDialog.setPositiveButton("YES, I'M 18+", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                    SharedPreferences prefs = getBaseContext().getSharedPreferences(
                            "prefs", getApplicationContext().MODE_PRIVATE);
                    prefs.edit().putBoolean("responsible", true).commit();
                }
            });
            alertDialog.show();
        }

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.BLACK);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //add new tab for fiew pager(Discover Class and ThisWeek Class)
        tabLayout.addTab(tabLayout.newTab().setText("DISCOVER"));
        tabLayout.addTab(tabLayout.newTab().setText("THIS WEEK"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("DISCOVER");
        tabLayout.getTabAt(1).setText("THIS WEEK");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    @Override
    public void onBackPressed() {
        SharedPreferences prefs = getBaseContext().getSharedPreferences(
                "prefs", getApplicationContext().MODE_PRIVATE);
        prefs.edit().putBoolean("responsible", false).commit();
        moveTaskToBack(true);
    }
    // after the application is minimized and then resume, it asks the user again if there are aged 18+
    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences prefs = this.getSharedPreferences(
                "prefs", getApplicationContext().MODE_PRIVATE);
        Boolean responsible = prefs.getBoolean("responsible",false);
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
        if(!responsible){
            // Every the app is launched, the alert dialog pops up to verify if user is 18+ or not
            alertDialog.setTitle("Are you responsible yet?");

            alertDialog.setMessage("Are you aged 18+?");
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    onBackPressed();
                }
            });
            alertDialog.setPositiveButton("YES, I'M 18+", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                    SharedPreferences prefs = getBaseContext().getSharedPreferences(
                            "prefs", getApplicationContext().MODE_PRIVATE);
                    prefs.edit().putBoolean("responsible", true).commit();
                }
            });
            alertDialog.show();
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        SharedPreferences prefs = getBaseContext().getSharedPreferences(
                "prefs", getApplicationContext().MODE_PRIVATE);
        String sortPreferences = "&order=eventTimestamp%20ASC";
        prefs.edit().putBoolean("dateAsc", true).apply();
        prefs.edit().putBoolean("dateDesc", false).apply();
        prefs.edit().putBoolean("priceAsc", false).apply();
        prefs.edit().putBoolean("priceDesc", false).apply();
        prefs.edit().putString("sortPreferences", sortPreferences).apply();
    }


}