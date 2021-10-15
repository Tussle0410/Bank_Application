package com.example.bank_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


public class nav_home_fragment extends Fragment {
    private ViewPager2 event_viewPager,financial_viewPager;
    private LinearLayout event_indicator,financial_indicator;
    private DrawerLayout drawerLayout;
    private View drawerView, view;
    private Button Transaction_history_button, remittance_button;
    private ImageButton drawer_button,drawer_close_button;
    private String[] event_ImageUrl = {             // 이벤트 ViewPager Url 배열
            "https://cdn.pixabay.com/photo/2019/12/26/10/44/horse-4720178_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/11/04/15/29/coffee-beans-5712780_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/03/08/21/41/landscape-4913841_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/09/02/18/03/girl-5539094_1280.jpg",
            "https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg"};
    private String[] financial_ImageUrl = {         // 금융상품 ViewPager Url 배열
            "https://cdn.pixabay.com/photo/2020/11/04/15/29/coffee-beans-5712780_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/03/08/21/41/landscape-4913841_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/09/02/18/03/girl-5539094_1280.jpg",
            "https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_nav_home_page,container,false);
        drawer_button = (ImageButton)view.findViewById(R.id.drawer_button);         //drawer_layout 가져오는 버튼 선언
        drawer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);                               //drawer_layout 가져오는 이벤트
            }
        });
        drawerLayout = (DrawerLayout)view.findViewById(R.id.drawer_layout);        //drawer_layout 선언
        drawer_close_button = (ImageButton) view.findViewById(R.id.drawer_close_button);    //drawer_layout 속에 버튼 선언
        drawerView = (View) view.findViewById(R.id.drawerView);                     //drawer_layout 안에 보여질 뷰 선언
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);       //끌어오기로 drawer_layout 나오는 이벤트 잠금
        DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }
            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                drawer_close_button.setOnClickListener(new View.OnClickListener() { //drawer_layout 열릴 때 작동되는 이벤트듣ㄹ
                    @Override
                    public void onClick(View v) {
                        drawerLayout.closeDrawer(drawerView);               //drawer_layout 나가기 버튼
                    }
                });
            }
            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
            }
            @Override
            public void onDrawerStateChanged(int newState) {
            }
        };
        drawerLayout.setDrawerListener(listener);                   //drawer_layout 리스너 설정

        financial_viewPager = (ViewPager2)view.findViewById(R.id.home_financial_viewpager);     //viewpager 선언
        financial_indicator = (LinearLayout)view.findViewById(R.id.home_financial_indicator);   //view indicator 선언
        financial_viewPager.setOffscreenPageLimit(1);                       //view pager 미리 로딩되는 수 1개 설정
        financial_viewPager.setAdapter(new viewPager_Adapter(view.getContext(),financial_ImageUrl));   //RecyclerViews 어뎁터 설정
        financial_viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {      //viewpager 변경시 동작 이벤트
                super.onPageSelected(position);
                setCurrentIndicator(position,financial_indicator);      //indicator 현재 위치로 변경
            }
        });
        setUpIndicator(financial_ImageUrl.length,financial_indicator);      //indicator 기본 설정

        event_viewPager = (ViewPager2) view.findViewById(R.id.home_event_viewpager);    //위와 동일한 방법
        event_indicator = (LinearLayout)view.findViewById(R.id.home_event_indicator);
        event_viewPager.setOffscreenPageLimit(1);
        event_viewPager.setAdapter(new viewPager_Adapter(view.getContext(),event_ImageUrl));
        event_viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position,event_indicator);
            }
        });
        setUpIndicator(event_ImageUrl.length,event_indicator);

        Transaction_history_button = (Button) view.findViewById(R.id.Transaction_history_button);
        remittance_button = (Button) view.findViewById(R.id.remittance_button);
        Transaction_history_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Transaction_intent = new Intent(view.getContext(),);
                startActivity(Transaction_intent);
            }
        });

        return view;
    }
    private void setUpIndicator(int count, LinearLayout indicator_layout){      //indicator 기본설정
        ImageView[] indicator = new ImageView[count];           //Url 크기만큼 indicator 설정
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);   //indicator 위치 설정
        params.setMargins(16,8,16,8);               //indicator margin 설정
        for(int i=0;i<indicator.length;i++){            //indicator 기본모양 설정 및 추가
            indicator[i] = new ImageView(view.getContext());        
            indicator[i].setImageDrawable(ContextCompat.getDrawable(view.getContext(),R.drawable.indicator_inactive));
            indicator[i].setLayoutParams(params);
            indicator_layout.addView(indicator[i]);
        }
        setCurrentIndicator(0,indicator_layout);        //indicator 기본 값 0번째로 설정
    }
    private void setCurrentIndicator(int position,LinearLayout indicator){      //현재 indicator 나타내는 함수
        int childCount = indicator.getChildCount();         //indicator 전체 개수 가져오기
        for(int i=0;i<childCount;i++){
            ImageView imageView = (ImageView)indicator.getChildAt(i);
            if(i==position){    //indicator 현재 보여지고 있는 부분 색깔 변경하기
                imageView.setImageDrawable(ContextCompat.getDrawable(view.getContext(),R.drawable.indicator_active));
            }else{
                imageView.setImageDrawable(ContextCompat.getDrawable(view.getContext(),R.drawable.indicator_inactive));
            }
        }
    }
}
