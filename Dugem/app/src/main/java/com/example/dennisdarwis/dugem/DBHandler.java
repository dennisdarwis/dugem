// Dennis Darwis 216280619
// Written for SIT207 Android Programing 2nd Assignment
package com.example.dennisdarwis.dugem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennisdarwis on 10/9/16.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "eventManager",
    TABLE_EVENT = "events",
    KEY_ID = "id",
    KEY_VENUEID = "venueId",
    KEY_EVENTPRICE = "eventPrice",
    KEY_EVENTNAME = "eventName",
    KEY_IMAGEURL = "imageUrl",
    KEY_EVENTURL = "eventUrl",
    KEY_VENUENAME = "venueName",
    KEY_ADDRESS = "address",
    KEY_CONTACT = "contact",
    KEY_EVENTDETAILS = "eventDetails",
    KEY_EVENTTIMESTAMP = "eventTimestamp",
    KEY_EVENTTIMESTART = "eventTimeStart",
    KEY_EVENTTIMEEND = "eventTimeEnd",
    KEY_LATITUDE = "latitude",
    KEY_LONGITUDE = "longitude",
    KEY_CITY = "city";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_EVENT+"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_VENUEID+
                " INTEGER,"+KEY_EVENTPRICE+" INTEGER,"+KEY_EVENTNAME+" TEXT,"+KEY_IMAGEURL+" TEXT,"+KEY_EVENTURL+
                " TEXT,"+KEY_VENUENAME+" TEXT,"+KEY_ADDRESS+" TEXT,"+KEY_CONTACT+" TEXT,"+KEY_EVENTDETAILS+" TEXT,"+
                KEY_EVENTTIMESTAMP+" DATE,"+KEY_EVENTTIMESTART+" TEXT,"+KEY_EVENTTIMEEND+" TEXT,"+KEY_LATITUDE+
                " INTEGER,"+KEY_LONGITUDE+" INTEGER,"+KEY_CITY+" TEXT"+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EVENT);
    }

    public void addEvent(EventModel eventModel){
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID, eventModel.getId());
        values.put(KEY_VENUEID, eventModel.getVenueId());
        values.put(KEY_EVENTPRICE, eventModel.getEventPrice());
        values.put(KEY_EVENTNAME, eventModel.getEventName());
        values.put(KEY_IMAGEURL, eventModel.getImageUrl());
        values.put(KEY_EVENTURL, eventModel.getEventUrl());
        values.put(KEY_VENUENAME, eventModel.getVenueName());
        values.put(KEY_ADDRESS, eventModel.getAddress());
        values.put(KEY_CONTACT, eventModel.getContact());
        values.put(KEY_EVENTDETAILS, eventModel.getEventDetails());
        values.put(KEY_EVENTTIMESTAMP, eventModel.getEventTimestamp().toString());
        values.put(KEY_EVENTTIMESTART, eventModel.getEventTimeStart());
        values.put(KEY_EVENTTIMEEND, eventModel.getEventTimeEnd());
        values.put(KEY_LATITUDE, eventModel.getLatitude());
        values.put(KEY_LONGITUDE, eventModel.getLongitude());
        values.put(KEY_CITY, eventModel.getCity());
        db.insert(TABLE_EVENT, null, values);
        db.close();

    }

    public EventModel getEvent(int id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENT, new String[]{KEY_ID, KEY_VENUEID, KEY_EVENTPRICE, KEY_EVENTNAME, KEY_IMAGEURL, KEY_VENUENAME, KEY_ADDRESS, KEY_CONTACT, KEY_EVENTDETAILS, KEY_EVENTTIMESTAMP, KEY_EVENTTIMESTART, KEY_EVENTTIMEEND, KEY_LATITUDE, KEY_LONGITUDE, KEY_CITY}, KEY_ID+"=?", new String[]{ String.valueOf(id)}, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        EventModel eventModel = new EventModel(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)), cursor.getString(3), cursor.getString(4), cursor.getString(5),
                cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9),
                Date.valueOf(cursor.getString(10)), cursor.getString(11), cursor.getString(12),
                Double.parseDouble(cursor.getString(13)), Double.parseDouble(cursor.getString(14)), cursor.getString(15));
        db.close();
        cursor.close();
        return eventModel;
    }

    public boolean checkEvent(int id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_EVENT, new String[]{KEY_ID, KEY_VENUEID, KEY_EVENTPRICE, KEY_EVENTNAME, KEY_IMAGEURL, KEY_VENUENAME, KEY_ADDRESS, KEY_CONTACT, KEY_EVENTDETAILS, KEY_EVENTTIMESTAMP}, KEY_ID+"=?", new String[]{ String.valueOf(id)}, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
            return false;
        }
        EventModel eventModel = new EventModel(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)), cursor.getString(3), cursor.getString(4), cursor.getString(5),
                cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9),
                Date.valueOf(cursor.getString(10)), cursor.getString(11), cursor.getString(12),
                Double.parseDouble(cursor.getString(13)), Double.parseDouble(cursor.getString(14)), cursor.getString(15));
        db.close();
        cursor.close();
        return true;
    }

    public void deleteEvent(EventModel eventModel){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_EVENT, KEY_ID+"=?", new String[]{String.valueOf(eventModel.getId())});
        db.close();
    }

    public int getEventCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_EVENT, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();
        return count;
    }

    public int updateEvent(EventModel eventModel){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_VENUEID, eventModel.getId());
        values.put(KEY_EVENTPRICE, eventModel.getEventPrice());
        values.put(KEY_EVENTNAME, eventModel.getEventName());
        values.put(KEY_IMAGEURL, eventModel.getImageUrl());
        values.put(KEY_VENUENAME, eventModel.getVenueName());
        values.put(KEY_ADDRESS, eventModel.getAddress());
        values.put(KEY_CONTACT, eventModel.getContact());
        values.put(KEY_EVENTDETAILS, eventModel.getEventDetails());
        values.put(KEY_EVENTTIMESTAMP, eventModel.getEventTimestamp().toString());
        values.put(KEY_EVENTTIMESTART, eventModel.getEventTimeStart());
        values.put(KEY_EVENTTIMEEND, eventModel.getEventTimeEnd());
        values.put(KEY_LATITUDE, eventModel.getLatitude());
        values.put(KEY_LONGITUDE, eventModel.getLongitude());
        values.put(KEY_CITY, eventModel.getCity());

        return db.update(TABLE_EVENT, values, KEY_ID+"=?", new String[]{String.valueOf(eventModel.getId())});
    }

    public List<EventModel> getAllEvents(){
        List<EventModel> events = new ArrayList<EventModel>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_EVENT, null);

        if (cursor.moveToFirst()){
            do{
                EventModel eventModel = new EventModel(Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)), cursor.getString(3), cursor.getString(4), cursor.getString(5),
                        cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9),
                        Date.valueOf(cursor.getString(10)), cursor.getString(11), cursor.getString(12),
                        Double.parseDouble(cursor.getString(13)), Double.parseDouble(cursor.getString(14)),
                        cursor.getString(15));
                events.add(eventModel);
            }
            while(cursor.moveToNext());
        }
        return events;
    }
}
