// Dennis Darwis 216280619
// Written for SIT207 Android Programing 2nd Assignment

package com.dugem.dugem;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;



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