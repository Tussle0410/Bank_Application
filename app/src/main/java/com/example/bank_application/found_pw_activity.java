package com.example.bank_application;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class found_pw_activity extends AppCompatActivity {
    private ImageButton back_button;
    private Button pw_found_button,id_intent_button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.found_pw_page);
        back_button = (ImageButton) findViewById(R.id.found_pw_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pw_found_button = (Button)findViewById(R.id.found_pw_button);
        pw_found_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        id_intent_button = (Button) findViewById(R.id.found_pw_intent_button);
        id_intent_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent found_id_intent = new Intent(getApplicationContext(),found_id_activity.class);
                startActivity(found_id_intent);
            }
        });
    }
    private void showDialog(){
        AlertDialog.Builder Dialog = new AlertDialog.Builder(found_pw_activity.this);
        Dialog.setTitle("OOO님의 비밀번호 찾기 결과입니다.");
        Dialog.setMessage("\n비밀번호 들어갈 자리 \n\n\n 확인을 누르면 로그인 화면으로 넘어갑니다.");
        Dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                Intent login_intent = new Intent(getApplicationContext(),login_activity.class);
                startActivity(login_intent);
            }
        });
        Dialog.show();
    }
}
