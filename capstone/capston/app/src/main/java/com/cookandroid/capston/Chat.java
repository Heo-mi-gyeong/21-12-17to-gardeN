package com.cookandroid.capston;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Chat extends Activity {
    private static final String TAG_JSON="togarden";
    private static final String TAG_ID = "users";
    private static final String TAG_TEXT = "text";
    //private static String IP_ADDRESS = "10.0.2.2";
    private static String IP_ADDRESS="togarden.dothome.co.kr";
    String userid;String id; String JsonString;
    TextView conv; Button 전송;
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView; String text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        userid = ((getset)getApplication()).getUserId();
        id = ((getset)getApplication()).getId();
        TextView 상대방 = (TextView)findViewById(R.id.상대방);
        conv = (EditText)findViewById(R.id.conv);
        전송 = (Button)findViewById(R.id.전송);
        상대방.setText(userid);

        mlistView = (ListView) findViewById(R.id.listview);
        mArrayList = new ArrayList<>();

        Chat.GetData task = new Chat.GetData();
        task.execute("http://" + IP_ADDRESS + "/selectChattingTable.php",id,userid);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        전송.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                text = conv.getText().toString();
                if(text.getBytes().length > 0){
                    GetData2 task2 = new GetData2();
                    task2.execute("http://" + IP_ADDRESS + "/chatting.php",id,userid,text);
                    mArrayList.clear();
                    Chat.GetData task = new Chat.GetData();
                    task.execute("http://" + IP_ADDRESS + "/selectChattingTable.php",id,userid);
                    conv.setText("");
                }
            }
        });
    }

    private class GetData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null){
                JsonString = result;
                showResult();
            }
        }
        @Override
        protected String doInBackground(String... params) {

            String serverURL = (String)params[0];
            String id= (String)params[1];
            String userid= (String)params[2];
            String postParameters = "id=" + id +"&userid=" + userid;

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
    }private class GetData2 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
        @Override
        protected String doInBackground(String... params) {

            String serverURL = (String) params[0];
            String id = (String) params[1];
            String userid = (String) params[2];
            String text = (String) params[3];
            String postParameters = "id=" + id + "&userid=" + userid + "&text=" + text;

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
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
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

            JSONObject jsonObject = new JSONObject(JsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);
                String id = item.getString(TAG_ID);
                String text = item.getString(TAG_TEXT);
                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_ID, id);
                hashMap.put(TAG_TEXT, text);

                mArrayList.add(hashMap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    Chat.this, mArrayList, R.layout.listviewchat,
                    new String[]{TAG_ID, TAG_TEXT},
                    new int[]{R.id.send, R.id.content}
            );

            mlistView.setAdapter(adapter);


        } catch (JSONException e) {

        }

    }
}
