package com.cookandroid.capston;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
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

public class Users extends Activity {

    private WebView mWebView;
    private static final String TAG_JSON="togarden";
    private static final String TAG_COUNT = "count";
    /*private static String IP_ADDRESS = "10.0.2.2";*/
    private static String IP_ADDRESS="togarden.dothome.co.kr";
    Handler handler = new Handler();
    String id; int count; String JsonString;

    private final String webViewUrl = "http://togarden.dothome.co.kr/signIn.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);




        mWebView = findViewById(R.id.testWebview);

        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.addJavascriptInterface(new AndroidBridge(), "java");
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient() {
        });

        mWebView.loadUrl(webViewUrl);

    }

    private class AndroidBridge {

        @JavascriptInterface
        public void AlertMsg(final String arg) { // 웹뷰내의 페이지에서 호출하는 함수
            handler.post(new Runnable() {
                @Override
                public void run() {
                    id=arg;
                    GetData task = new GetData();
                    task.execute("http://" + IP_ADDRESS + "/mypage.php", id);
                    getset Id= (getset) getApplication();
                    Id.setId(id);
                    Toast.makeText(Users.this, id+"로 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }
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
            JSONObject jsonObject = new JSONObject(JsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            JSONObject item = jsonArray.getJSONObject(0);
            count=item.getInt(TAG_COUNT);

            getset mycount = (getset) getApplication();
            mycount.setCount(count);

            if(id!=""){
                Intent main= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
            }


        } catch (JSONException e) {

        }

    }

}