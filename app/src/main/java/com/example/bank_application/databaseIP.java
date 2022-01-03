package com.example.bank_application;

import android.app.Application;

public class databaseIP extends Application {       //Application 이용한 전역변수 선언
    private String IP_Address = "172.30.1.15";      //Database 서버 주소

    public String getIP_Address() {
        return IP_Address;
    }
}
