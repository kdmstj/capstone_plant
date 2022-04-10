package com.example.capston_plant;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    Button btn_goRegister;
    ImageButton btn_goBack;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        btn_goRegister = findViewById(R.id.btn_goRegister);
        btn_goBack = findViewById(R.id.btn_goBack);

        btn_login.setOnClickListener(view -> {
            Intent intent1 = new Intent(LoginActivity.this, SettingActivity.class);
            startActivity(intent1);
            finish();
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
}