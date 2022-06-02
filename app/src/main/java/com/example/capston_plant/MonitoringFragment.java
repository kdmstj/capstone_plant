package com.example.capston_plant;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MonitoringFragment extends Fragment {

    private View view;
    private ImageButton btn_goBack;



    final static private String url = "http://112.170.208.72:8920/monitoring.php";

    String plant_owner;
    String plant_ID;
    WebView webView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //SharedPreferences 에서 정보 가져오기
        SharedPreferences auto = getActivity().getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
        plant_owner = auto.getString("user_id",null);
        plant_ID = auto.getString("plant_ID",null);
        System.out.println("plant_owner monitoring"+plant_owner);
        System.out.println("plant_id monitoring"+plant_ID);


    }

    class MyWeb extends WebViewClient {  //실제로 모든 일을 다 해주는 WebViewClient을 상속받아와 사용한다
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return super.shouldOverrideUrlLoading(view, url);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_monitoring, container, false);



        //webView 선언
        webView = view.findViewById(R.id.webView);
        webView.setWebViewClient(new MyWeb());


        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);


        webView.loadUrl("http://112.170.208.72:9000");

        //뒤로가기 버튼 구현
        btn_goBack = view.findViewById(R.id.btn_goBack);
        btn_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                transaction.replace(R.id.main_layout, homeFragment);
                transaction.commit();
            }
        });


        return view;
    }

}