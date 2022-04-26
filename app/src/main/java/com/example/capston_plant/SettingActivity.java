package com.example.capston_plant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    EditText et_plantName;
    EditText et_plantInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent = getIntent();
        String user_id = intent.getStringExtra("user_id");

        et_plantName = findViewById(R.id.et_plantName);
        et_plantInfo = findViewById(R.id.et_plantInfo);


        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String plant_name = et_plantName.getText().toString();
                String plant_info = et_plantInfo.getText().toString();
                String plant_owner = user_id;

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(), "식물 정보 등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SettingActivity.this,MainActivity.class);
                                intent.putExtra("plant_owner",user_id);
                                startActivity(intent);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                SettingRequest settingRequest = new SettingRequest(plant_name,plant_info,plant_owner, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SettingActivity.this);
                queue.add(settingRequest);
            }
        });

    }
}