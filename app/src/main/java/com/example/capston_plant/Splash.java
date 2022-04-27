package com.example.capston_plant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class Splash extends AppCompatActivity {

    String user_id;
    String user_pw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        SharedPreferences auto = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
        user_id = auto.getString("user_id",null);
        user_pw = auto.getString("user_pw", null);



        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(user_id != null && user_pw != null){//기존에 로그인을 했을 경우 자동 로그인으로 넘어간다.
                    Toast.makeText(getApplicationContext(),user_id+"님 자동로그인 입니다!",Toast.LENGTH_SHORT).show();
                    Intent intent4 = new Intent(Splash.this, SettingActivity.class);
                    intent4.putExtra("plant_owner",user_id);
                    startActivity(intent4);
                    finish();


                }else if(user_id == null && user_pw == null){
                    Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },3000);
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}