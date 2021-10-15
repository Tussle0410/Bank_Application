package com.example.bank_application;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class login_activity extends AppCompatActivity {
    private EditText ID, Password;
    private String JsonString,IP_ADDRESS;
    private Button found_Password_Button, register_Button,login_Button;
    private CheckBox Save_ID;
    private Boolean Save_ID_Check;
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.login_page);//로그인 페이지 가져오기
        Context context=  this;
        IP_ADDRESS = ((databaseIP) getApplication()).getIP_Address();   //데이터 베이스 IP 가져오기
        ID = (EditText)findViewById(R.id.login_ID_input);           //페이지 요소들 선언
        Password = (EditText) findViewById(R.id.login_PW_input);
        Save_ID_Check = PreferenceManager.getBoolean(context,"check");
        Save_ID = (CheckBox) findViewById(R.id.login_saveID_check);
        if(Save_ID_Check){
            ID.setText(PreferenceManager.getString(context,"ID"));
            Save_ID.setChecked(true);
        }
        found_Password_Button = (Button) findViewById(R.id.password_find_button);
        found_Password_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent found_intent = new Intent(getApplicationContext(),found_pw_activity.class);
                startActivity(found_intent);
            }
        });
        register_Button = (Button) findViewById(R.id.register_button);
        register_Button.setOnClickListener(new View.OnClickListener() { //회원가입 페이지 이동
            @Override
            public void onClick(View v) {
                Intent register_intent = new Intent(getApplicationContext(),register_id_activity.class);
                startActivity(register_intent);
            }
        });
        login_Button = (Button) findViewById(R.id.login_button);
        login_Button.setOnClickListener(new View.OnClickListener() {        //로그인 버튼 선언 및 클릭 이벤트
            @Override
            public void onClick(View v) {
                if(ID.getText().toString().equals("")){
                    Toast.makeText(context, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                }else{
                    if(Password.getText().toString().equals("")){
                        Toast.makeText(context, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    }else{
                        if(Save_ID.isChecked()){
                            PreferenceManager.setBoolean(context,"check",Save_ID.isChecked());
                            PreferenceManager.setString(context,"ID",ID.getText().toString());
                        }else{
                            PreferenceManager.setBoolean(context,"check",Save_ID.isChecked());
                        }
                        login login = new login();
                        login.execute("http://" + IP_ADDRESS + "/bank/login.php",ID.getText().toString(),
                                Password.getText().toString());
                    }
                }
            }
        });

    }
    private class login extends AsyncTask<String,Void,String>{
        ProgressDialog progressDialog;
        String errMsg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(login_activity.this,"Please Wait",
                    null,true,true);
        }

        @Override
        protected String doInBackground(String... strings) {
            String databaseUrl = strings[0];
            String ID = strings[1];
            String PW = strings[2];
            String PostValue = "ID=" + ID + "&PW=" + PW;
            try {
                URL Url = new URL(databaseUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) Url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(PostValue.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int httpRequestCode = httpURLConnection.getResponseCode();
                InputStream inputStream;
                if(httpRequestCode == HttpURLConnection.HTTP_OK){
                    inputStream = httpURLConnection.getInputStream();
                }else{
                    inputStream = httpURLConnection.getErrorStream();
                }
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();
            }catch (Exception e){
                errMsg = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.equals("false")){
                Toast.makeText(login_activity.this,
                        "아이디와 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }else{
                JsonString = result;
                showResult();
            }
            progressDialog.dismiss();

        }
        protected void showResult(){
            String Tag_Json = "login";
            String Tag_ID = "ID";
            String Tag_Name = "Name";
            String Tag_Birth = "Birth";
            String Tag_Gender = "Gender";
            String Tag_Email ="Email";
            String Tag_addressMainKinds = "addressMainKinds";
            try {
                JSONObject jsonObject = new JSONObject(JsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(Tag_Json);
                JSONObject value = jsonArray.getJSONObject(0);
                Intent home_intent = new Intent(getApplicationContext(),home_nav_activity.class);
                home_intent.putExtra("ID",value.getString(Tag_ID));
                home_intent.putExtra("Name",value.getString(Tag_Name));
                home_intent.putExtra("Birth",value.getString(Tag_Birth));
                home_intent.putExtra("Gender",value.getString(Tag_Gender));
                home_intent.putExtra("Email",value.getString(Tag_Email));
                home_intent.putExtra("addressMainKinds",value.getString(Tag_addressMainKinds));
                startActivity(home_intent);
            }catch (Exception e){
                Log.d("PHP","에러발생" + e);
            }
        }
    }
}
