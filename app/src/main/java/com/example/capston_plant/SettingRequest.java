package com.example.capston_plant;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SettingRequest extends StringRequest {

    final static private String URL = "http://112.170.208.72:8920/setting.php";
    private Map<String, String> map;

    public SettingRequest(String plant_ID, String plant_Name, String plant_Info, String plant_owner, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("plant_ID", plant_ID);
        map.put("plant_owner",plant_owner);
        map.put("plant_Name",plant_Name);
        map.put("plant_Info",plant_Info);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return map;
    }

}
