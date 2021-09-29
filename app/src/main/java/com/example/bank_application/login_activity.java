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
        register_Button = (Button) findViewById(R.id.register_button);
        register_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_intent = new Intent(getApplicationContext(),register_id_activity.class);
                startActivity(register_intent);
            }
        });
        login_Button = (Button) findViewById(R.id.login_button);

    }
}
