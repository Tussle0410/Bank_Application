package com.example.bank_application;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class register_pw_activity extends AppCompatActivity {
    private ImageButton back_button;
    private Button register_button;
    private EditText userPW;
    private String userID,Name,email,gender,birth,password,IP_ADDRESS;
    private String accountAddress="";
    boolean address_check;
    private CheckBox show_pw_check;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_pw_page);
        userID = getIntent().getExtras().getString("userID");       //회원가입 정보 받아오기
        Name = getIntent().getExtras().getString("name");
        email = getIntent().getExtras().getString("email");
        gender = getIntent().getExtras().getString("gender");
        birth = getIntent().getExtras().getString("birth");
        IP_ADDRESS = ((databaseIP)getApplication()).getIP_Address();
        userPW = (EditText) findViewById(R.id.register_userPW);

        show_pw_check = (CheckBox) findViewById(R.id.register_pw_show_check);
        show_pw_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    userPW.setInputType(1);
                }else if(!isChecked){
                    userPW.setInputType(0x00000081);
                }
            }
        });
        back_button = (ImageButton) findViewById(R.id.register_pw_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {     //뒤로가기 버튼 선언 및 클릭 이벤트
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        register_button = (Button) findViewById(R.id.register_pw_button);
        register_button.setOnClickListener(new View.OnClickListener() { //회원가입 완료 버튼 선언 및 클릭 이벤트
            @Override
            public void onClick(View v) {
                accountAddress = AccountAddress(IP_ADDRESS);
                Handler handler =  new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(userPW.getText().toString().equals("")){
                            Toast.makeText(register_pw_activity.this,
                                    "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }else if(address_check){
                            Register register = new Register();
                            register.execute("http://" + IP_ADDRESS + "/bank/register.php",userID,
                                    userPW.getText().toString(),Name,gender,email,birth,accountAddress);
                        }else{
                            Toast.makeText(register_pw_activity.this,
                                    "생성된 계좌가 중복되어 다시 한번 눌러주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },300);
            }
        });

    }
    private String AccountAddress(String IP_ADDRESS){   //계좌번호 랜덤 생성
        address_check=false;
        String address="";
        int[] temp = new int[4];
        int max_value = 999, min_value=100;
        Random random = new Random();
        temp[0] = random.nextInt(max_value-min_value+1) +min_value;
        max_value = 9999; min_value=1000;
        temp[1] = random.nextInt(max_value-min_value+1) + min_value;
        temp[2] = random.nextInt(max_value-min_value+1) + min_value;
        max_value=99; min_value=10;
        temp[3] = random.nextInt(max_value-min_value+1) + min_value;
        for(int i=0;i<temp.length;i++){
            address += String.valueOf(temp[i]); }
        addressCheck addressCheck = new addressCheck();
        addressCheck.execute("http://" + IP_ADDRESS + "/bank/accountAddressCheck.php",address);
        return address;
    }
    private class addressCheck extends AsyncTask<String,Void,String>{   //데이터베이스에 계좌번호 중복되는지 확인
        ProgressDialog progressDialog;
        String err_msg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(register_pw_activity.this,
                    "Please Wait",null,true,true);
        }

        @Override
        protected String doInBackground(String... strings) {
            String databaseUrl = strings[0];
            String address = strings[1];
            String PostValues = "address=" + address;
            try {
                URL url = new URL(databaseUrl);
                HttpURLConnection httpURLConnection =  (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(PostValues.getBytes("UTF-8"));
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
                while((line = bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();

            }catch (Exception e){
                err_msg = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.equals("true")){
                address_check = true;
            }
            progressDialog.dismiss();
        }
    }
    private class Register extends AsyncTask<String,Void,String>{   //데이터베이스 유저 추가하는 클래스
        ProgressDialog progressDialog;
        String err_msg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(register_pw_activity.this,"Please Wait",
                    null,true,true);
        }

        @Override
        protected String doInBackground(String... strings) {
            String databaseUrl = strings[0];
            String ID = strings[1];
            String PW = strings[2];
            String Name = strings[3];
            String Gender = strings[4];
            String Email = strings[5];
            String Birth = strings[6];
            String Address= strings[7];
            String PostValues = "ID=" + ID + "&PW=" + PW + "&Name=" + Name + "&Gender=" + Gender
                    + "&Email=" + Email + "&Birth=" + Birth + "&Address=" + Address;
            try {
                URL url = new URL(databaseUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(PostValues.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int httpRequestCode = httpURLConnection.getResponseCode();
                InputStream inputStream;
                if(httpRequestCode==HttpURLConnection.HTTP_OK){
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
                err_msg = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.equals("true")){
                Toast.makeText(register_pw_activity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                finishAffinity();           //지금까지 열렸던 회원가입 페이지 종료
                Intent login_intent = new Intent(getApplicationContext(),login_activity.class);
                startActivity(login_intent);
            }
            progressDialog.dismiss();
        }
    }
}
