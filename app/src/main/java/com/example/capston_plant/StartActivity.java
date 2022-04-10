package com.example.capston_plant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btn_goLogin = findViewById(R.id.btn_goLogin);
        Button btn_goRegister = findViewById(R.id.btn_goRegister);

        btn_goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(StartActivity.this,LoginActivity.class);
                startActivity(intent1);
            }
        });


        btn_goRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(intent2);
            }
        });


    }
}