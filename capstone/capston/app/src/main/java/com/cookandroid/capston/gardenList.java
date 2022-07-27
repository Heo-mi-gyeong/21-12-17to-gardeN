package com.cookandroid.capston;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.view.menu.MenuView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import static android.os.Build.ID;

public class gardenList extends Activity {
    private static String TAG = "garden";
    private static final String TAG_JSON="togarden";
    private static final String TAG_USER="id";
    private static final String TAG_COUNT="count";
    private static final String TAG_PLACE="place";
    //private static String IP_ADDRESS = "10.0.2.2";
    private static String IP_ADDRESS="togarden.dothome.co.kr";
    String[] arrayId; int[] arrayImage;
    String id;String mJsonString;ListView listView;
    ImageView garden1; int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gardenlist);

        listView=findViewById(R.id.listview);
        Button back = (Button) findViewById(R.id.back);
        id = ((getset)getApplication()).getId();
        garden1=(ImageView)findViewById(R.id.garden1);


        GetData task = new GetData();
        task.execute("http://" + IP_ADDRESS + "/list.php",id);

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
            SingerAdapter adapter = new SingerAdapter();
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            arrayId=new String[jsonArray.length()];
            arrayImage=new int[jsonArray.length()];

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String userid = item.getString(TAG_USER);
                String place = item.getString(TAG_PLACE);
                count=item.getInt(TAG_COUNT)/3;
                arrayId[i]=userid;
                if (count==0){
                    adapter.addItem(new item(userid+" 네 텃밭","✨ Lv. "+count + " / \uD83C\uDFE0 " + place,R.drawable.lv0));
                    arrayImage[i]=R.drawable.lv0;
                } else if (count<=1){
                    adapter.addItem(new item(userid+" 네 텃밭","✨ Lv. "+count+ " / \uD83C\uDFE0 " + place,R.drawable.lv1));
                    arrayImage[i]=R.drawable.lv1;
                } else if (count<=2){
                    adapter.addItem(new item(userid+" 네 텃밭","✨ Lv. "+count+ " / \uD83C\uDFE0 " + place,R.drawable.lv2));
                    arrayImage[i]=R.drawable.lv2;
                } else if (count<=3){
                    adapter.addItem(new item(userid+" 네 텃밭","✨ Lv. "+count+ " / \uD83C\uDFE0 " + place,R.drawable.lv3));
                    arrayImage[i]=R.drawable.lv3;
                } else if (count<=4){
                    adapter.addItem(new item(userid+" 네 텃밭","✨ Lv. "+count+ " / \uD83C\uDFE0 " + place,R.drawable.lv4));
                    arrayImage[i]=R.drawable.lv4;
                } else if (count<=5){
                    adapter.addItem(new item(userid+" 네 텃밭","✨ Lv. "+count+ " / \uD83C\uDFE0 " + place,R.drawable.lv5));
                    arrayImage[i]=R.drawable.lv5;
                } else if (count<=6){
                    adapter.addItem(new item(userid+" 네 텃밭","✨ Lv. "+count+ " / \uD83C\uDFE0 " + place,R.drawable.lv6));
                    arrayImage[i]=R.drawable.lv6;
                }else if (count<=7){
                    adapter.addItem(new item(userid+" 네 텃밭","✨ Lv. "+count+ " / \uD83C\uDFE0 " + place,R.drawable.lv7));
                    arrayImage[i]=R.drawable.lv7;
                }
            }
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View v, int position, long id) {
                    if (position>=0) {
                        getset userid = (getset) getApplication();
                        userid.setUserId(arrayId[position]);
                        getset userimageview= (getset) getApplication();
                        userimageview.setImage(arrayImage[position]);
                        Intent intent = new Intent(getApplicationContext(), Garden1.class);
                        startActivity(intent);
                    }
                }
            });


        } catch (JSONException e) {
        }

    }
    class SingerAdapter extends BaseAdapter{
        ArrayList<item> items = new ArrayList<item>();

        @Override
        public int getCount(){
            return items.size();
        }

        public void addItem(item item){
            items.add(item);
        }

        @Override
        public Object getItem(int position){
            return items.get(position);
        }

        @Override
        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            itemView singerItemView = null;

            if(convertView==null){
                singerItemView=new itemView(getApplicationContext());
            } else{
                singerItemView=(itemView) convertView;
            }
            item item =items.get(position);
            singerItemView.setName(item.getName());
            singerItemView.setMobile(item.getMobile());
            singerItemView.setImage(item.getResld());
            return singerItemView;
        }
    }
}

