package com.example.bank_application;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.audiofx.AudioEffect;
import android.os.Bundle;
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
        data.add(new PieEntry(1000000f,"입출금"));
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
}
