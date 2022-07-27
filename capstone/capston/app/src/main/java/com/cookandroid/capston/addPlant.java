package com.cookandroid.capston;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

public class addPlant extends Activity {
    //private static String IP_ADDRESS = "10.0.2.2";
    private static String IP_ADDRESS="togarden.dothome.co.kr";
    private static String TAG = "addplant";
    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageview;
    private Uri selectedImageUri;
    String id; Bitmap bm; String image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addplant);
        setTitle("to-gardeN");
        final Button upload = (Button) findViewById(R.id.등록);
        id = ((getset)getApplication()).getId();

        imageview = (ImageView)findViewById(R.id.addimage);
        Button 사진업로드 = (Button) findViewById(R.id.업로드);
        사진업로드.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);

            }
        });

        final EditText eplantname= (EditText) findViewById(R.id.plantname);
        final EditText ehope= (EditText) findViewById(R.id.hope);
        final CheckBox eexchange = (CheckBox) findViewById(R.id.exchange);
        final CheckBox eshare = (CheckBox) findViewById(R.id.share);

        eexchange.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(eexchange.isChecked()){
                    ehope.setEnabled(true);
                }
                else {
                    ehope.setEnabled(false);
                }
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (eplantname.length() != 0) {
                    String plantname = eplantname.getText().toString();
                    if (eexchange.isChecked() && eshare.isChecked()) {
                        String exchange = "교환";
                        String share = "나눔";
                        if(ehope.length()!=0){
                        String hope = ehope.getText().toString();
                        InsertData task = new InsertData();
                        task.execute(id, plantname, exchange, share, hope," ");
                        Toast.makeText(addPlant.this, "등록되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent myUpload = new Intent(addPlant.this, MainActivity.class);
                        startActivity(myUpload);}
                        else{
                            Toast.makeText(addPlant.this, "희망 교환 식물을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                        }
                    } else if (eexchange.isChecked()) {
                        String exchange = "교환";
                        String share = " X ";
                        if(ehope.length()!=0){
                            String hope = ehope.getText().toString();
                            InsertData task = new InsertData();
                            task.execute(id, plantname, exchange, share, hope," ");
                            Toast.makeText(addPlant.this, "등록되었습니다.", Toast.LENGTH_SHORT).show();
                            Intent myUpload = new Intent(addPlant.this, MainActivity.class);
                            startActivity(myUpload);}
                        else{
                            Toast.makeText(addPlant.this, "희망 교환 식물을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                        }
                    } else if (eshare.isChecked()) {
                        String exchange = " X ";
                        String share = "나눔";
                        String hope = " X ";
                        InsertData task = new InsertData();
                        task.execute(id, plantname, exchange, share, hope," ");
                        Toast.makeText(addPlant.this, "등록되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent myUpload = new Intent(addPlant.this, MainActivity.class);
                        startActivity(myUpload);
                    } else {
                        Toast.makeText(addPlant.this, "교환/나눔 여부를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(addPlant.this, "식물을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }});


}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);
            try {
            bm= resize(MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri));
                bitmapToByteArray(bm);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    public void bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); byte[] bytes = baos.toByteArray();
        image = byteArrayToBinaryString(bytes);
    }
    public static String byteArrayToBinaryString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; ++i) {
            sb.append(byteToBinaryString(b[i]));
        } return sb.toString();
    }
    // 바이너리 바이트를 스트링으로
    public static String byteToBinaryString(byte n) {
        StringBuilder sb = new StringBuilder("00000000");
        for (int bit = 0; bit < 8; bit++) { if (((n >> bit) & 1) > 0) {
            sb.setCharAt(7 - bit, '1');
        }
        } return sb.toString();
    }


    private Bitmap resize(Bitmap bm){
        Configuration config=getResources().getConfiguration();
        if(config.smallestScreenWidthDp>=800)
            bm = Bitmap.createScaledBitmap(bm, 400, 240, true);
        else if(config.smallestScreenWidthDp>=600)
            bm = Bitmap.createScaledBitmap(bm, 300, 180, true);
        else if(config.smallestScreenWidthDp>=400)
            bm = Bitmap.createScaledBitmap(bm, 200, 120, true);
        else if(config.smallestScreenWidthDp>=360)
            bm = Bitmap.createScaledBitmap(bm, 180, 108, true);
        else
            bm = Bitmap.createScaledBitmap(bm, 160, 96, true);
        return bm;
    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog; @Override protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(addPlant.this, "로딩중입니다.", null, true, true);
        }



        @Override
        protected String doInBackground(String... params) {
            String id= (String)params[0];
            String plantname= (String)params[1];
            String exchange= (String)params[2];
            String share= (String)params[3];
            String hope= (String)params[4];
            String image= (String)params[5];

            String serverURL = "http://" + IP_ADDRESS + "/insert.php";
            String postParameters = "id=" + id + "&plantname=" + plantname + "&exchange=" + exchange + "&share=" + share + "&hope=" + hope+ "&image=" + image;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

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
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }}}
