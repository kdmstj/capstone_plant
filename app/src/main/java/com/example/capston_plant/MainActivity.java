package com.example.capston_plant;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    MonitoringFragment monitoringFragment;
    GalleryFragment galleryFragment;
    SettingFragment settingFragment;

    String plant_owner;
    String plant_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SharedPreferences 에서 정보 가져오기
        SharedPreferences auto = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
        plant_owner = auto.getString("user_id",null);
        plant_id = auto.getString("plant_id",null);
        System.out.println("plant_owner"+plant_owner);
        System.out.println("plant_id"+plant_id);




        //초기 세팅
        init();

        Bundle bundle1 = new Bundle();
        bundle1.putString("plant_owner",plant_owner);


        //bottomNavigation의 아이콘을 선택했을 때 원하는 프레그먼트가 띄어질 수 있도록 리스너를 추가한다.
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.tab1: {
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction fmt = fm.beginTransaction();
                        //HomeFragment.setArguments(bundle1);
                        fmt.replace(R.id.main_layout, new HomeFragment()).commit();

                        return true;
                    }
                    case R.id.tab2: {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_layout, new MonitoringFragment())
                                .commit();
                        return true;
                    }
                    case R.id.tab3: {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_layout, new GalleryFragment())
                                .commit();
                        return true;
                    }
                    case R.id.tab4: {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_layout, new SettingFragment())
                                .commit();
                        return true;
                    }

                }
                return false;
            }
        });





    }

    private void init(){
        //fragment 생성
        homeFragment = new HomeFragment();
        monitoringFragment = new MonitoringFragment();
        galleryFragment = new GalleryFragment();
        settingFragment = new SettingFragment();

        //bottomNavigationView 생성
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        //제일 처음 띄어줄 뷰 세팅
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,homeFragment).commitAllowingStateLoss();

    }





}




