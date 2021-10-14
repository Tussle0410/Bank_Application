package com.example.bank_application;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

public class found_id_activity extends AppCompatActivity {
    private ImageButton back_button;
    private Button found_id_button;
    private EditText name,birth;
    private String JsonString,IP_ADDRESS;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.found_id_page);
        IP_ADDRESS = ((databaseIP) getApplication()).getIP_Address();
        name = (EditText) findViewById(R.id.found_id_name);
        birth = (EditText) findViewById(R.id.found_id_birth);

        back_button = (ImageButton) findViewById(R.id.found_id_back_button);        //뒤로가기 버튼 선언
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        found_id_button = (Button)findViewById(R.id.found_id_button);
        found_id_button.setOnClickListener(new View.OnClickListener() {     //ID 찾기 버튼 선언
            @Override
            public void onClick(View v) {
                int check=0;
                for (int i=0;i<birth.length();i++){
                    if(birth.getText().toString().charAt(i)=='-'){
                        check++;
                    }
                }
                if(check==2) {
                    foundID foundID = new foundID();
                    foundID.execute("http://" + IP_ADDRESS + "/bank/foundID.php",
                            name.getText().toString(), birth.getText().toString());
                }else{
                    Toast.makeText(found_id_activity.this,
                            "생년월일을 예시처럼 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void showDialog(String[] item){      //ID 찾기 클릭시 사용되는 Dialog Method
        AlertDialog.Builder Dialog = new AlertDialog.Builder(found_id_activity.this);   //Dialog 생성
        Dialog.setTitle("해당 정보의 아이디 목록입니다.");       //Dialog 제목
        Dialog.setItems(item, null);
        Dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {        //Dialog 확인버튼 클릭시
                        finish();
                    }
        });
        Dialog.show();          //Dialog 출력
    }
    private class foundID extends AsyncTask<String,Void,String>{
        ProgressDialog progressDialog;
        String errMsg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(found_id_activity.this,"Please Wait",
                    null,true,true);
        }

        @Override
        protected String doInBackground(String... strings) {
            String databaseUrl = strings[0];
            String Name = strings[1];
            String Birth = strings[2];
            String PostValue = "name=" + Name + "&birth=" + Birth;

            try {
                URL url = new URL(databaseUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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
                if(httpRequestCode==HttpURLConnection.HTTP_OK){
                    inputStream = httpURLConnection.getInputStream();
                }else{
                    inputStream = httpURLConnection.getErrorStream() ;}

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
            }catch(Exception e){
                errMsg = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.equals("false")){
                Toast.makeText(found_id_activity.this,
                        "해당하는 아이디가 없습니다.", Toast.LENGTH_SHORT).show();
            }else{
                JsonString = result;
                System.out.println(result);
                showResult();
            }
            progressDialog.dismiss();

        }
        protected void showResult(){
            String Tag_Json = "foundID";
            String Tag_ID = "ID";
            try {
                JSONObject jsonObject = new JSONObject(JsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(Tag_Json);
                String[] value_arr = new String[jsonArray.length()+1];
                value_arr[0] ="확인을 누르면 비밀번호 찾기로 넘어갑니다";
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject value = jsonArray.getJSONObject(i);
                    value_arr[i+1] = value.getString(Tag_ID);
                }
                showDialog(value_arr);
            }catch(Exception e){
                Log.d("PHP","에러발생=" + e);
            }
        }
    }
}
