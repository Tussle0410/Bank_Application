package com.example.bank_application;

import android.app.ProgressDialog;
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
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class register_id_activity extends AppCompatActivity {
    private Button next_button; 
    private ImageButton back_button;
    private EditText ID;
    private String IP_Address;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_id_page);

        IP_Address = ((databaseIP)getApplication()).getIP_Address();        //IP 주소 가져오기
        ID = (EditText)findViewById(R.id.register_userID);

        back_button = (ImageButton) findViewById(R.id.register_id_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }           //뒤로가기 사용시 activity 종료
        });

        next_button = (Button) findViewById(R.id.register_id_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {           //다음 버튼 클릭시
                if(ID.getText().toString().equals("")){
                    Toast.makeText(register_id_activity.this,
                            "아이디를 입력해주세요",Toast.LENGTH_SHORT).show();
                }else {
                    ID_Check id_check = new ID_Check();         //데이터베이스 동일한 아이디 있나 확인 클래스 선언 및 실행
                    id_check.execute("http://" + IP_Address + "/bank/regCheckID.php", ID.getText().toString());
                }
            }
        });
    }
    private class ID_Check extends AsyncTask<String,Void,String> {      //동일한 아이디 확인 클래스
        ProgressDialog progressDialog;      
        String error_msg = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(register_id_activity.this,"Please Wait",null,
                    true,true); //진행중 잠시 기달려다라는 메세지
        }

        @Override
        protected String doInBackground(String... strings) {
            String DatabaseUrl = strings[0];
            String ID = strings[1];
            String PostValues = "ID=" + ID;         //PHP 코드에 보내는 정보

            try {
                URL url = new URL(DatabaseUrl);     //URL 설정
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();     //URL Connection 연결
                httpURLConnection.setReadTimeout(5000);         //5초 기다리고 읽지 못하면 종료
                httpURLConnection.setConnectTimeout(5000);      //5초 기다리고 연결 안되면 종료      
                httpURLConnection.setRequestMethod("POST");     //PHP 보내는 방식
                httpURLConnection.setDoInput(true);             //입력 허가
                httpURLConnection.connect();                    //HTTP 연결

                OutputStream outputStream = httpURLConnection.getOutputStream();    //HTTP outputStream 받기
                outputStream.write(PostValues.getBytes("UTF-8"));   //UTF-8 형식으로 값 받음
                outputStream.flush();                                           // 값 출력
                outputStream.close();                                           // outputStream 닫기

                int httpURLConnection_RequestCode = httpURLConnection.getResponseCode(); 
                InputStream inputStream;

                if(httpURLConnection_RequestCode==HttpURLConnection.HTTP_OK){   //requestCode=OK HTTP 결과값 얻음
                    inputStream = httpURLConnection.getInputStream();  
                }else{                                                          //requestCode!=OK 에러메세지 얻음
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");//Reader 설정
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);          //버퍼로 가져와서 일기

                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while((line =bufferedReader.readLine()) !=null){        //Line 값만큼 StringBuilder 저장
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch(Exception e){
                error_msg = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println(result);
            if(result.equals("true")){          //중복 없을시 ID 정보와 함께 다음페이지 이동
                Toast.makeText(register_id_activity.this,
                        "아이디가 중복되었습니다. \n 다른 아이디를 사용해주세요.",Toast.LENGTH_SHORT).show();
            }else{                  //중복시 Toast 메세지 띄우기
                Intent next_intent = new Intent(getApplicationContext(),register_email_activity.class);
                next_intent.putExtra("userID",ID.getText().toString());
                startActivity(next_intent);
            }
            progressDialog.dismiss();
        }
    }
}
