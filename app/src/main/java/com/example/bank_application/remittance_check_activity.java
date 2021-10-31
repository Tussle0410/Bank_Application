package com.example.bank_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class remittance_check_activity extends AppCompatActivity {
    private ImageButton back_button;
    private TextView address,money,limit,amount,receive_address,name,receive_name;
    private Button next_button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remittance_check_page);
        back_button = (ImageButton) findViewById(R.id.remittance_check_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); }});

        address = (TextView) findViewById(R.id.remittance_check_address);       //TextView 선언
        money = (TextView) findViewById(R.id.remittance_check_money);
        limit = (TextView) findViewById(R.id.remittance_check_limit);
        amount = (TextView) findViewById(R.id.remittance_check_amount);
        receive_address = (TextView) findViewById(R.id.remittance_check_receive_address);
        name = (TextView) findViewById(R.id.remittance_check_name);
        receive_name = (TextView) findViewById(R.id.remittance_check_receive_name);
        address.setText(getIntent().getExtras().getString("Address_hyphen"));
        money.setText(String.valueOf(getIntent().getExtras().getInt("Money")));
        limit.setText(String.valueOf(getIntent().getExtras().getInt("Limit")));
        amount.setText(getIntent().getExtras().getString("Amount"));
        receive_address.setText(getIntent().getExtras().getString("Receive_address"));
        name.setText(getIntent().getExtras().getString("Name"));
        receive_name.setText(getIntent().getExtras().getString("Receive_name"));

        next_button = (Button) findViewById(R.id.remittance_check_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent remittance_pw_intent = new Intent(getApplicationContext(),remittance_pwCheck_activity.class);
                remittance_pw_intent.putExtra("ID",getIntent().getExtras().getString("ID"));
                remittance_pw_intent.putExtra("Name",getIntent().getExtras().getString("Name"));
                remittance_pw_intent.putExtra("Receive_name",getIntent().getExtras().getString("Receive_name"));
                remittance_pw_intent.putExtra("Amount",getIntent().getExtras().getString("Amount"));
                remittance_pw_intent.putExtra("Receive_address",getIntent().getExtras().getString("Receive_address"));
                remittance_pw_intent.putExtra("Money",getIntent().getExtras().getInt("Money"));
                remittance_pw_intent.putExtra("Limit",getIntent().getExtras().getInt("Limit"));
                remittance_pw_intent.putExtra("Address",getIntent().getExtras().getString("Address"));
                startActivity(remittance_pw_intent);
                finish();
            }
        });
    }
}
