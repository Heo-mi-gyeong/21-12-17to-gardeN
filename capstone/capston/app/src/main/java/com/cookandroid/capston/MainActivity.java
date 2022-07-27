package com.cookandroid.capston;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    private static String TAG = "garden";
    private static final String TAG_JSON="togarden";
    private static final String TAG_NAME = "plantname";
    /*private static String IP_ADDRESS = "10.0.2.2";*/
    private static String IP_ADDRESS = "togarden.dothome.co.kr";
    Button updateplant1,updateplant2,updateplant3,updateplant4,식물추가;
    int count; String id; String JsonString;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("to-gardeN");

        Button 텃밭구경 = (Button) findViewById(R.id.텃밭구경);
        Button 마이페이지 = (Button) findViewById(R.id.마이페이지);
        Button 채팅방 = (Button) findViewById(R.id.채팅방);
        Button gps = (Button) findViewById(R.id.gps);
        식물추가 = (Button) findViewById(R.id.식물추가);
        updateplant1 = (Button) findViewById(R.id.plant1);
        updateplant2 = (Button) findViewById(R.id.plant2);
        updateplant3 = (Button) findViewById(R.id.plant3);
        updateplant4 = (Button) findViewById(R.id.plant4);
        imageView=(ImageView)findViewById(R.id.imageView);
        id = ((getset)getApplication()).getId();
        count = ((getset)getApplication()).getCount();

        GetData task = new GetData();
        task.execute("http://" + IP_ADDRESS + "/getjson.php",id);

        텃밭구경.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent 텃밭구경 = new Intent(getApplicationContext(), gardenList.class);
                startActivity(텃밭구경);
            }
        });
        마이페이지.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent 마이페이지 = new Intent(getApplicationContext(), myPage.class);
                startActivity(마이페이지);
            }
        });
        식물추가.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent 식물추가 = new Intent(getApplicationContext(), addPlant.class);
                startActivity(식물추가);
            }
        });
        채팅방.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent 채팅방= new Intent(getApplicationContext(), chatList.class);
                startActivity(채팅방);
            }
        });
        gps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent gps= new Intent(getApplicationContext(), Place.class);
                startActivity(gps);
            }
        });
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
        updateplant1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                ad.setIcon(R.drawable.plant);
                ad.setTitle("확인");
                ad.setMessage("거래를 완료하시겠습니까?");
                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        count=count+1;
                        String udcount = String.valueOf(count);
                        String buttonText1 = updateplant1.getText().toString();
                        Toast.makeText(MainActivity.this, buttonText1+" 거래가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        GetData2 task1 = new GetData2();
                        task1.execute("http://" + IP_ADDRESS + "/update.php",id,udcount);
                        GetData2 task = new GetData2();
                        task.execute("http://" + IP_ADDRESS + "/delete.php",id, buttonText1);
                        updateplant1.setVisibility(View.INVISIBLE);
                        dialog.dismiss();
                    }
                });
                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();

            }
        });
        updateplant2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                ad.setIcon(R.drawable.plant);
                ad.setTitle("확인");
                ad.setMessage("거래를 완료하시겠습니까?");
                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        count=count+1;
                        String udcount = String.valueOf(count);
                        String buttonText1 = updateplant2.getText().toString();
                        Toast.makeText(MainActivity.this, buttonText1+" 거래가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        GetData2 task1 = new GetData2();
                        task1.execute("http://" + IP_ADDRESS + "/update.php",id,udcount);
                        GetData2 task = new GetData2();
                        task.execute("http://" + IP_ADDRESS + "/delete.php",id,buttonText1);

                        updateplant2.setVisibility(View.INVISIBLE);
                        dialog.dismiss();
                    }
                });
                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();

            }
        });
        updateplant3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                ad.setIcon(R.drawable.plant);
                ad.setTitle("확인");
                ad.setMessage("거래를 완료하시겠습니까?");
                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        count=count+1;
                        String udcount = String.valueOf(count);
                        String buttonText1 = updateplant2.getText().toString();
                        Toast.makeText(MainActivity.this, buttonText1+" 거래가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        GetData2 task1 = new GetData2();
                        task1.execute("http://" + IP_ADDRESS + "/update.php",id,udcount);
                        GetData2 task = new GetData2();
                        task.execute("http://" + IP_ADDRESS + "/delete.php",id,buttonText1);
                        updateplant3.setVisibility(View.INVISIBLE);
                        dialog.dismiss();
                    }
                });
                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();

            }
        });
        updateplant4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                ad.setIcon(R.drawable.plant);
                ad.setTitle("확인");
                ad.setMessage("거래를 완료하시겠습니까?");
                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        count=count+1;
                        String udcount = String.valueOf(count);
                        String buttonText1 = updateplant2.getText().toString();
                        Toast.makeText(MainActivity.this, buttonText1+" 거래가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        GetData2 task1 = new GetData2();
                        task1.execute("http://" + IP_ADDRESS + "/update.php",id,udcount);
                        GetData2 task = new GetData2();
                        task.execute("http://" + IP_ADDRESS + "/delete.php",id,buttonText1);
                        updateplant4.setVisibility(View.INVISIBLE);
                        dialog.dismiss();
                    }
                });
                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();

            }
        });
        }
    private class GetData extends AsyncTask<String, Void, String>{
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
    }
    private class GetData2 extends AsyncTask<String, Void, String>{
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
        @Override
        protected String doInBackground(String... params) {

            String serverURL = (String)params[0];
            String id= (String)params[1];
            String count= (String)params[2];
            String postParameters = "id=" + id+ "&count=" + count;

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

                Log.d(TAG, "InsertData: Error ", e);
                return null;
            }

        }
    }private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(JsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            String name1,name2,name3,name4  = "";

            JSONObject item1 = jsonArray.getJSONObject(0);
            name1=item1.getString(TAG_NAME);
            if (name1!=""){
                updateplant1.setVisibility(View.VISIBLE);
                updateplant1.setText(name1);
            }


            JSONObject item2 = jsonArray.getJSONObject(1);
            name2=item2.getString(TAG_NAME);
            if (name2!=""){
                updateplant2.setVisibility(View.VISIBLE);
                updateplant2.setText(name2);
            }

            JSONObject item3 = jsonArray.getJSONObject(2);
            name3=item3.getString(TAG_NAME);
            if (name3!=""){
                updateplant3.setVisibility(View.VISIBLE);
                updateplant3.setText(name3);
            }

            JSONObject item4 = jsonArray.getJSONObject(3);
            name4=item4.getString(TAG_NAME);
            if (name4!=""){
                updateplant3.setVisibility(View.VISIBLE);
                updateplant3.setText(name4);
            }



        } catch (JSONException e) {

        }

    }

}

