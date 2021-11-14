package com.example.bank_application;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class remittance_history_activity extends AppCompatActivity {
    private ImageButton back_button;
    private Button deposit,saving,funding,loan;
    remittance_history_deposit_fragment remittance_history_deposit_fragment;
    remittance_history_loan_fragment remittance_history_loan_fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remittance_history_page);
        back_button = (ImageButton) findViewById(R.id.remittance_history_back_button);      //뒤로가기 버튼
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }});
        remittance_history_deposit_fragment = new remittance_history_deposit_fragment();
        Bundle deposit_bundle = new Bundle();
        deposit_bundle.putString("addressName",getIntent().getExtras().getString("addressName"));
        deposit_bundle.putString("Address_hyphen",getIntent().getExtras().getString("Address_hyphen"));
        deposit_bundle.putString("Money",getIntent().getExtras().getString("Money"));
        deposit_bundle.putString("Address",getIntent().getExtras().getString("Address"));
        remittance_history_deposit_fragment.setArguments(deposit_bundle);
        remittance_history_loan_fragment = new remittance_history_loan_fragment();
        Bundle loan_bundle = new Bundle();
        deposit_bundle.putString("addressName",getIntent().getExtras().getString("addressName"));
        deposit_bundle.putString("Address_hyphen",getIntent().getExtras().getString("Address_hyphen"));
        deposit_bundle.putString("Money",getIntent().getExtras().getString("Money"));
        deposit_bundle.putString("Address",getIntent().getExtras().getString("Address"));
        remittance_history_loan_fragment.setArguments(loan_bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.remittance_history_frame,
                remittance_history_deposit_fragment).commit();
        deposit = (Button)findViewById(R.id.remittance_history_deposit_button);
        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Fragment fragment : getSupportFragmentManager().getFragments()){
                    if(fragment.isVisible()){
                        if(!(fragment instanceof remittance_history_deposit_fragment)){
                                getSupportFragmentManager().beginTransaction().replace(R.id.remittance_history_frame,
                                        remittance_history_deposit_fragment).commit();
                        }
                    }
                }
            }
        });
        saving = (Button)findViewById(R.id.remittance_history_savings_button);
        funding = (Button)findViewById(R.id.remittance_history_funding_button);
        loan = (Button)findViewById(R.id.remittance_history_loan_button);

    }
}
