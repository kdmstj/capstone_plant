package com.example.capston_plant;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    String plant_name;
    String TEMP;
    String HUMID;
    String SOIL;
    String TANK;
    String plant_owner;
    String plant_ID;
    Integer btn_led = 0;

    TextView tv_plantName;
    TextView tv_temp;
    TextView tv_humid;
    TextView tv_soil;
    TextView tv_tank;
    TextView tv_btnLed;
    TextView tv_led;

    ImageButton btn_Led;
    final static private String url = "http://112.170.208.72:8920/sensorinfo.php";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //SharedPreferences 에서 정보 가져오기
        SharedPreferences auto = getActivity().getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
        plant_owner = auto.getString("user_id",null);
        plant_ID = auto.getString("plant_ID",null);
        System.out.println("plant_owner home"+plant_owner);
        System.out.println("plant_id home"+plant_ID);
    }



    //onCreateView 는 레이아웃을 inflate 하는 메소드, view 객체를 얻을 수 있어, view 와 관련된 객체를 생성한다.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //GetData 클래스 task 객체 생성
        GetData task = new GetData();
        task.execute(url);


        //fragment_home xml 파일과 연결
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        tv_plantName = v.findViewById(R.id.tv_plantName);
        tv_temp = v.findViewById(R.id.tv_temp);
        tv_humid = v.findViewById(R.id.tv_humid);
        tv_soil = v.findViewById(R.id.tv_soil);
        tv_tank = v.findViewById(R.id.tv_tank);

        btn_Led = v.findViewById(R.id.btn_led);
        tv_btnLed = v.findViewById(R.id.tv_btnLed);
        tv_led = v.findViewById(R.id.tv_led);





        btn_Led.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("btn_led = " + btn_led);


                btn_led++;
                if(btn_led == 3){
                    btn_led = 0;
                }

                if(btn_led == 0){
                    tv_btnLed.setText("Turn On LED");
                    tv_led.setText("OFF 상태입니다.");
                    //OFF == 0
                }else if(btn_led == 1){
                    //ON == 1
                    tv_btnLed.setText("Turn On MooD");
                    tv_led.setText("ON 상태입니다.");
                }else if(btn_led == 2){
                    tv_btnLed.setText("Turn Off LED");
                    tv_led.setText("MOOD 상태입니다.");
                    //MOOD = 2
                }


                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                System.out.println("성공적으로 btn_led 수정함.");

                            }else{
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.

                LEDRequest ledRequest = new LEDRequest(String.valueOf(btn_led),plant_ID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(ledRequest);







            }
        });








        return v;

    }

    private void refresh(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
    }


    private class GetData extends AsyncTask<String, String, String>{
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

            String postParameters = "plant_ID="+plant_ID+"&plant_owner="+plant_owner;
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

            Log.d(TAG, " <<<<<onProgressUpdate>>>> ");

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray  jsonArray = jsonObject.getJSONArray("sensor");
                JSONObject item = jsonArray.getJSONObject(0);

                if(jsonArray != null){
                    plant_name = item.optString("PLANT_NAME","text on no value");
                    TEMP = item.optString("TEMP","text on no value");
                    HUMID = item.optString("HUMID","text on no value");
                    SOIL = item.optString("SOIL","text on no value");
                    TANK =item.optString("TANK","text on no value");

                    tv_plantName.setText(plant_name);
                    tv_temp.setText(TEMP);
                    tv_humid.setText(HUMID);
                    tv_soil.setText(SOIL);
                    tv_tank.setText(TANK);
                }else{
                    System.out.println("nononono");
                }

                Log.d(TAG, TEMP + "/" + HUMID+ "/" + SOIL +"/" + TANK +"/"+plant_name);

            } catch (Exception e) {
                Log.d(TAG, "showResult : ", e);
            }






        }
    }
}

