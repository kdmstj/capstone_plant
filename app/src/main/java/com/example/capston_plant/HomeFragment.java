package com.example.capston_plant;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HomeFragment extends Fragment {

    String plant_name;
    String TEMP;
    String HUMID;
    String SOIL;
    String TANK;

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

        TextView tv_plantName = v.findViewById(R.id.tv_plantName);
        TextView tv_temp = v.findViewById(R.id.tv_temp);
        TextView tv_humid = v.findViewById(R.id.tv_humid);
        TextView tv_soil = v.findViewById(R.id.tv_soil);
        TextView tv_tank = v.findViewById(R.id.tv_tank);

        System.out.println(TEMP);
        System.out.println(HUMID);
        System.out.println(SOIL);
        System.out.println(TANK);
        System.out.println(plant_name);

        tv_plantName.setText(plant_name);
        tv_temp.setText(TEMP);
        tv_humid.setText(HUMID);
        tv_soil.setText(SOIL);
        tv_tank.setText(TANK);

        return v;

    }

    private class GetData extends AsyncTask<String, Void, String>{


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
            String get_json= "";

            //HTTP 메시지 본문에 포함되어 전송되기 때문에 따로 데이터를 준비해야 한다.
            //전송할 데이터는 “이름=값” 형식이며 여러 개를 보내야 할 경우에는 항목 사이에 &를 추가합니다.
            //여기에 적어준 이름을 나중에 PHP 에서 사용하여 값을 얻게 됩니다.


            try{
               // HttpURLConnection 클래스를 사용하여 POST 방식으로 데이터를 전송합니다.
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                //5초 안에 응답이 오지 않으면 예외처리
                httpURLConnection.setReadTimeout(5000);
                //5초 안에 연결이 되지 않으면 예외처리
                httpURLConnection.setConnectTimeout(5000);
                //요청 방식을 POST 방식으로 한다.
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                //전송할 데이터가 저장된 변수를 이곳에 입력합니다. 인코딩을 고려해줘야 합니다.
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();


                InputStream inputStream;
                //응답을 읽는다.
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    //정상적인 응답
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    //에러발생
                    inputStream = httpURLConnection.getErrorStream();
                }

                //StringBuilder 를 사용하여 수신되는 데이터를 저장한다.
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                //저장된 데이터를 스트링으로 변환하여 리턴한다.
                return sb.toString();

            } catch (Exception e){

                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONArray array = new JSONObject(result).getJSONArray(url);
                if (array != null) {
                    JSONObject jsonObject = array.getJSONObject(0);
                    TEMP = jsonObject.getString("TEMP");
                    HUMID = jsonObject.getString("HUMID");
                    SOIL = jsonObject.getString("SOIL");
                    TANK = jsonObject.getString("TANK");
                    plant_name = jsonObject.getString("plant_name");

                } else {
                }

            } catch (Exception e) {
            }
        }
    }
}

