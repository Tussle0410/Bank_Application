package com.example.bank_application;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class found_pw_activity extends AppCompatActivity {
    private ImageButton back_button;
    private Button pw_found_button,id_intent_button;
    private EditText ID,Email;
    private String JsonString,IP_ADDRESS;
    private register_email_activity register_email_activity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.found_pw_page);
        register_email_activity = new register_email_activity();    //CheckEmailForm 사용하기 위해 선언
        IP_ADDRESS = ((databaseIP)getApplication()).getIP_Address();    //데이터 베이스 IP 가져오기

        ID = (EditText) findViewById(R.id.found_pw_userID);
        Email = (EditText) findViewById(R.id.found_pw_email);
        back_button = (ImageButton) findViewById(R.id.found_pw_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {     //뒤로가기 선언 및 클릭 기능
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pw_found_button = (Button)findViewById(R.id.found_pw_button);
        pw_found_button.setOnClickListener(new View.OnClickListener() {      //PW 찾기 버튼 선언 및 클릭 기능
            @Override
            public void onClick(View v) {
                if(register_email_activity.checkEmailForm(Email.getText().toString())){
                    foundPW foundPW = new foundPW();
                    foundPW.execute("http://" + IP_ADDRESS + "/bank/foundPW.php",
                            ID.getText().toString(),Email.getText().toString());
                }else{
                    Toast.makeText(found_pw_activity.this,
                            "이메일 형식으로 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        id_intent_button = (Button) findViewById(R.id.found_pw_intent_button);
        id_intent_button.setOnClickListener(new View.OnClickListener() {        //ID 찾기 페이지로 이동
            @Override
            public void onClick(View v) {
                Intent found_id_intent = new Intent(getApplicationContext(),found_id_activity.class);
                startActivity(found_id_intent);
            }
        });
    }
    private void showDialog(String PW){          //PW 찾기 Dialog Method
        AlertDialog.Builder Dialog = new AlertDialog.Builder(found_pw_activity.this);
        Dialog.setTitle(ID.getText().toString()+"님의 비밀번호 찾기 결과입니다.");        //Dialog 제목
        Dialog.setMessage("\n"+PW +"\n\n 확인을 누르면 로그인 화면으로 넘어갑니다.");//Dialog 내용
        Dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {    //Dialog 확인 버튼시
                finishAffinity();
                Intent login_intent = new Intent(getApplicationContext(),login_activity.class);
                startActivity(login_intent);
            }
        });
        Dialog.show();      //Dialog 보여주기
    }
    private class foundPW extends AsyncTask<String, Void,String>{   //데이터베이스와 연결하여 PW 가져오기기
       ProgressDialog progressDialog;
        String errMsg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(found_pw_activity.this,"Please Wait",
                    null,true,true);
        }

        @Override
        protected String doInBackground(String... strings) {
            String databaseUrl = strings[0];
            String ID = strings[1];
            String Email = strings[2];
            String PostValue = "ID=" + ID + "&Email=" + Email;
            try {
                URL url = new URL(databaseUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
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
            System.out.println(result);
            if(result.equals("false")){
                Toast.makeText(found_pw_activity.this,
                        "아이디와 이메일이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }else{
                JsonString = result;
                showResult();
            }
            progressDialog.dismiss();
        }
        protected void showResult(){
            String Tag_Json = "foundPW";
            String Tag_PW = "PW";
            try {
                JSONObject jsonObject = new JSONObject(JsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(Tag_Json);
                JSONObject value = jsonArray.getJSONObject(0);
                String PW = value.getString(Tag_PW);
                showDialog(PW);
            }catch (Exception e){
                Log.d("PHP" , "에러발생=" + e);
            }
        }
    }
}
