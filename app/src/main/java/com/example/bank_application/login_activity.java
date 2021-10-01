package com.example.bank_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class login_activity extends AppCompatActivity {
    private EditText ID, Password;
    private Button found_Password_Button, register_Button,login_Button;
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.login_page);                        //로그인 페이지 가져오기
        ID = (EditText)findViewById(R.id.login_ID_input);           //페이지 요소들 선언
        Password = (EditText) findViewById(R.id.login_PW_input);
        found_Password_Button = (Button) findViewById(R.id.password_find_button);
        found_Password_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent found_intent = new Intent(getApplicationContext(),found_pw_activity.class);
                startActivity(found_intent);
            }
        });
        register_Button = (Button) findViewById(R.id.register_button);
        register_Button.setOnClickListener(new View.OnClickListener() { //회원가입 페이지 이동
            @Override
            public void onClick(View v) {
                Intent register_intent = new Intent(getApplicationContext(),register_id_activity.class);
                startActivity(register_intent);
            }
        });
        login_Button = (Button) findViewById(R.id.login_button);
        login_Button.setOnClickListener(new View.OnClickListener() {        //로그인 버튼 선언 및 클릭 이벤트
            @Override
            public void onClick(View v) {
                Intent home_intent = new Intent(getApplicationContext(),home_nav_activity.class);
                startActivity(home_intent);
            }
        });

    }
}
