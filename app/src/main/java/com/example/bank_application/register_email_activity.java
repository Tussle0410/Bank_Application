package com.example.bank_application;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.IpPrefix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class register_email_activity extends AppCompatActivity{
    private ImageButton back_button;
    private Spinner email_spinner;
    private Button next_button;
    private EditText email_ID,email_address_edit;
    private String email,email_address,userID,IP_Address;
    private TextView address_text;
    private boolean email_address_direct_check=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_email_page);

        IP_Address = ((databaseIP) getApplication()).getIP_Address();
        userID = getIntent().getExtras().getString("userID");       //ID 정보 받기
        email_ID = (EditText)findViewById(R.id.register_email);
        address_text = (TextView)findViewById(R.id.register_email_address_text);
        email_address_edit = (EditText)findViewById(R.id.register_email_address);

        back_button = (ImageButton)findViewById(R.id.register_email_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {     //뒤로가기 버튼시 activity 종료!
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        email_spinner = (Spinner) findViewById(R.id.register_email_spinner);
        ArrayAdapter spinner_Adapter = ArrayAdapter.createFromResource(             //스피너 Adapter
                this,R.array.email_spinner, android.R.layout.simple_spinner_dropdown_item);
        spinner_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        email_spinner.setAdapter(spinner_Adapter);
        email_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(email_spinner.getItemAtPosition(position).equals("직접입력")){
                    address_text.setVisibility(View.VISIBLE);           //직접 입력시 EditView 보이게 설정
                    email_address_edit.setVisibility(View.VISIBLE);
                    email_address_direct_check= true;
                }else{
                    email_address = String.valueOf(email_spinner.getItemAtPosition(position));
                    address_text.setVisibility(View.INVISIBLE);         //직접 입력 아닐시 EditView 숨김
                    email_address_edit.setVisibility(View.INVISIBLE);
                    email_address_direct_check=false;
                    email_address  = email_spinner.getItemAtPosition(position).toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        next_button = (Button)findViewById(R.id.register_email_button);
        next_button.setOnClickListener(new View.OnClickListener() {         //다음 회윈가입 페이지로 넘어감
            @Override
            public void onClick(View v) {
                if(email_ID.getText().toString().equals("")){
                    Toast.makeText(register_email_activity.this,
                            "이메일 아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }else if(email_address_direct_check){
                    if(email_address_edit.getText().toString().equals("")) {
                        Toast.makeText(register_email_activity.this,
                                "이메일 주소를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        email_address = email_address_edit.getText().toString();
                    }
                }
                email = email_ID.getText().toString()+"@"+email_address;
                if(!checkEmailForm(email)){
                    Toast.makeText(register_email_activity.this,
                            "이메일 형식으로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Check_Email check_email = new Check_Email();
                    check_email.execute("http://" + IP_Address + "/bank/regCheckEmail.php", email);
                }
            }
        });
    }
    public boolean checkEmailForm(String email){
        boolean result = false;
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        if(matcher.find()){
            result=true;
        }
        return result;
    }
    private class Check_Email extends AsyncTask<String,Void,String>{
        ProgressDialog progressDialog;
        String error_msg=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = progressDialog.show(register_email_activity.this,"Pleases Wait",
                    null,true,true);
        }

        @Override
        protected String doInBackground(String... strings) {
            String DatabaseUrl = strings[0];
            String Email = strings[1];
            String PostValues = "Email=" + Email;

            try {
                URL Url = new URL(DatabaseUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) Url.openConnection();
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
                if(httpRequestCode == HttpURLConnection.HTTP_OK){
                    inputStream = httpURLConnection.getInputStream();
                }else{
                    inputStream = httpURLConnection.getErrorStream(); }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder = new StringBuilder();
                String line;

                if((line = bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch (Exception e){
                error_msg = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println(email_ID+"@"+email_address);
            if(result.equals("true")){
                Toast.makeText(register_email_activity.this,
                        "중복된 이메일입니다.\n다른 이메일을 사용해주세요.",Toast.LENGTH_SHORT).show();
            }else{
                Intent info_intent = new Intent(getApplicationContext(),register_info_activity.class);
                info_intent.putExtra("userID",userID);
                info_intent.putExtra("email",email);
                startActivity(info_intent);
            }
            progressDialog.dismiss();
        }
    }
}