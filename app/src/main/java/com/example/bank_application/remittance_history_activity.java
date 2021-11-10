package com.example.bank_application;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class remittance_history_activity extends AppCompatActivity {
    private ImageButton back_button;
    private Button deposit,saving,funding,loan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remittance_history_page);
        back_button = (ImageButton) findViewById(R.id.remittance_history_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }});
        deposit = (Button)findViewById(R.id.remittance_history_deposit_button);
        saving = (Button)findViewById(R.id.remittance_history_savings_button);
        funding = (Button)findViewById(R.id.remittance_history_funding_button);
        loan = (Button)findViewById(R.id.remittance_history_loan_button);


    }
}
