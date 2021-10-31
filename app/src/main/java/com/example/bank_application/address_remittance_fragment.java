package com.example.bank_application;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class address_remittance_fragment extends Fragment {
    private Button next_button;
    private EditText address;
    private Bundle Info;
    private String IP_ADDRESS,JsonString;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.address_remittance_frame,container,false);
        Info = getArguments();
        IP_ADDRESS = ((databaseIP)getActivity().getApplication()).getIP_Address();
        address = (EditText)view.findViewById(R.id.remittance_address);
        next_button = (Button) view.findViewById(R.id.remittance_address_next);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(address.getText().toString().isEmpty()){
                    Toast.makeText(view.getContext(), "계좌번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    if(address.getText().toString().equals(Info.getString("Address"))){
                        Toast.makeText(view.getContext(), "동일한 계좌로 송금할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        CheckAddress checkAddress = new CheckAddress();
                        checkAddress.execute("http://" + IP_ADDRESS + "/bank/remittance_CheckAddress.php",address.getText().toString());
                    }
                }
            }
        });
        return view;
    }
    private class CheckAddress extends AsyncTask<String,Void,String>{
        ProgressDialog progressDialog;
        String errMsg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(view.getContext(),"Please Wait",null,
                    true,true);
        }

        @Override
        protected String doInBackground(String... strings) {
            String databaseUrl = strings[0];
            String address = strings[1];
            String PostValue ="address=" + address;
            try {
                URL url = new URL(databaseUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
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

                StringBuilder sb = new StringBuilder();
                String line;
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
            if(result.equals("false")){
                Toast.makeText(view.getContext(), "존재하지 않는 계좌입니다.", Toast.LENGTH_SHORT).show();
            }else{
                JsonString = result;
                getName();
            }
            progressDialog.dismiss();
        }
        protected void getName(){
            String Tag_JSON = "address";
            String Tag_Name = "Name";
            try {
                JSONObject jsonObject = new JSONObject(JsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(Tag_JSON);
                JSONObject value = jsonArray.getJSONObject(0);
                Intent amount_intent = new Intent(view.getContext(),remittance_amount_activity.class);
                amount_intent.putExtra("ID", Info.getString("ID"));
                amount_intent.putExtra("Name", Info.getString("Name"));
                amount_intent.putExtra("Address", Info.getString("Address"));
                amount_intent.putExtra("Address_hyphen", Info.getString("Address_hyphen"));
                amount_intent.putExtra("Money", Info.getInt("Money"));
                amount_intent.putExtra("Limit", Info.getInt("Limit"));
                amount_intent.putExtra("Receive_Address",address.getText().toString());
                amount_intent.putExtra("Receive_name",value.getString(Tag_Name));
                amount_intent.putExtra("Check","0");
                startActivity(amount_intent);
            }catch (Exception e){
                Log.d("PHP","에러발생" + e);
            }
        }
    }
}
