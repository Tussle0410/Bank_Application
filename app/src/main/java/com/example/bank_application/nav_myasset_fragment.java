package com.example.bank_application;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class nav_myasset_fragment extends Fragment {
    PieChart pieChart;
    int[] ColorArray = {Color.BLUE,Color.GREEN,Color.YELLOW,Color.RED};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_nav_myasset_page,container,false);
        pieChart = (PieChart) view.findViewById(R.id.myasset_chart);
        PieDataSet pieDataSet = new PieDataSet(pieChart_setDate(),"자산현황");
        pieDataSet.setColors(ColorArray);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

        return view;
    }
    private ArrayList<PieEntry> pieChart_setDate(){
        ArrayList<PieEntry> data = new ArrayList<>();
        data.add(new PieEntry(1000000,"입출금"));
        data.add(new PieEntry(1000000,"예적금"));
        data.add(new PieEntry(1000000,"대출"));
        data.add(new PieEntry(1000000,"펀드"));
        return data;
    }
}
