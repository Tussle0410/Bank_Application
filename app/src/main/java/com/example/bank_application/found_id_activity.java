package com.example.bank_application;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class found_id_activity extends AppCompatActivity {
    private ImageButton back_button;
    private Button found_id_button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.found_id_page);
        back_button = (ImageButton) findViewById(R.id.found_id_back_button);        //뒤로가기 버튼 선언
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        found_id_button = (Button)findViewById(R.id.found_id_button);
        found_id_button.setOnClickListener(new View.OnClickListener() {     //ID 찾기 버튼 선언
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }
    private void showDialog(){      //ID 찾기 클릭시 사용되는 Dialog Method
        AlertDialog.Builder Dialog = new AlertDialog.Builder(found_id_activity.this);   //Dialog 생성
        Dialog.setTitle("해당 정보의 아이디 목록입니다.");       //Dialog 제목
        final String[] item = new String[] {"확인을 누르면 비밀번호 찾기로 넘어갑니다","loves","lucky","happy"};  //Dialog Items 배열생성
        Dialog.setItems(item, null);
                Dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {        //Dialog 확인버튼 클릭시
                        finish();
                    }
                });
        Dialog.show();          //Dialog 출력
    }
}
