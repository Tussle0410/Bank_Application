package com.example.bank_application;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.HandlerThread;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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

public class remittance_amount_activity extends AppCompatActivity {
    private ImageButton back_button;
    private TextView address,limit,money,receive_address;
    private EditText amount;
    private Button next_button,ten_thousand_plus,fifty_thousand_plus,hundred_thousand_plus;
    private Button million_plus,all_plus;
    private String JsonString,IP_ADDRESS,receive_name;
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
        IP_ADDRESS = ((databaseIP)getApplication()).getIP_Address();
        receive_name = getIntent().getExtras().getString("Receive_name");
        address = (TextView) findViewById(R.id.remittance_amount_address);      //TextView 선언
        limit = (TextView) findViewById(R.id.remittance_amount_limit);
        money = (TextView) findViewById(R.id.remittance_amount_money);
        receive_address = (TextView) findViewById(R.id.remittance_amount_receive_address);
        address.setText(getIntent().getExtras().getString("Address_hyphen"));
        limit.setText(String.valueOf(getIntent().getExtras().getInt("Limit")));
        money.setText(getIntent().getExtras().getString("Money"));
        if(getIntent().getExtras().getString("Check").equals("0")){
            receive_address.setText(getIntent().getExtras().getString("Receive_Address"));
        }else{
            getEmail_info getEmail_info = new getEmail_info();
            getEmail_info.execute("http://" + IP_ADDRESS + "/bank/get_email_info.php",
                    getIntent().getExtras().getString("Email"));
        }

        amount = (EditText) findViewById(R.id.remittance_amount);
        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(Integer.parseInt(amount.getText().toString())>Integer.parseInt(money.getText().toString())){
                    Toast.makeText(remittance_amount_activity.this, "잔액을 초과하였습니다.",
                            Toast.LENGTH_SHORT).show();
                    amount.setText("0");
                }
            }
        });
        next_button = (Button) findViewById(R.id.remittance_amount_button);
        ten_thousand_plus = (Button) findViewById(R.id.remittance_tenThousand_plus);
        fifty_thousand_plus = (Button) findViewById(R.id.remittance_fiftyThousand_plus);
        hundred_thousand_plus = (Button) findViewById(R.id.remittance_hundredThousand_plus);
        million_plus = (Button) findViewById(R.id.remittance_million_plus);
        all_plus = (Button) findViewById(R.id.remittance_all_plus);
        ten_thousand_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { amountChange(10000); }});
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
            public void onClick(View v) {amountChange(Integer.parseInt(money.getText().toString())); }});
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount.getText().toString().isEmpty()){
                    Toast.makeText(remittance_amount_activity.this, "금액을 입력하세요.", Toast.LENGTH_SHORT).show();
                } else if(Integer.parseInt(amount.getText().toString())>getIntent().getExtras().getInt("Limit")){
                    Toast.makeText(remittance_amount_activity.this, "한도를 초과한 금액입니다.", Toast.LENGTH_SHORT).show();
                }else {
                    Intent next_intent = new Intent(getApplicationContext(), remittance_check_activity.class);
                    next_intent.putExtra("ID", getIntent().getExtras().getString("ID"));
                    next_intent.putExtra("Name", getIntent().getExtras().getString("Name"));
                    next_intent.putExtra("Address", getIntent().getExtras().getString("Address"));
                    next_intent.putExtra("Address_hyphen", getIntent().getExtras().getString("Address_hyphen"));
                    next_intent.putExtra("Money", getIntent().getExtras().getString("Money"));
                    next_intent.putExtra("Limit", getIntent().getExtras().getInt("Limit"));
                    next_intent.putExtra("Receive_address", receive_address.getText().toString());
                    next_intent.putExtra("Amount", amount.getText().toString());
                    next_intent.putExtra("Receive_name", receive_name);
                    startActivity(next_intent);
                    finish();
                }
            }
        });

    }
    public void amountChange(int add_Money){
            int temp=0;
            if(!amount.getText().toString().equals("")){
                temp = Integer.parseInt(amount.getText().toString());
            }
            temp+=add_Money;
            if(temp>Integer.parseInt(money.getText().toString())){
                Toast.makeText(this, "잔액이 초과되었습니다.", Toast.LENGTH_SHORT).show();
            }else {
                amount.setText(String.valueOf(temp));
            }
    }
    private class getEmail_info extends AsyncTask<String,Void,String>{
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
