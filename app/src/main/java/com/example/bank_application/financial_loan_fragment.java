package com.example.bank_application;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class financial_loan_fragment extends Fragment {
    private View view;
    private ArrayList<financial_data> mArrayList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private financial_Adapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.financial_loan_page,container,false);
        return view;
    }
    class httpConnect extends AsyncTask<String,Void,String> {
        ProgressDialog progressDialog;
        String errMsg,JsonString;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(view.getContext(),"Please Wait",null,
                    true,true);
        }

        @Override
        protected String doInBackground(String... strings) {
            String databaseUrl = strings[0];
            String kinds = strings[1];
            String postValue = "kinds=" + kinds;
            try {
                URL url = new URL(databaseUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postValue.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int httpResponseCode = httpURLConnection.getResponseCode();
                InputStream inputStream;
                if(httpResponseCode==HttpURLConnection.HTTP_OK){
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
            JsonString = result;
            if(result.equals("fail")){
                Toast.makeText(view.getContext(), "상품이 없습니다.", Toast.LENGTH_SHORT).show();
            }else {
                getProduction();
            }
            progressDialog.dismiss();
        }
        protected void getProduction(){
            String Tag_JSON= "production";
            String Tag_name = "name";
            String Tag_description = "description";
            String Tag_interestRate = "interestRate";
            String Tag_interestCycle = "interestCycle";
            try {
                JSONObject jsonObject = new JSONObject(JsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(Tag_JSON);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject value = jsonArray.getJSONObject(i);
                    financial_data data = new financial_data();
                    data.setDescription(value.getString(Tag_description));
                    data.setName(value.getString(value.getString(Tag_name)));
                    data.setInterestCycle(value.getInt(Tag_interestCycle));
                    data.setInterestRate(value.getDouble(Tag_interestRate));
                    mArrayList.add(data);
                }
            }catch (Exception e){
                Log.d("PHP","에러발생 " + e);
            }
        }
    }
}
