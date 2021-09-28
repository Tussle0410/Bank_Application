package com.example.bank_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class splash_activity extends AppCompatActivity {
    private ImageView loading_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_page);                              // 로딩화면 띄우기
        loading_img = (ImageView) findViewById(R.id.loading_page_image);
        loading_img.setImageResource(R.drawable.ic_launcher_background);    // 로딩화면 이미지 설정
        Handler loading_handler = new Handler();
        loading_handler.postDelayed(new Runnable() {                        //3초후 로그인 화면으로 자동 이동
            @Override
            public void run() {
                Intent login_intent = new Intent(getApplicationContext(),login_activity.class);
                startActivity(login_intent);
                finish();
            }
        },3000);
    }
}