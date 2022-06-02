package com.example.capston_plant;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LEDget extends StringRequest {

    final static private String URL = "http://112.170.208.72:8920/ledget.php";
    private Map<String, String> map;

    public LEDget(String plant_ID, Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("plant_ID",plant_ID);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
