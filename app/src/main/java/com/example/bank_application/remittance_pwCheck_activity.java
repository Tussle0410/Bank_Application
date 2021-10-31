package com.example.bank_application;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class remittance_pwCheck_activity extends AppCompatActivity {
    private ImageButton back_button;
    private TextView receive_name,name,receive_address,amount;
    private EditText pw;
    private Button remittance_button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remittance_pwcheck_page);
        remittance_button = (Button) findViewById(R.id.remittance_pw_button);
        back_button = (ImageButton) findViewById(R.id.remittance_pw_back_button);       //뒤로가기 버튼
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); }});

        receive_name = (TextView) findViewById(R.id.remittance_pw_receive_name);        //TextView 선언
        name = (TextView) findViewById(R.id.remittance_pw_name);
        receive_address = (TextView) findViewById(R.id.remittance_pw_receive_address);
        amount = (TextView) findViewById(R.id.remittance_pw_amount);
        receive_name.setText(getIntent().getExtras().getString("Receive_name"));
        name.setText(getIntent().getExtras().getString("Name"));
        receive_address.setText(getIntent().getExtras().getString("Receive_address"));
        amount.setText(getIntent().getExtras().getString("Amount"));

        pw = (EditText) findViewById(R.id.remittance_pw);
        remittance_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
