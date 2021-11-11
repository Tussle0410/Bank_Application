package com.example.bank_application;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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


public class remittance_history_deposit_fragment extends Fragment {
    TextView addressName,money,address;
    Button weekend,month,sixMonth,year,all;
    ArrayList<remittance_history_data> mArrayList;
    RecyclerView deposit_recycler;
    RecyclerView.LayoutManager deposit_layoutManager;
    remittance_history_Adapter deposit_adapter;
    String JsonString,IP_Address;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.remittance_history_deposit_page,container,false);
        Bundle Info = getArguments();
        int text_color = ContextCompat.getColor(view.getContext(),R.color.history_text);
        int white_color = ContextCompat.getColor(view.getContext(),R.color.white);
        IP_Address = ((databaseIP)getActivity().getApplication()).getIP_Address();
        addressName = (TextView) view.findViewById(R.id.history_deposit_addressName);   //레이아웃 요소 설정
        money = (TextView)view.findViewById(R.id.history_deposit_money);
        address = (TextView) view.findViewById(R.id.history_deposit_address);
        weekend = (Button) view.findViewById(R.id.deposit_weekend);
        weekend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weekend.getTextColors().equals(text_color)){
                    mArrayList.clear();
                    getHistory getHistory = new getHistory();
                    getHistory.execute("http://" + IP_Address + "/bank/getHistory.php",Info.getString("Address"),"7");
                }
            }
        });
        month = (Button) view.findViewById(R.id.deposit_month);
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.clear();
                getHistory getHistory = new getHistory();
                getHistory.execute("http://" + IP_Address + "/bank/getHistory.php",Info.getString("Address"),"30");
            }
        });
        sixMonth = (Button) view.findViewById(R.id.deposit_six_month);
        year = (Button) view.findViewById(R.id.deposit_year);
        all = (Button) view.findViewById(R.id.deposit_all);
        deposit_recycler = (RecyclerView) view.findViewById(R.id.deposit_recycleView);
        mArrayList = new ArrayList<>();
        deposit_adapter = new remittance_history_Adapter(getActivity(),mArrayList);
        deposit_layoutManager = new LinearLayoutManager(view.getContext());
        deposit_recycler.setLayoutManager(deposit_layoutManager);
        deposit_recycler.setAdapter(deposit_adapter);
        getHistory getHistory = new getHistory();
        getHistory.execute("http://" + IP_Address + "/bank/getHistory.php",Info.getString("Address"),"30");
        return view;
    }
    protected class getHistory extends AsyncTask<String,Void,String>{
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
            String length = strings[2];
            String PostValue = "address=" + address + "&length=" + length;
            try {
                URL Url = new URL(databaseUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) Url.openConnection();
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
                String line;
                StringBuilder sb = new StringBuilder();
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
            if(result.equals("fail")){
                Toast.makeText(view.getContext(), "거래내역이 없습니다.", Toast.LENGTH_SHORT).show();
            }else{
                JsonString = result;
                getInfo();
            }
            progressDialog.dismiss();
        }
        protected void getInfo(){
            String Tag_Json = "history";
            String Tag_sendAddress = "sendAddress";
            String Tag_receiveAddress = "receiveAddress";
            String Tag_sendName = "sendName";
            String Tag_receiveName = "receiveName";
            String Tag_money = "money";
            String Tag_date = "transactionDate";
            try {
                JSONObject jsonObject = new JSONObject(JsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(Tag_Json);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject value = jsonArray.getJSONObject(i);
                    remittance_history_data data = new remittance_history_data();
                    data.setSendAddress(value.getString(Tag_sendAddress));
                    data.setReceiveAddress(value.getString(Tag_receiveAddress));
                    data.setSendName(value.getString(Tag_sendName));
                    data.setReceiveName(value.getString(Tag_receiveName));
                    data.setMoney(value.getLong(Tag_money));
                    data.setDate(value.getString(Tag_date));
                    mArrayList.add(data);
                    deposit_adapter.notifyDataSetChanged();
                }
            }catch(Exception e){
                Log.d("PHP","에러발생 : " + e);
            }
        }
    }
}
