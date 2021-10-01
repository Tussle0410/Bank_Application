package com.example.bank_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class register_pw_activity extends AppCompatActivity {
    private ImageButton back_button;
    private Button register_button;
    private EditText userPW;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_pw_page);
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
}
