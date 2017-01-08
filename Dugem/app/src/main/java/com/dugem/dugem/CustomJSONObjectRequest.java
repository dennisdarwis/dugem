// Dennis Darwis 216280619
// Written for SIT207 Android Programing 2nd Assignment
package com.dugem.dugem;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dennisdarwis on 10/10/16.
 */
public class CustomJSONObjectRequest extends JsonObjectRequest {
    public CustomJSONObjectRequest(int method, String url, JSONObject jsonRequest,
                                   Response.Listener<JSONObject> listener,
                                   Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        headers.put("X-DreamFactory-Api-Key", "62220ea2b6d61eb7aca380d40801ffccbc08bec358c72843023f626774493ac9");
        //headers.put("X-DreamFactory-Session-Token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIsInVzZXJfaWQiOjIsImVtYWlsIjoicHVibGljQHB1YmxpYy5jb20iLCJmb3JldmVyIjp0cnVlLCJpc3MiOiJodHRwOlwvXC8xMDQuMTk5LjE1NS4xNVwvYXBpXC92MlwvdXNlclwvc2Vzc2lvbiIsImlhdCI6MTQ3MDM4MzczMCwiZXhwIjoxNTAxOTE5NzMwLCJuYmYiOjE0NzAzODM3MzAsImp0aSI6ImFlZWQ3YWRhYmI4N2I0ODBhNmIyYWRlYWM0YzQzZTU0In0.J35hKt384v25G3ylb547w0OAlJ4yYGWRxhknwm5sYhg");
        return headers;
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        return super.getRetryPolicy();
    }
}

