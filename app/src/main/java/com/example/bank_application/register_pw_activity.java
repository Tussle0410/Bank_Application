package com.example.bank_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class register_pw_activity extends AppCompatActivity {
    private ImageButton back_button;
    private Button register_button;
    private EditText userPW;
    private String userID,Name,email,gender,birth,password;
    private String accountAddress="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_pw_page);
        userID = getIntent().getExtras().getString("userID");       //회원가입 정보 받아오기
        Name = getIntent().getExtras().getString("name");
        email = getIntent().getExtras().getString("email");
        gender = getIntent().getExtras().getString("gender");
        birth = getIntent().getExtras().getString("birth");
        Random random = new Random();
        back_button = (ImageButton) findViewById(R.id.register_pw_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {     //뒤로가기 버튼 선언 및 클릭 이벤트
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        register_button = (Button) findViewById(R.id.register_pw_button);
        register_button.setOnClickListener(new View.OnClickListener() { //회원가입 완료 버튼 선언 및 클릭 이벤트
            @Override
            public void onClick(View v) {
                finishAffinity();           //지금까지 열렸던 회원가입 페이지 종료
                Intent login_intent = new Intent(getApplicationContext(),login_activity.class);
                startActivity(login_intent);
            }
        });

    }
    private String AccountAddress(String address){
        int[] temp = new int[4];
        int max_value = 999, min_value=100;
        Random random = new Random();
        temp[0] = random.nextInt(max_value-min_value+1) +min_value;
        max_value = 9999; min_value=1000;
        temp[1] = random.nextInt(max_value-min_value+1) + min_value;
        temp[2] = random.nextInt(max_value-min_value+1) + min_value;
        max_value=99; min_value=10;
        temp[3] = random.nextInt(max_value-min_value+1) + min_value;
        for(int i=0;i<temp.length;i++){
            address += String.valueOf(temp[i]);
        }
        return address;
    }
}
