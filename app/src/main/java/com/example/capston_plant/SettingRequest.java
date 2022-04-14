package com.example.capston_plant;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SettingRequest extends StringRequest {

    final static private String URL = "http://112.170.208.72:8920/setting.php";
    private Map<String, String> map;

    public SettingRequest(String plant_name, String plant_info, String user_id, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("plant_owner",user_id);
        map.put("plant_name",plant_name);
        map.put("plant_info",plant_info);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return map;
    }

}
