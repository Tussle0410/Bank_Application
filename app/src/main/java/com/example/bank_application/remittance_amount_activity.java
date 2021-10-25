package com.example.bank_application;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class remittance_amount_activity extends AppCompatActivity {
    ImageButton back_button;
    EditText amount;
    Button next_button,ten_thousand_plus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remittance_amount_page);
        amount = (EditText) findViewById(R.id.remittance_amount);
        ten_thousand_plus = (Button) findViewById(R.id.remittance_tenThousand_plus);
        ten_thousand_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int money=0;
                if(!amount.getText().toString().equals("")){
                    money = Integer.parseInt(amount.getText().toString());
                }
                money+=10000;
                amount.setText(String.valueOf(money));
            }
        });

    }
}
