package com.example.capston_plant;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //초기 세팅
        init();


        //bottomNavigation의 아이콘을 선택했을 때 원하는 프레그먼트가 띄어질 수 있도록 리스너를 추가한다.
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.tab1: {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_layout, new HomeFragment())
                                .commit();
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




