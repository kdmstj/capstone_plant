package com.example.capston_plant;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.net.URI;
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

        //GetData 클래스 task 객체 생성
        MonitoringFragment.GetData task = new MonitoringFragment.GetData();
        task.execute(url);

        //webView 선언
        webView = view.findViewById(R.id.webView);
        webView.setWebViewClient(new MyWeb());

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



    private class GetData extends AsyncTask<String, String, String> {
        //params , progress , result
        //Params : doInBackground() 함수의 파라미터로 쓰인다. 사용자가 execute(Params) 함수를 호출하면서 넘기는 파라미터이다.
        //
        //Progress : onProgressUpdate() 함수의 파라미터로 쓰인다.
        //
        //Result : doInBackground() 백그라운드에서 계산한 결과의 반환값의 유형을 정의한다. onPostExecute() 의 파라미터로 사용된다. 그러니까 doInBackground() 의 반환값을 onPostExecute() 파라미터로 받는 것이다.

        StringBuffer Buffer = new StringBuffer();

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

        }


        //백그라운드에서 발생하는 코드
        @Override
        protected String doInBackground(String... params) {



            //서버에 있는  PHP 파일을 실행시키고 응답을 저장하고 스트링으로 변환하여  리턴한다.
            //POST 방식으로 데이터 전달시에는 데이터가 주소에 직접 입력되지 않는다.
            String serverURL = (String)params[0];

            String postParameters = "plant_ID="+plant_ID;
            String get_json = "";

            //HTTP 메시지 본문에 포함되어 전송되기 때문에 따로 데이터를 준비해야 한다.
            //전송할 데이터는 “이름=값” 형식이며 여러 개를 보내야 할 경우에는 항목 사이에 &를 추가합니다.
            //여기에 적어준 이름을 나중에 PHP 에서 사용하여 값을 얻게 됩니다.


            try {
                // HttpURLConnection 클래스를 사용하여 POST 방식으로 데이터를 전송합니다.
                URL url = new URL(serverURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setReadTimeout(5000); //5초안에 응답이 오지 않으면 예외가 발생한다.
                conn.setConnectTimeout(5000);   //5초안에 연결이 안되면 예외가 발생한다.
                conn.setRequestMethod("POST"); //요청 방식을 POST로 한다.
                conn.connect();

                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8")); //전송할 데이터가 저장된 변수를 이곳에 입력한다. 인코딩을 고려해줘야 한다.
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = conn.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;

                if (conn != null) {
                    //응답을 읽는다.

                    //정상적인 응답 데이터
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        // 서버에서 읽어오기 위한 스트림 객체
                        //정상적인 응답 데이터
                        inputStream = conn.getInputStream();

                        InputStreamReader isr = new InputStreamReader(inputStream,"UTF-8");
                        // 줄단위로 읽어오기 위해 BufferReader로 감싼다.
                        BufferedReader br = new BufferedReader(isr);
                        // 반복문 돌면서읽어오기
                        while (true) {
                            String line = br.readLine();
                            if (line == null) {
                                break;
                            }
                            Buffer.append(line);
                        }
                        br.close();
                        conn.disconnect();
                    }else{
                        //에러 발생
                        inputStream = conn.getErrorStream();
                    }
                }
                get_json = Buffer.toString();
                Log.d(TAG, "get_json: " + get_json);
                Thread.sleep(10);



            } catch (Exception e) {
                Log.e("에러 ", e.getMessage());
            }
            return get_json;


        }







        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.d(TAG, " <<<<<onPostExecute>>>> ");

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("sensor");
                JSONObject item = jsonArray.getJSONObject(0);

                if(jsonArray != null){

                    String SAVE_PATH = item.optString("SAVE_PATH","text on no value");
                    webView.loadUrl(SAVE_PATH);

                }else{
                    System.out.println("nononono");
                }


            } catch (Exception e) {
                Log.d(TAG, "showResult : ", e);
            }






        }
    }
}