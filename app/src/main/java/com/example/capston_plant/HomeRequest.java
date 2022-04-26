package com.example.capston_plant;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class HomeRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://112.170.208.72:8920/login.php";
    private Map<String, String> map;


    public HomeRequest(String plant_owner, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("plant_owner",plant_owner);


    }



    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
