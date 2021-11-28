package com.example.bank_application;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class home_nav_activity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    nav_home_fragment nav_home_fragment;
    nav_financial_products_fragment nav_financial_products_fragment;
    nav_myasset_fragment nav_myasset_fragment;
    nav_mybank_fragment nav_mybank_fragment;
    String ID, Name, Birth, Gender, Email, addressMainKinds, JsonString;
    ArrayList<String> eventBanner , financeBanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_nav_page);
        String IP_ADDRESS = ((databaseIP)getApplication()).getIP_Address();
        ID = getIntent().getExtras().getString("ID");       //Intent 정보 받아오기
        Name = getIntent().getExtras().getString("Name");
        Birth = getIntent().getExtras().getString("Birth");
        Gender = getIntent().getExtras().getString("Gender");
        Email = getIntent().getExtras().getString("Email");
        addressMainKinds = getIntent().getExtras().getString("addressMainKinds");
        nav_home_fragment = new nav_home_fragment();                            //바텀 네비게이션 클릭시 이동할 Fragment 선언
        nav_financial_products_fragment = new nav_financial_products_fragment();
        nav_mybank_fragment = new nav_mybank_fragment();
        nav_myasset_fragment = new nav_myasset_fragment();
        Bundle myAssetBundle = new Bundle();
        myAssetBundle.putString("Name",Name);
        myAssetBundle.putString("ID",ID);
        nav_myasset_fragment.setArguments(myAssetBundle);
        Bundle myBankBundle = new Bundle();
        myBankBundle.putString("ID",ID);
        myBankBundle.putString("Name",Name);
        myBankBundle.putString("Birth",Birth);
        myBankBundle.putString("Email",Email);
        nav_mybank_fragment.setArguments(myBankBundle);
        Bundle financialBundle = new Bundle();
        httpConnect getBanner= new httpConnect();
        getBanner.execute("http://"+IP_ADDRESS+"/bank/getBanner.php","banner");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                httpConnect getAddressInfo = new httpConnect();
                getAddressInfo.execute("http://"+IP_ADDRESS+"/bank/getAddress.php","addressInfo",ID);
                financialBundle.putStringArrayList("financeBanner",financeBanner);
                nav_financial_products_fragment.setArguments(financialBundle);
            }
        },300);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.home_bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {   //바텀 네비게이션 클릭시 이벤트
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, nav_home_fragment).commit();    //홈으로 이동
                        return true;
                    case R.id.nav_financial_products:
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, nav_financial_products_fragment).commit(); //금융상품 이동
                        return true;
                    case R.id.nav_myAsset:
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, nav_myasset_fragment).commit();  //MY자산으로 이동
                        return true;
                    case R.id.nav_myBank:
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, nav_mybank_fragment).commit();   //MY뱅크로 이동
                        return true;
                }
                return false;
            }
        });
    }
    private class httpConnect extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errMsg;
        String kinds;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(home_nav_activity.this, "Please Wait", null,
                    true, true);
        }

        @Override
        protected String doInBackground(String... strings) {
            String databaseUrl = strings[0];
            String PostValues = "";
            if(strings[1].equals("addressInfo")) {
                kinds = "addressInfo";
                String userID = strings[2];
                PostValues = "userID=" + userID;
            }else if(strings[1].equals("banner")){
                kinds = "banner";
            }
            try {
                URL url = new URL(databaseUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(PostValues.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int httpRequestCode = httpURLConnection.getResponseCode();
                InputStream inputStream;
                if (httpRequestCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return sb.toString().trim();
            } catch (Exception e) {
                errMsg = e.toString();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JsonString = result;
            if(kinds.equals("addressInfo")){
                getAddressInfo();
            }else if(kinds.equals("banner")){
                getBanner();
            }
            progressDialog.dismiss();

        }

        protected void getAddressInfo() {
            String Tag_JSON = "address";
            String Tag_Money = "Money";
            String Tag_Address = "Address";
            String Tag_limit = "Limit";
            String Tag_cur_limit = "currentLimit";
            String Tag_addressName = "addressName";
            try {
                JSONObject jsonObject = new JSONObject(JsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(Tag_JSON);
                JSONObject values = jsonArray.getJSONObject(0);
                Bundle home_bundle = new Bundle();
                home_bundle.putString("ID", ID);
                home_bundle.putString("Name", Name);
                home_bundle.putString("Email",Email);
                home_bundle.putString("Address",values.getString(Tag_Address));
                home_bundle.putInt("Money",values.getInt(Tag_Money));
                home_bundle.putInt("Limit",values.getInt(Tag_limit));
                home_bundle.putInt("curLimit",values.getInt(Tag_cur_limit));
                home_bundle.putString("addressName",values.getString(Tag_addressName));
                home_bundle.putStringArrayList("eventBanner",eventBanner);
                home_bundle.putStringArrayList("financeBanner",financeBanner);
                nav_home_fragment.setArguments(home_bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, nav_home_fragment).commit();//처음화면 설정
            }catch (Exception e){
                Log.d("PHP","에러발생 " + e);
            }

        }
        protected void getBanner(){
            String Tag_JSON = "banner";
            String Tag_Route = "bannerRoute";
            String Tag_kinds = "kinds";
            eventBanner = new ArrayList<>();
            financeBanner = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(JsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(Tag_JSON);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject value = jsonArray.getJSONObject(i);
                    if(value.getString(Tag_kinds).equals("event")){
                        eventBanner.add(value.getString(Tag_Route));
                    }else if(value.getString(Tag_kinds).equals("finance")){
                        financeBanner.add(value.getString(Tag_Route));
                    }
                }
            }catch (Exception e){
                Log.d("PHP","에러발생 " + e);
            }
        }
    }
}
