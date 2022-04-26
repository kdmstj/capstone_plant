package com.example.capston_plant;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    Button btn_goRegister;
    ImageButton btn_goBack;
    EditText et_id;
    EditText et_pw;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        btn_goRegister = findViewById(R.id.btn_goRegister);
        btn_goBack = findViewById(R.id.btn_goBack);
        et_id = findViewById(R.id.et_id);
        et_pw  = findViewById(R.id.et_pw);

        btn_login.setOnClickListener(view -> {
            String user_id = et_id.getText().toString();
            String user_pw = et_pw.getText().toString();

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) { // 로그인에 성공한 경우
                            String user_id = jsonObject.getString("user_id");
                            Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                            check_setting();
                            Intent intent = new Intent(LoginActivity.this, SettingActivity.class);
                            intent.putExtra("user_id", user_id);
                            startActivity(intent);
                        } else { // 로그인에 실패한 경우
                            Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            LoginRequest loginRequest = new LoginRequest(user_id, user_pw, responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
        });


        btn_goBack.setOnClickListener(view -> {
            Intent intent2 = new Intent(LoginActivity.this, StartActivity.class);
            startActivity(intent2);
            finish();
        });


        btn_goRegister.setOnClickListener(view -> {
            Intent intent3 = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent3);
            finish();
        });

    }

    private static void check_setting(){

    }
}