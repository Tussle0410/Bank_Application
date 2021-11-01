package com.example.bank_application;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;

public class remittance_pwCheck_activity extends AppCompatActivity {
    private ImageButton back_button;
    private TextView receive_name,name,receive_address,amount;
    private EditText pw;
    private Button remittance_button;
    private String IP_ADDRESS;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remittance_pwcheck_page);
        IP_ADDRESS = ((databaseIP)getApplication()).getIP_Address();
        remittance_button = (Button) findViewById(R.id.remittance_pw_button);
        back_button = (ImageButton) findViewById(R.id.remittance_pw_back_button);       //뒤로가기 버튼
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); }});

        receive_name = (TextView) findViewById(R.id.remittance_pw_receive_name);        //TextView 선언
        name = (TextView) findViewById(R.id.remittance_pw_name);
        receive_address = (TextView) findViewById(R.id.remittance_pw_receive_address);
        amount = (TextView) findViewById(R.id.remittance_pw_amount);
        receive_name.setText(getIntent().getExtras().getString("Receive_name"));
        name.setText(getIntent().getExtras().getString("Name"));
        receive_address.setText(getIntent().getExtras().getString("Receive_address"));
        amount.setText(getIntent().getExtras().getString("Amount"));
        pw = (EditText) findViewById(R.id.remittance_pw);
        remittance_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remittance remittance = new remittance();
                remittance.execute("http://" + IP_ADDRESS + "/bank/remittance.php",getIntent().getExtras().getString("Address"),
                        receive_address.getText().toString(),getIntent().getExtras().getString("ID"),
                        name.getText().toString(),receive_name.getText().toString(),amount.getText().toString(),
                        pw.getText().toString());
            }
        });
    }
    public class remittance extends AsyncTask<String,Void,String>{
        ProgressDialog progressDialog;
        String errMsg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(remittance_pwCheck_activity.this,"Pleases Wait",null,
                    true, true);
        }

        @Override
        protected String doInBackground(String... strings) {
            String databaseIP = strings[0];
            String address=strings[1];
            String receiveAddress = strings[2];
            String ID = strings[3];
            String name = strings[4];
            String receiveName = strings[5];
            String money = strings[6];
            String pw = strings[7];
            String PostValue = "address=" + address + "&receiveAddress=" + receiveAddress + "&ID=" + ID
                    + "&name=" + name + "&receiveName=" + receiveName + "&money=" + money + "&pw=" + pw;
            try {
                URL url = new URL(databaseIP);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
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
                StringBuilder sb = new StringBuilder();
                String line;
                while((line=bufferedReader.readLine())!=null){
                    sb.append(line);
                }
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return sb.toString();
            }catch (Exception e){
                errMsg = e.toString();
                return errMsg;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.equals("false")){
                Toast.makeText(remittance_pwCheck_activity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }else{
                Intent complete_Intent = new Intent(getApplicationContext(),remittance_complete_activity.class);
                complete_Intent.putExtra("Receive_name",receive_name.getText().toString());
                complete_Intent.putExtra("name",name.getText().toString());
                complete_Intent.putExtra("amount",amount.getText().toString());
                complete_Intent.putExtra("Receive_address",getIntent().getExtras().getString("Receive_address"));
                startActivity(complete_Intent);
                finish();
            }
            progressDialog.dismiss();
        }
    }
}
