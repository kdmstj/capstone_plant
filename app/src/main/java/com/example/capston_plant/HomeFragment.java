package com.example.capston_plant;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HomeFragment extends Fragment {

    String plant_name;
    String TEMP;
    String HUMID;
    String SOIL;
    String TANK;

    TextView tv_plantName;
    TextView tv_temp;
    TextView tv_humid;
    TextView tv_soil;
    TextView tv_tank;

    final static private String url = "http://112.170.208.72:8920/sensorinfo.php";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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





        return v;

    }

    private class GetData extends AsyncTask<String, Void, String>{

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
            String serverURL = params[0];
            String get_json = "";

            //HTTP 메시지 본문에 포함되어 전송되기 때문에 따로 데이터를 준비해야 한다.
            //전송할 데이터는 “이름=값” 형식이며 여러 개를 보내야 할 경우에는 항목 사이에 &를 추가합니다.
            //여기에 적어준 이름을 나중에 PHP 에서 사용하여 값을 얻게 됩니다.


            try {
                // HttpURLConnection 클래스를 사용하여 POST 방식으로 데이터를 전송합니다.
                URL url = new URL(serverURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if (conn != null) {
                    conn.setConnectTimeout(20000);
                    conn.setUseCaches(false);
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        // 서버에서 읽어오기 위한 스트림 객체
                        InputStreamReader isr = new InputStreamReader(
                                conn.getInputStream());
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
                    }
                }
                get_json = Buffer.toString();
                Log.d(TAG, "get_json: " + get_json);



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
                JSONArray  jsonArray = jsonObject.getJSONArray("sensor");
                JSONObject item = jsonArray.getJSONObject(0);
                //JSONArray array = new JSONObject(result).getJSONArray(url);
                //JSONObject jsonObject = array.getJSONObject(0);
                if(jsonArray != null){
                    plant_name = item.optString("plant_name","text on no value");
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

