package com.cookandroid.capston;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class myPage extends Activity {
    private static final String TAG_JSON="togarden";
    private static final String TAG_COUNT = "count";
    private static final String TAG_PLACE = "place";
    //private static String IP_ADDRESS = "10.0.2.2";
    private static String IP_ADDRESS="togarden.dothome.co.kr";
    String id; int count; ImageView imageView;
    String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);
        Button back = (Button) findViewById(R.id.back);
        id = ((getset)getApplication()).getId();
        imageView=(ImageView)findViewById(R.id.imageView);

        GetData task = new GetData();
        task.execute("http://" + IP_ADDRESS + "/mypage.php",id);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent 돌아가기= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(돌아가기);
            }
        });

    }
    private class GetData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null){
                mJsonString = result;
                showResult();

            }
        }
        @Override
        protected String doInBackground(String... params) {

            String serverURL = (String)params[0];
            String id= (String)params[1];
            String postParameters = "id=" + id;

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                return null;
            }

        }
}private void showResult(){
        try {
            TextView 거래횟수 = (TextView)findViewById(R.id.count);
            TextView ID = (TextView)findViewById(R.id.ID);
            TextView LEVEL = (TextView)findViewById(R.id.level);
            TextView POINT = (TextView)findViewById(R.id.point);
            TextView PLACE = (TextView)findViewById(R.id.place);

            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            JSONObject item = jsonArray.getJSONObject(0);
            count=item.getInt(TAG_COUNT);
            String place = item.getString(TAG_PLACE);

            getset myCount = (getset) getApplication();
            myCount.setCount(count);


            ID.setText(id);
            거래횟수.setText(""+count);

            String POINTText=count*500+"P";
            POINT.setText(POINTText);

            String LEVELText = count / 3+"단계";
            LEVEL.setText(LEVELText);

            PLACE.setText(place);
            if (count/3==0){
                imageView.setImageResource(R.drawable.lv0);
            } else if (count/3==1){
                imageView.setImageResource(R.drawable.lv1);
            } else if (count/3==2){
                imageView.setImageResource(R.drawable.lv2);
            } else if (count/3==3){
                imageView.setImageResource(R.drawable.lv3);
            } else if (count/3==4){
                imageView.setImageResource(R.drawable.lv4);
            } else if (count/3==5){
                imageView.setImageResource(R.drawable.lv5);
            } else if (count/3==6){
                imageView.setImageResource(R.drawable.lv6);
            }else if (count/3==7){
                imageView.setImageResource(R.drawable.lv7);
            }

        } catch (JSONException e) {

        }

    }

}