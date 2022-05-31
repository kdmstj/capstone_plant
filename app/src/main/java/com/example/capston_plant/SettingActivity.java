package com.example.capston_plant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

public class SettingActivity extends AppCompatActivity {

    Button btn_save;
    EditText et_plantId;
    EditText et_plantName;
    EditText et_plantInfo;
    String plant_owner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);




        //SharedPreferences 에서 정보 가져오기
        SharedPreferences auto = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
        plant_owner = auto.getString("user_id",null);

        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    String plant_ID = jsonObject.getString("plant_ID");
                    if (success) { // 정보가 없는 경우 true


                    } else {//정보가 있는 경우 false

                        SharedPreferences auto = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor autoLoginEdit = auto.edit();

                        autoLoginEdit.putString("plant_owner", plant_owner);
                        autoLoginEdit.putString("plant_ID", plant_ID);

                        System.out.println("setting success"+success);
                        System.out.println("setting plant_id"+plant_ID);

                        autoLoginEdit.commit();


                        Intent intent = new Intent(SettingActivity.this,MainActivity.class);
                        startActivity(intent);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        CheckRequest checkRequest = new CheckRequest(plant_owner,responseListener2);
        RequestQueue queue1 = Volley.newRequestQueue(SettingActivity.this);
        // 서버로 Volley를 이용해서 요청을 함.
        queue1.add(checkRequest);





        et_plantName = findViewById(R.id.et_plantName);
        et_plantInfo = findViewById(R.id.et_plantInfo);
        et_plantId =  findViewById(R.id.et_plantID);


        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String plant_name = et_plantName.getText().toString();
                String plant_info = et_plantInfo.getText().toString();
                String plant_id = et_plantId.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            String plant_ID = jsonObject.getString("plant_id");
                            System.out.print("success"+success);
                            System.out.print("plant_id"+plant_ID);
                            if(success){
                                Toast.makeText(getApplicationContext(), "식물 정보 등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();

                                SharedPreferences auto = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor autoLoginEdit = auto.edit();

                                autoLoginEdit.putString("plant_owner", plant_owner);
                                autoLoginEdit.putString("plant_ID", plant_ID);

                                autoLoginEdit.commit();

                                Intent intent = new Intent(SettingActivity.this,MainActivity.class);
                                startActivity(intent);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                SettingRequest settingRequest = new SettingRequest(plant_id, plant_name,plant_info,plant_owner, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SettingActivity.this);
                queue.add(settingRequest);


            }
        });

    }



}