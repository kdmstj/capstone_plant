package com.example.capston_plant;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LEDRequest extends StringRequest {


    final static private String URL = "http://112.170.208.72:8920/led.php";
    private Map<String, String> map;

    public LEDRequest(String btn_led, String plant_ID,Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("btn_led", btn_led);
        map.put("plant_ID",plant_ID);


    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
