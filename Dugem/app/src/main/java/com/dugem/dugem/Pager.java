// Dennis Darwis 216280619
// Written for SIT207 Android Programing 2nd Assignment
package com.dugem.dugem;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by dennisdarwis on 10/1/16.
 */
public class Pager extends FragmentStatePagerAdapter {

    int tabCount;

    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                Discover discover = new Discover();
                return discover;
            case 1:
                ThisWeek thisWeek = new ThisWeek();
                return thisWeek;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
