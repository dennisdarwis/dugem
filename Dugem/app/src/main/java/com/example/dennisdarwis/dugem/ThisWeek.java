// Dennis Darwis 216280619
// Written for SIT207 Android Programing 2nd Assignment
package com.example.dennisdarwis.dugem;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThisWeek extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {

    private List<EventModel> eventModelList = new ArrayList<>();
    private ListViewAdapter listViewAdapter;
    private ListView listView;
    private RequestQueue requestQueue;
    private SwipeRefreshLayout swipeRefreshLayout = null;
    static Parcelable state;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_this_week, container, false);
        requestQueue = Volley.newRequestQueue(getContext());
        String url = "http://104.199.155.15/api/v2/db/_table/dugem?order=eventTimestamp%20DESC";
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                int threshold = 1;
                int count = listView.getCount();

                if (i == SCROLL_STATE_IDLE) {
                    if (listView.getLastVisiblePosition() >= count
                            - threshold) {
                        // Execute LoadMoreDataTask AsyncTask
                        Log.d("MENTOK", "MENTOK: ");
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.GET, url, new JSONObject(), (Response.Listener<JSONObject>) this, this);
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonRequest);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Refreshing data on server
                eventModelList = new ArrayList<>();
                jsonRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(jsonRequest);
            }
        });
        return view;
    }

    @Override
    public void onPause(){
        //to save the current scroll position once the user goes into other activity and go back into the list view
        state = listView.onSaveInstanceState();
        super.onPause();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Error:",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray("resource");
            // Every code doest same as Discover activity, except for checking if the events occurs between today and a week
            Calendar currentTime = Calendar.getInstance();
            currentTime.add(Calendar.DATE,-1);
            Calendar nextWeek = Calendar.getInstance();
            nextWeek.add(Calendar.DATE,7);
            Date dateNow = new Date((currentTime.getTime()).getTime());
            for (int i=0; i < jsonArray.length(); i++){
                JSONObject resource = jsonArray.getJSONObject(i);
                Date eventTimestamp = Date.valueOf(resource.getString("eventTimestamp"));
                if(eventTimestamp.after(currentTime.getTime()) && eventTimestamp.before(nextWeek.getTime())){
                    int id = resource.getInt("id");
                    int eventPrice = resource.getInt("eventPrice");
                    String eventName = resource.getString("eventName");
                    String imageUrl = resource.getString("ImageUrl");
                    String eventUrl = resource.getString("eventUrl");
                    String venueName = resource.getString("venueName");
                    String address = resource.getString("address");
                    String contact = resource.getString("contact");
                    String eventDetails = resource.getString("eventDetails");

                    int venueId = resource.getInt("venueId");
                    Log.d("ASU", "onResponse: "+eventName);
                    EventModel data = new EventModel(id, venueId, eventPrice, eventName, imageUrl, eventUrl, venueName, address, contact, eventDetails, eventTimestamp);
                    eventModelList.add(data);
                    Log.d("DATA", data.toString());
                }

            }
            Log.d("tot", "TOT: "+eventModelList.toString());
            listViewAdapter = new ListViewAdapter(getActivity(), eventModelList);
            listView.setAdapter(listViewAdapter);
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
