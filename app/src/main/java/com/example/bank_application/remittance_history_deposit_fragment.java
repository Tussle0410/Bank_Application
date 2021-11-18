package com.example.bank_application;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
    TextView addressName, money, address;
    Button weekend, month, sixMonth, year, all,orderBy;
    ArrayList<remittance_history_data> mArrayList;
    RecyclerView deposit_recycler;
    RecyclerView.LayoutManager deposit_layoutManager;
    remittance_history_Adapter deposit_adapter;
    String JsonString, IP_Address,OrderBy="transactionDate";
    Bundle Info;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.remittance_history_fragment_page, container, false);
        int text_color = ContextCompat.getColor(view.getContext(), R.color.history_text);
        int black_color = ContextCompat.getColor(view.getContext(), R.color.black);
        int white_color = ContextCompat.getColor(view.getContext(),R.color.white);
        int solid_color = ContextCompat.getColor(view.getContext(),R.color.history_solid);
        Info = getArguments();
        IP_Address = ((databaseIP) getActivity().getApplication()).getIP_Address();
        deposit_recycler = (RecyclerView) view.findViewById(R.id.history_recycleView);  //RecycleView 설정
        mArrayList = new ArrayList<>();
        InitRecycleView();
        getHistoryExecute("7");
        addressName = (TextView) view.findViewById(R.id.history_addressName);   //레이아웃 요소 설정
        addressName.setText(Info.getString("addressName"));
        money = (TextView) view.findViewById(R.id.history_money);
        money.setText(Info.getString("Money"));
        address = (TextView) view.findViewById(R.id.history_address);
        address.setText(Info.getString("Address_hyphen"));
        weekend = (Button) view.findViewById(R.id.history_weekend);//거래내역 기준 변경
        weekend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weekend.getCurrentTextColor()==black_color) {
                    InitRecycleView();//RecycleView 초기화
                    weekend = buttonSelectColorSet(weekend,solid_color,text_color);
                    month=buttonBasicColorSet(month,white_color,black_color);
                    sixMonth=buttonBasicColorSet(sixMonth,white_color,black_color);
                    year=buttonBasicColorSet(year,white_color,black_color);
                    all=buttonBasicColorSet(all,white_color,black_color);
                    getHistoryExecute("7");
                }
            }
        });
        month = (Button) view.findViewById(R.id.history_month);
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (month.getCurrentTextColor()==black_color) {
                    InitRecycleView();
                    month = buttonSelectColorSet(month, solid_color, text_color);
                    weekend = buttonBasicColorSet(weekend, white_color, black_color);
                    sixMonth = buttonBasicColorSet(sixMonth, white_color, black_color);
                    year = buttonBasicColorSet(year, white_color, black_color);
                    all = buttonBasicColorSet(all, white_color, black_color);
                    getHistoryExecute("30");
                }
            }
        });
        sixMonth = (Button) view.findViewById(R.id.history_six_month);
        sixMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sixMonth.getCurrentTextColor()==black_color) {
                    InitRecycleView();
                    sixMonth = buttonSelectColorSet(sixMonth, solid_color, text_color);
                    month = buttonBasicColorSet(month, white_color, black_color);
                    weekend = buttonBasicColorSet(weekend, white_color, black_color);
                    year = buttonBasicColorSet(year, white_color, black_color);
                    all = buttonBasicColorSet(all, white_color, black_color);
                    getHistoryExecute("180");
                }
            }
        });
        year = (Button) view.findViewById(R.id.history_year);
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (year.getCurrentTextColor()==black_color) {
                    InitRecycleView();
                    year = buttonSelectColorSet(year, solid_color, text_color);
                    month = buttonBasicColorSet(month, white_color, black_color);
                    sixMonth = buttonBasicColorSet(sixMonth, white_color, black_color);
                    weekend = buttonBasicColorSet(weekend, white_color, black_color);
                    all = buttonBasicColorSet(all, white_color, black_color);
                    getHistoryExecute("360");
                }
            }
        });
        all = (Button) view.findViewById(R.id.history_all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (all.getCurrentTextColor()==black_color) {
                    InitRecycleView();
                    all = buttonSelectColorSet(all, solid_color, text_color);
                    month = buttonBasicColorSet(month, white_color, black_color);
                    sixMonth = buttonBasicColorSet(sixMonth, white_color, black_color);
                    year = buttonBasicColorSet(year, white_color, black_color);
                    weekend = buttonBasicColorSet(weekend, white_color, black_color);
                    getHistoryExecute("3600");
                }
            }
        });
        orderBy = (Button) view.findViewById(R.id.history_orderBy);
        orderBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] item = {"거래일자 순","거래금액 순"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                dialog.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            OrderBy = "transactionDate";
                            orderBy.setText("거래일자 순");
                        } else if (which==1) {
                            OrderBy = "money";
                            orderBy.setText("거래금액 순");
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return view;
    }

    protected class getHistory extends AsyncTask<String, Void, String> {       //거래내역 가져오는 함수
        ProgressDialog progressDialog;
        String errMsg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(view.getContext(), "Please Wait", null,
                    true, true);
        }

        @Override
        protected String doInBackground(String... strings) {
            String databaseUrl = strings[0];
            String address = strings[1];
            String length = strings[2];
            String orderBy = strings[3];
            String PostValue = "address=" + address + "&length=" + length + "&orderBy=" + orderBy;
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
                if (httpRequestCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return sb.toString();
            } catch (Exception e) {
                errMsg = e.toString();
                return errMsg;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("fail")) {
                Toast.makeText(view.getContext(), "거래내역이 없습니다.", Toast.LENGTH_SHORT).show();
            } else {
                JsonString = result;
                getInfo();
            }
            progressDialog.dismiss();
        }

        protected void getInfo() {
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
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject value = jsonArray.getJSONObject(i);
                    remittance_history_data data = new remittance_history_data();
                    data.setSendAddress(value.getString(Tag_sendAddress));
                    data.setReceiveAddress(value.getString(Tag_receiveAddress));
                    data.setSendName(value.getString(Tag_sendName));
                    data.setReceiveName(value.getString(Tag_receiveName));
                    data.setMoney(value.getLong(Tag_money));
                    data.setDate(value.getString(Tag_date));
                    data.setAddress(Info.getString("Address"));
                    mArrayList.add(data);
                    deposit_adapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                Log.d("PHP", "에러발생 : " + e);
            }
        }
    }

    public void InitRecycleView() {
        //RecycleView 초기화
        mArrayList.clear();
        deposit_layoutManager = new LinearLayoutManager(view.getContext());
        deposit_adapter = new remittance_history_Adapter(getActivity(), mArrayList);
        deposit_recycler.setLayoutManager(deposit_layoutManager);
        deposit_recycler.setAdapter(deposit_adapter);
    }
    public Button buttonBasicColorSet(Button b,int white_color,int black_color){
        b.setBackgroundColor(white_color);
        b.setTextColor(black_color);
        return b;
    }
    public Button buttonSelectColorSet(Button b,int solid_color,int text_color){
        b.setBackgroundColor(solid_color);
        b.setTextColor(text_color);
        return b;
    }
    public void getHistoryExecute(String days){
        getHistory getHistory = new getHistory();
        getHistory.execute("http://" + IP_Address + "/bank/getHistory.php", Info.getString("Address"),days,OrderBy);
    }
}