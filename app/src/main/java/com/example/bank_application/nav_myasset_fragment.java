package com.example.bank_application;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.audiofx.AudioEffect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class nav_myasset_fragment extends Fragment {
    PieChart pieChart;
    View view;
    TextView name,assetName,deposit,savings,loan,funding,total_money;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_nav_myasset_page,container,false);
        String IP_ADDRESS = ((databaseIP) getActivity().getApplication()).getIP_Address();
        Bundle Info = getArguments();
        name = (TextView) view.findViewById(R.id.myAsset_name);             //TextView 설정
        assetName = (TextView) view.findViewById(R.id.myAsset_asset_name);
        deposit = (TextView) view.findViewById(R.id.myAsset_deposit_money);
        savings = (TextView) view.findViewById(R.id.myAsset_savings_money);
        loan = (TextView) view.findViewById(R.id.myAsset_loan_money);
        funding = (TextView) view.findViewById(R.id.myAsset_funding_money);
        total_money = (TextView) view.findViewById(R.id.myAsset_total_money);
        name.setText(Info.getString("Name"));
        assetName.setText(Info.getString("Name"));
        moneyInfo moneyInfo = new moneyInfo();
        moneyInfo.execute("http://" + IP_ADDRESS + "/bank/getMyasset.php",Info.getString("ID"));
        return view;
    }
    private ArrayList<PieEntry> pieChart_setDate(int deposit,int savings,int loan,int funding){     //파이 그래프 데이터 적용 함수
        ArrayList<PieEntry> data = new ArrayList<>();
        data.add(new PieEntry(deposit,"입출금"));
        data.add(new PieEntry(savings,"예적금"));
        data.add(new PieEntry(loan,"대출"));
        data.add(new PieEntry(funding,"펀드"));
        return data;
    }
    public void setPieChart(ArrayList<PieEntry> list){
        int[] ColorArray = {ContextCompat.getColor(view.getContext(),R.color.myasset_piechart_color1),
                ContextCompat.getColor(view.getContext(),R.color.myasset_piechart_color2),
                ContextCompat.getColor(view.getContext(),R.color.myasset_piechart_color3),
                ContextCompat.getColor(view.getContext(),R.color.myasset_piechart_color4)};     //파이 그래프 요소 색

        pieChart = (PieChart) view.findViewById(R.id.myAsset_chart);        //파이 그래프 선언
        PieDataSet pieDataSet = new PieDataSet(list,"");  //파이 데이터 셋 선언
        pieDataSet.setColors(ColorArray);
        pieDataSet.setSliceSpace(3);            //요소 별 빈 공간
        PieData pieData = new PieData(pieDataSet);
        Description description = new Description();
        description.setText("자산현황");                                        //파이 그래프 우측 하단 설명칸
        description.setTextSize(15);
        pieChart.setDescription(description);
        pieData.setValueTextSize(20);                   //파이 그래프 퍼센트 글자 크기
        pieData.setValueTextColor(Color.BLACK);
        pieChart.setExtraOffsets(5,10,5,5);     //거리
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setEntryLabelTextSize(20);             //파이 그래프 요소 글자 크기
        pieChart.setDrawHoleEnabled(false);             //중앙 원 안보이게
        pieChart.setHoleColor(Color.WHITE);             // 중앙 원 색 변경
        pieChart.setDrawEntryLabels(true);          //라벨 보이도록
        pieChart.setUsePercentValues(true);           //퍼센트 허용
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setData(pieData);
        pieChart.invalidate();                  //파이 그래프 활성화화
    }
    protected class moneyInfo extends AsyncTask<String,Void,String>{
        ProgressDialog progressDialog;
        String errMsg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog  = ProgressDialog.show(view.getContext(),"Please Wait",null,
                    true,true);
        }

        @Override
        protected String doInBackground(String... strings) {
            String dataBaseUrl = strings[0];
            String ID = strings[1];
            String postValue = "ID=" + ID;
            try {
                URL url = new URL(dataBaseUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postValue.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int httpRespondCode = httpURLConnection.getResponseCode();
                InputStream inputStream;
                if(httpRespondCode == HttpURLConnection.HTTP_OK){
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
            getInfo(result);
            progressDialog.dismiss();

        }
        protected void getInfo(String JsonString){
            String Tag_JSON = "myAsset";
            String Tag_Money = "money";
            String Tag_kinds = "kinds";
            int deposit_money=0 ,loan_money=0,funding_money=0,savings_money=0;
            try {
                JSONObject jsonObject = new JSONObject(JsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(Tag_JSON);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject value = jsonArray.getJSONObject(i);
                    if(value.getString(Tag_kinds).equals("deposit")){
                        deposit_money+=value.getInt(Tag_Money);
                    }else if(value.getString(Tag_kinds).equals("savings")){
                        savings_money+=value.getInt(Tag_Money);
                    }else if(value.getString(Tag_kinds).equals("loan")){
                        loan_money+=value.getInt(Tag_Money);
                    }else if(value.getString(Tag_kinds).equals("funding")){
                        funding_money+=value.getInt(Tag_Money);
                    }
                }
                deposit.setText(String.valueOf(deposit_money));
                savings.setText(String.valueOf(savings_money));
                loan.setText(String.valueOf(loan_money));
                funding.setText(String.valueOf(funding_money));
                int total = deposit_money+savings_money+funding_money-loan_money;
                if(total<0){
                    int red = ContextCompat.getColor(view.getContext(),R.color.red);
                    total_money.setTextColor(red);
                }
                total_money.setText(String.valueOf(total));
                ArrayList<PieEntry> list = pieChart_setDate(deposit_money,savings_money,loan_money,funding_money);
                setPieChart(list);
            }catch (Exception e){
                Log.d("PHP","에러발생 : " + e);
            }
        }
    }
}
