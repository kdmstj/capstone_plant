package com.example.capston_plant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class RegisterActivity extends AppCompatActivity {

    ImageButton btn_goBack;
    Button btn_Register;
    EditText et_id;
    EditText et_pw;
    EditText et_pw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        et_pw2 = findViewById(R.id.et_pw2);


        btn_goBack = findViewById(R.id.btn_goBack);
        btn_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(RegisterActivity.this, StartActivity.class);
                startActivity(intent2);
                finish();
            }
        });

        btn_Register = findViewById(R.id.btn_Register);
        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user_id = et_id.getText().toString();
                String user_pw = et_pw.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // ??????????????? ????????? ??????
                                Toast.makeText(getApplicationContext(),"?????? ????????? ?????????????????????.",Toast.LENGTH_SHORT).show();
                                SharedPreferences auto = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor autoLoginEdit = auto.edit();

                                autoLoginEdit.putString("user_id", user_id);
                                autoLoginEdit.putString("user_pw", user_pw);

                                autoLoginEdit.commit();


                                Intent intent = new Intent(RegisterActivity.this, SettingActivity.class);
                                startActivity(intent);
                            } else { // ??????????????? ????????? ??????
                                Toast.makeText(getApplicationContext(),"?????? ????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                // ????????? Volley??? ???????????? ????????? ???.
                RegisterRequest registerRequest = new RegisterRequest(user_id,user_pw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }
        });




    }
}