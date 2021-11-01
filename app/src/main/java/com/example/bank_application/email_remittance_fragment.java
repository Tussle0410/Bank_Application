package com.example.bank_application;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class email_remittance_fragment extends Fragment {
    private Button next_button;
    private EditText name,email;
    private String IP_ADDRESS;
    Bundle Info;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.email_remittance_frame,container,false);
        Info  = getArguments();
        IP_ADDRESS = ((databaseIP)getActivity().getApplication()).getIP_Address();
        email = (EditText) view.findViewById(R.id.remittance_receive_email);
        name = (EditText) view.findViewById(R.id.remittance_receive_name);
        next_button = (Button) view.findViewById(R.id.remittance_email_next);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_email_activity register_email_activity = new register_email_activity();
                if(email.getText().toString().isEmpty()) {
                    Toast.makeText(view.getContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else if(name.getText().toString().isEmpty()){
                    Toast.makeText(view.getContext(), "수신인을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else {
                    if (!register_email_activity.checkEmailForm(email.getText().toString())) {
                        Toast.makeText(view.getContext(), "이메일 형식으로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }else{
                        if(email.getText().toString().equals(Info.getString("Email"))){
                            Toast.makeText(view.getContext(), "이메일 전송으로는 자신 계좌로 송금하지 못합니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            CheckEmail checkEmail = new CheckEmail();
                            checkEmail.execute("http://" + IP_ADDRESS + "/bank/remittance_emailCheck.php", email.getText().toString(),
                                    name.getText().toString());
                        }
                    }
                }
            }
        });
        return view;
    }
    private class CheckEmail extends AsyncTask<String,Void,String> {
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
            String dataUrl = strings[0];
            String Email = strings[1];
            String Name = strings[2];
            String PostValue = "email=" + Email + "&name=" + Name;
            try {
                URL url = new URL(dataUrl);
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
                if(httpRequestCode==HttpURLConnection.HTTP_OK){
                    inputStream = httpURLConnection.getInputStream();
                }else{
                    inputStream = httpURLConnection.getErrorStream();
                }
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine())!=null){
                    sb.append(line);
                }
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return sb.toString().trim();
            }catch (Exception e){
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.equals("false")){
                Toast.makeText(view.getContext(),
                        "이메일에 해당하는 계좌가 존재하지 않거나\n이메일과 수신인의 이름이 일치하지 않습니다.",
                        Toast.LENGTH_SHORT).show();
            }else{
                Intent amount_intent = new Intent(view.getContext(),remittance_amount_activity.class);
                amount_intent.putExtra("ID", Info.getString("ID"));
                amount_intent.putExtra("Name", Info.getString("Name"));
                amount_intent.putExtra("Address", Info.getString("Address"));
                amount_intent.putExtra("Address_hyphen", Info.getString("Address_hyphen"));
                amount_intent.putExtra("Money", Info.getInt("Money"));
                amount_intent.putExtra("Limit", Info.getInt("Limit"));
                amount_intent.putExtra("Email",email.getText().toString());
                amount_intent.putExtra("Receive_name",name.getText().toString());
                amount_intent.putExtra("Check","1");
                startActivity(amount_intent);
                getActivity().finish();
            }
            progressDialog.dismiss();
        }
    }
}
