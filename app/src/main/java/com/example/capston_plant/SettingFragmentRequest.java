package com.example.capston_plant;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SettingFragmentRequest extends StringRequest {

    final static private String URL = "http://112.170.208.72:8920/settingfragment.php";
    private Map<String, String> map;

    public SettingFragmentRequest(String plant_owner, String plant_name, String plant_info, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("plant_owner",plant_owner);
        map.put("plant_name",plant_name);
        map.put("plant_info",plant_info);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return map;
    }

}
