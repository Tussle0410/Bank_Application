package com.example.bank_application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.manager.SupportRequestManagerFragment;

public class nav_financial_products_fragment extends Fragment {
    private Button savings,funding,loan;
    private ViewPager2 financial_AD;
    private LinearLayout financial_AD_indicator;
    private View view;
    financial_funding_fragment financial_funding_fragment;
    financial_loan_fragment financial_loan_fragment;
    financial_savings_fragment financial_savings_fragment;
    String[] AD_URL = {
            "https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/11/04/15/29/coffee-beans-5712780_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/03/08/21/41/landscape-4913841_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/09/02/18/03/girl-5539094_1280.jpg",
            "https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_nav_financial_products_page,container,false);
        int button_click_color = ContextCompat.getColor(view.getContext(),R.color.main_color);
        int button_unClick_color = ContextCompat.getColor(view.getContext(),R.color.gray);
        financial_savings_fragment = new financial_savings_fragment();
        financial_loan_fragment = new financial_loan_fragment();
        financial_funding_fragment = new financial_funding_fragment();


        getChildFragmentManager().beginTransaction().replace(R.id.financial_frame,financial_savings_fragment).commit();

        savings = (Button) view.findViewById(R.id.financial_savings_button);
        funding = (Button) view.findViewById(R.id.financial_funding_button);
        loan = (Button) view.findViewById(R.id.financial_loan_button);

        savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.financial_frame,financial_savings_fragment).commit();
                savings.setTextColor(button_click_color);
                funding.setTextColor(button_unClick_color);
                loan.setTextColor(button_unClick_color);
            }
        });
        funding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getChildFragmentManager().beginTransaction().replace(R.id.financial_frame,financial_funding_fragment).commit();
                savings.setTextColor(button_unClick_color);
                funding.setTextColor(button_click_color);
                loan.setTextColor(button_unClick_color);
            }
        });
        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.financial_frame,financial_loan_fragment).commit();
                savings.setTextColor(button_unClick_color);
                funding.setTextColor(button_unClick_color);
                loan.setTextColor(button_click_color);
            }
        });

        financial_AD = (ViewPager2) view.findViewById(R.id.financial_AD);
        financial_AD_indicator = (LinearLayout) view.findViewById(R.id.financial_AD_indicator);
        financial_AD.setOffscreenPageLimit(1);
        financial_AD.setAdapter(new viewPager_Adapter(view.getContext(),AD_URL));
        financial_AD.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position,financial_AD_indicator);
            }
        });
        setUpIndicator(AD_URL.length,financial_AD_indicator);

        return view;
    }
    private void setUpIndicator(int count,LinearLayout indicator_layout){
        ImageView[] indicator = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(16,8,16,8);
        for(int i=0;i<indicator.length;i++){
            indicator[i] = new ImageView(view.getContext());
            indicator[i].setImageDrawable(ContextCompat.getDrawable(view.getContext(),R.drawable.indicator_inactive));
            indicator[i].setLayoutParams(params);
            indicator_layout.addView(indicator[i]);
        }
        setCurrentIndicator(0,indicator_layout);
    }
    private void setCurrentIndicator(int Position, LinearLayout indicator_layout){
        int ChildCount = indicator_layout.getChildCount();
        for(int i=0;i<ChildCount;i++){
            ImageView imageView = (ImageView) indicator_layout.getChildAt(i);
            if(i==Position){
                imageView.setImageDrawable(ContextCompat.getDrawable(view.getContext(),R.drawable.indicator_active));
            }else{
                imageView.setImageDrawable(ContextCompat.getDrawable(view.getContext(),R.drawable.indicator_inactive));
            }
        }

    }
}
