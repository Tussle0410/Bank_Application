package com.example.bank_application;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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

public class remittance_amount_activity extends AppCompatActivity {
    private ImageButton back_button;
    private TextView address,limit,money,receive_address;
    private EditText amount;
    private Button next_button,ten_thousand_plus,fifty_thousand_plus,hundred_thousand_plus;
    private Button million_plus,all_plus;
    private String JsonString;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remittance_amount_page);
        back_button = (ImageButton) findViewById(R.id.remittance_amount_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        address = (TextView) findViewById(R.id.remittance_amount_address);      //TextView 선언
        limit = (TextView) findViewById(R.id.remittance_amount_limit);
        money = (TextView) findViewById(R.id.remittance_amount_money);
        receive_address = (TextView) findViewById(R.id.remittance_amount_receive_address);
        address.setText(getIntent().getExtras().getString("Address_hyphen"));
        limit.setText(getIntent().getExtras().getString("Limit"));
        money.setText(getIntent().getExtras().getString("Money"));

        amount = (EditText) findViewById(R.id.remittance_amount);
        next_button = (Button) findViewById(R.id.remittance_amount_button);
        ten_thousand_plus = (Button) findViewById(R.id.remittance_tenThousand_plus);
        fifty_thousand_plus = (Button) findViewById(R.id.remittance_fiftyThousand_plus);
        hundred_thousand_plus = (Button) findViewById(R.id.remittance_hundredThousand_plus);
        million_plus = (Button) findViewById(R.id.remittance_million_plus);
        all_plus = (Button) findViewById(R.id.remittance_all_plus);
        ten_thousand_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountChange(10000);
        }});
        fifty_thousand_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {amountChange(50000);}});
        hundred_thousand_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {amountChange(100000); }});
        million_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {amountChange(1000000); }});
        all_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {amountChange(Integer.parseInt(money.getText().toString()));
            }
        });
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_intent = new Intent(getApplicationContext(),remittance_check_activity.class);
            }
        });

    }
    public void amountChange(int add_Money){
            int money=0;
            if(!amount.getText().toString().equals("")){
                money = Integer.parseInt(amount.getText().toString());
            }
            money+=add_Money;
            amount.setText(String.valueOf(money));
    }
    private class getEmail_address extends AsyncTask<String,Void,String>{
        ProgressDialog progressDialog;
        String errMsg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(remittance_amount_activity.this,"Please Wait",null,
                    true,true);
        }

        @Override
        protected String doInBackground(String... strings) {
            String databaseUrl = strings[0];
            String email = strings[1];
            String PostValue = "email=" + email;
            try {
                URL Url = new URL(databaseUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) Url.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");
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
                    inputStream = httpURLConnection.getErrorStream();
                }
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                StringBuilder sb = new StringBuilder();
                while((line=bufferedReader.readLine())!=null){
                    sb.append(line);
                }
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return sb.toString().trim();
            }catch (Exception e){
                errMsg = e.toString();
                return errMsg;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JsonString = result;
            getAddress();
            progressDialog.dismiss();
        }
        protected void getAddress(){
            String Tag_JSON="address";
            String Tag_address="address";
            try {
                JSONObject jsonObject = new JSONObject(JsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(Tag_JSON);
                JSONObject value = jsonArray.getJSONObject(0);
                receive_address.setText(value.getString(Tag_address));
            }catch (Exception e){
                Log.d("PHP","에러발생 " + e);
            }
        }
    }
}
