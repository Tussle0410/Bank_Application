package com.example.bank_application;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class home_nav_activity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    nav_home_fragment nav_home_fragment;
    nav_financial_products_fragment nav_financial_products_fragment;
    nav_myasset_fragment nav_myasset_fragment;
    nav_mybank_fragment nav_mybank_fragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_nav_page);

        nav_home_fragment = new nav_home_fragment();                            //바텀 네비게이션 클릭시 이동할 Fragment 선언
        nav_financial_products_fragment = new nav_financial_products_fragment();
        nav_mybank_fragment = new nav_mybank_fragment();
        nav_myasset_fragment = new nav_myasset_fragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.home_frame,nav_home_fragment).commit();//처음화면 설정
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.home_bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {   //바텀 네비게이션 클릭시 이벤트
                switch(item.getItemId()){
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, nav_home_fragment).commit();    //홈으로 이동
                        return true;
                    case R.id.nav_financial_products:
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_frame,nav_financial_products_fragment).commit(); //금융상품 이동
                        return true;
                    case R.id.nav_myAsset:
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_frame,nav_myasset_fragment).commit();  //MY자산으로 이동
                        return true;
                    case R.id.nav_myBank:
                        getSupportFragmentManager().beginTransaction().replace(R.id.home_frame,nav_mybank_fragment).commit();   //MY뱅크로 이동
                        return true;
                }
                return false;
            }
        });
    }
}
