// Dennis Darwis 216280619
// Written for SIT207 Android Programing 2nd Assignment
package com.example.dennisdarwis.dugem;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Discover extends Fragment implements Response.ErrorListener, Listener<JSONObject> {

    private List<EventModel> eventModelList = new ArrayList<>();
    private ListViewAdapter listViewAdapter;
    private ListView listView;
    private RequestQueue requestQueue;
    private SwipeRefreshLayout swipeRefreshLayout = null;
    // limit is the amount of events loaded from server.
    private int limit = 64;
    private int offset = 0;
    private int total;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_discover, container, false);
        //creating new requestQueue, to get event objects from google cloud
        requestQueue = Volley.newRequestQueue(getContext());
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {


            private int currentVisibleItemCount;
            private int currentScrollState;
            private int currentFirstVisibleItem;
            private int totalItem;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.totalItem = totalItemCount;
            }

            private void isScrollCompleted() {
                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
                        && this.currentScrollState == SCROLL_STATE_IDLE && totalItem != total) {
                    Log.d("TOTALITEM", String.valueOf(totalItem)+" "+String.valueOf(currentFirstVisibleItem));
                    swipeRefreshLayout.setRefreshing(true);
                    reload();
                }
            }
        });
        // the listviewadapter takes the events data from eventModelList
        listViewAdapter = new ListViewAdapter(getActivity(), eventModelList);
        // the listviewadapter that contains the events data from eventModelList will be adapted into listview
        listView.setAdapter(listViewAdapter);
        //listView.setSelection(eventModelList.size()-6);
        // String URL
        String url = "http://104.199.155.15/api/v2/db/_table/dugem?limit="+String.valueOf(limit)+"&offset="+String.valueOf(offset)+"&order=eventTimestamp%20DESC&include_count=true";
        // CustomJSONObjectRequest as jsonRequest, contains headers required for API Request
        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.GET, url, new JSONObject(), (Listener<JSONObject>) this, this);
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // the CustomJSONObjectRequest is put into requestQueue for process.
        requestQueue.add(jsonRequest);
        // swipeRefreshLayout for refreshing list of event objects once it is swiped down.
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // in every request, the List must be emptied first, to avoid duplication
                eventModelList = new ArrayList<>();
                //Refreshing data on server
                jsonRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(jsonRequest);
                offset=0;
            }
        });

        return view;

    }

    private void reload() {
        // Execute LoadMoreDataTask AsyncTask
        offset=offset+64;
        String url = "http://104.199.155.15/api/v2/db/_table/dugem?limit="+limit+"&offset="+String.valueOf(offset)+"&order=eventTimestamp%20DESC&include_count=true";
        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.GET, url, new JSONObject(), (Listener<JSONObject>) this, this);
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonRequest);
        //listView.setSelection(eventModelList.size()-6);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("Error:",error.toString());
    }

    @Override
    // the result from the API will be in JSON
    public void onResponse(JSONObject response) {
        try {
            // in JSON result of API request, resource contains array of events.
            JSONArray jsonArray = response.getJSONArray("resource");
            JSONObject jsonObject = response.getJSONObject("meta");
            total = jsonObject.getInt("count");
            Log.d("TOTAL", String.valueOf(total));
            for (int i=0; i < jsonArray.length(); i++){
                // each object will be parsed
                JSONObject resource = jsonArray.getJSONObject(i);
                // parsing every variable of events.
                int id = resource.getInt("id");
                int eventPrice = resource.getInt("eventPrice");
                String eventName = resource.getString("eventName");
                String imageUrl = resource.getString("ImageUrl");
                String eventUrl = resource.getString("eventUrl");
                String venueName = resource.getString("venueName");
                String address = resource.getString("address");
                String contact = resource.getString("contact");
                String eventDetails = resource.getString("eventDetails");
                Date eventTimestamp = Date.valueOf(resource.getString("eventTimestamp"));
                int venueId = resource.getInt("venueId");
                Log.d("ASU", "onResponse: "+eventName);
                // each parsed variable will be put for new data model
                EventModel data = new EventModel(id, venueId, eventPrice, eventName, imageUrl, eventUrl, venueName, address, contact, eventDetails, eventTimestamp);
                // each data model will be put into the list
                eventModelList.add(data);
                Log.d("DATA", data.toString());
            }
            Log.d("tot", "TOT: "+eventModelList.toString());
            // notifyDataSetChanged(), to change into another added data to list of data
            listViewAdapter.notifyDataSetChanged();
            // once the request is complete, the refresh animation is stopped
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}