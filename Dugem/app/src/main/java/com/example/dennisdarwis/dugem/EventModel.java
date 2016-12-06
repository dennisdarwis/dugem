// Dennis Darwis 216280619
// Written for SIT207 Android Programing 2nd Assignment
package com.example.dennisdarwis.dugem;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by dennisdarwis on 10/3/16.
 */
// implements Seriable, therefore the EventModel can be sent through the intent.
public class EventModel implements Serializable{
    private int id;
    private int venueId;
    private int eventPrice;
    private String eventName;
    private String imageUrl;
    private String eventUrl;
    private String venueName;
    private String address;
    private String contact;
    private String eventDetails;
    private Date eventTimestamp;

    public EventModel(int id, int venueId, int eventPrice, String eventName, String imageUrl, String eventUrl, String venueName, String address, String contact, String eventDetails, Date timestamp){
        this.id = id;
        this.venueId = venueId;
        this.eventPrice = eventPrice;
        this.eventName = eventName;
        this.imageUrl = imageUrl;
        this.eventUrl = eventUrl;
        this.venueName = venueName;
        this.address = address;
        this.contact = contact;
        this.eventDetails = eventDetails;
        this.eventTimestamp = timestamp;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getVenueId(){
        return venueId;
    }
    public void setVenueId(int venueId){
        this.venueId = venueId;
    }
    public int getEventPrice(){
        return eventPrice;
    }
    public void setEventPrice(int eventPrice){
        this.eventPrice = eventPrice;
    }
    public String getEventName(){
        return eventName;
    }
    public void setEventName(String eventName){
        this.eventName = eventName;
    }
    public String getImageUrl(){
        return imageUrl;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }
    public String getEventUrl(){
        return eventUrl;
    }
    public void setEventUrl(String eventUrl){
        this.eventUrl = eventUrl;
    }
    public String getVenueName(){
        return venueName;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getContact(){
        return contact;
    }
    public void setContact(String contact){
        this.contact = contact;
    }
    public String getEventDetails(){
        return eventDetails;
    }
    public void setEventDetails(String eventDetails){
        this.eventDetails = eventDetails;
    }
    public Date getEventTimestamp(){
        return eventTimestamp;
    }
    public void setEventTimestamp(Date eventTimestamp){
        this.eventTimestamp = eventTimestamp;
    }


}
