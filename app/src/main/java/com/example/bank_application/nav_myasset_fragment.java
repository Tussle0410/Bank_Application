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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_nav_myasset_page,container,false);
        setPieChart();
        return view;
    }
    private ArrayList<PieEntry> pieChart_setDate(){     //파이 그래프 데이터 적용 함수
        ArrayList<PieEntry> data = new ArrayList<>();
        data.add(new PieEntry(1000000,"입출금"));
        data.add(new PieEntry(1000000f,"예적금"));
        data.add(new PieEntry(1000000f,"대출"));
        data.add(new PieEntry(1000000f,"펀드"));
        return data;
    }
    public void setPieChart(){
        int[] ColorArray = {ContextCompat.getColor(view.getContext(),R.color.myasset_piechart_color1),
                ContextCompat.getColor(view.getContext(),R.color.myasset_piechart_color2),
                ContextCompat.getColor(view.getContext(),R.color.myasset_piechart_color3),
                ContextCompat.getColor(view.getContext(),R.color.myasset_piechart_color4)};     //파이 그래프 요소 색

        pieChart = (PieChart) view.findViewById(R.id.myasset_chart);        //파이 그래프 선언
        PieDataSet pieDataSet = new PieDataSet(pieChart_setDate(),"");  //파이 데이터 셋 선언
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

        }
        protected void getInfo(String JsonString){
            String Tag_JSON = "info";
            String Tag_Money = "money";
            String Tag_kinds = "kinds";
            try {

            }catch (Exception e){
                Log.d("PHP","에러발생 : " + e);
            }
        }
    }
}
