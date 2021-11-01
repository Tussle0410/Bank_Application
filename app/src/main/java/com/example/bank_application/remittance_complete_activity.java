package com.example.bank_application;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class remittance_complete_activity extends AppCompatActivity {
    private TextView name,receiveName,amount,receive_address;
    private Button complete_button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remittance_complete_page);
        complete_button = (Button) findViewById(R.id.remittance_complete_button); //완료 버튼 선언
        complete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }});
        name = (TextView) findViewById(R.id.remittance_complete_name);
        receiveName = (TextView) findViewById(R.id.remittance_complete_receiveName);
        amount = (TextView) findViewById(R.id.remittance_complete_amount);
        receive_address = (TextView)findViewById(R.id.remittance_complete_address);
        name.setText(getIntent().getExtras().getString("name"));
        receiveName.setText(getIntent().getExtras().getString("Receive_name"));
        amount.setText(getIntent().getExtras().getString("amount"));
        receive_address.setText(getIntent().getExtras().getString("Receive_address"));
    }
}
