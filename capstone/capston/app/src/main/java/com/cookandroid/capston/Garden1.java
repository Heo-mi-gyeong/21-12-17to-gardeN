package com.cookandroid.capston;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class Garden1 extends Activity {
    private static String TAG = "garden";
    private static final String TAG_JSON="togarden";
    private static final String TAG_NAME = "plantname";
    private static final String TAG_EXCHANGE = "exchange";
    private static final String TAG_SHARE = "share";
    private static final String TAG_HOPE = "hope";
    //private static String IP_ADDRESS = "10.0.2.2";
    private static String IP_ADDRESS="togarden.dothome.co.kr";
    Button updateplant1,updateplant2,updateplant3,updateplant4;
    String name1,name2,name3,name4  = "";
    String exchange1,exchange2,exchange3,exchange4="";
    String share1,share2,share3,share4="";
    String hope1,hope2,hope3,hope4="";
    int userImage; String userid; String mJsonString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garden1);
        setTitle("to-gardeN");

        userid=((getset)getApplication()).getUserId();
        userImage=((getset)getApplication()).getImage();

        Button 마이페이지 = (Button) findViewById(R.id.마이페이지);
        Button 대화하기 = (Button) findViewById(R.id.chatting);
        TextView textView=(TextView) findViewById(R.id.textView);
        updateplant1 = (Button) findViewById(R.id.plant1);
        updateplant2 = (Button) findViewById(R.id.plant2);
        updateplant3 = (Button) findViewById(R.id.plant3);
        updateplant4 = (Button) findViewById(R.id.plant4);
        ImageView imageView=(ImageView)findViewById(R.id.imageView);
        Button back = (Button) findViewById(R.id.back);

        GetData task = new GetData();
        task.execute("http://" + IP_ADDRESS + "/getjson.php",userid);

        imageView.setImageResource(userImage);

        textView.setText("\uD83C\uDF31  "+userid+"네 텃밭  \uD83C\uDF31");
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent 돌아가기= new Intent(getApplicationContext(), gardenList.class);
                startActivity(돌아가기);
            }
        });

        대화하기.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent 채팅방 = new Intent(getApplicationContext(), Chat.class);
                startActivity(채팅방);
            }
        });
        마이페이지.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent 마이페이지 = new Intent(getApplicationContext(), myPage.class);
                startActivity(마이페이지);
            }
        });
        updateplant1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OnClickHandler(v," 식물 이름 : " + name1 + "\n 나눔/교환 : " + exchange1 + " / "+share1 + "\n 희망 교환 식물 : " + hope1 +"\n");
            }
        });
        updateplant2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OnClickHandler(v," 식물 이름 : " + name2 + "\n 나눔/교환 : " + exchange2 + " / "+share2 + " \n 희망 교환 식물 : " + hope2 +"\n");
            }
        });
        updateplant3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OnClickHandler(v," 식물 이름 : " + name3 + "\n 나눔/교환 : " + exchange3 + " / "+share3 + " \n 희망 교환 식물 : " + hope3 +"\n");
            }
        });
        updateplant4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OnClickHandler(v," 식물 이름 : " + name4 + "\n 나눔/교환 : " + exchange4 + " / "+share4 + " \n 희망 교환 식물 : " + hope4 +"\n");
            }
        });

    }
    public void OnClickHandler(View view,String text)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = (View) View.inflate(
                Garden1.this, R.layout.customdialog, null);
        ImageView iv= (ImageView) dialogView
                .findViewById(R.id.image);
        TextView tv= (TextView) dialogView
                .findViewById(R.id.textView);
        builder.setTitle("식물 정보");
        iv.setImageResource(R.drawable.farmer2);
        tv.setText(text);
        builder.setIcon(R.drawable.plant);
        builder.setView(dialogView);

        builder.setNegativeButton("닫기", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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

                Log.d(TAG, "InsertData: Error ", e);
                return null;
            }

        }
    }private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            JSONObject item1 = jsonArray.getJSONObject(0);
            name1=item1.getString(TAG_NAME);
            exchange1=item1.getString(TAG_EXCHANGE);
            share1=item1.getString(TAG_SHARE);
            hope1=item1.getString(TAG_HOPE);
            //getBlob[0]=StringToBitMap(item1.getString(TAG_IMAGE));
            if (name1!=""){
                updateplant1.setVisibility(View.VISIBLE);
                updateplant1.setText(name1);
            }

            JSONObject item2 = jsonArray.getJSONObject(1);
            name2=item2.getString(TAG_NAME);
            exchange2=item2.getString(TAG_EXCHANGE);
            share2=item2.getString(TAG_SHARE);
            hope2=item2.getString(TAG_HOPE);
            //getBlob[1]=StringToBitMap(item2.getString(TAG_IMAGE));
            if (name2!=""){
                updateplant2.setVisibility(View.VISIBLE);
                updateplant2.setText(name2);
            }

            JSONObject item3 = jsonArray.getJSONObject(2);
            name3=item3.getString(TAG_NAME);
            exchange3=item3.getString(TAG_EXCHANGE);
            share3=item3.getString(TAG_SHARE);
            hope3=item3.getString(TAG_HOPE);
            //getBlob[2]=StringToBitMap(item3.getString(TAG_IMAGE));

            if (name3!=""){
                updateplant3.setVisibility(View.VISIBLE);
                updateplant3.setText(name3);
            }

            JSONObject item4 = jsonArray.getJSONObject(3);
            name4=item4.getString(TAG_NAME);
            exchange4=item4.getString(TAG_EXCHANGE);
            share4=item4.getString(TAG_SHARE);
            hope4=item4.getString(TAG_HOPE);
            //getBlob[3]=StringToBitMap(item4.getString(TAG_IMAGE));
            if (name4!=""){
                updateplant4.setVisibility(View.VISIBLE);
                updateplant4.setText(name4);
            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }


}