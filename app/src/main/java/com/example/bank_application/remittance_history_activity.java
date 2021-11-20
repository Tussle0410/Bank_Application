package com.example.bank_application;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class remittance_history_activity extends AppCompatActivity {
    private ImageButton back_button;
    private Button deposit,saving,funding,loan;
    int mainColor,grayColor;
    remittance_history_deposit_fragment remittance_history_deposit_fragment;
    remittance_history_loan_fragment remittance_history_loan_fragment;
    remittance_history_savings_fragment remittance_history_savings_fragment;
    remittance_history_funding_fragment remittance_history_funding_fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remittance_history_page);
        mainColor = ContextCompat.getColor(this,R.color.main_color);
        grayColor = ContextCompat.getColor(this,R.color.gray);
        back_button = (ImageButton) findViewById(R.id.remittance_history_back_button);      //뒤로가기 버튼
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }});
        remittance_history_deposit_fragment = new remittance_history_deposit_fragment();
        remittance_history_loan_fragment = new remittance_history_loan_fragment();
        remittance_history_savings_fragment = new remittance_history_savings_fragment();
        remittance_history_funding_fragment = new remittance_history_funding_fragment();
        Bundle deposit_bundle = new Bundle();
        deposit_bundle.putString("addressName",getIntent().getExtras().getString("addressName"));
        deposit_bundle.putString("Address_hyphen",getIntent().getExtras().getString("Address_hyphen"));
        deposit_bundle.putString("Money",getIntent().getExtras().getString("Money"));
        deposit_bundle.putString("Address",getIntent().getExtras().getString("Address"));
        remittance_history_deposit_fragment.setArguments(deposit_bundle);
        Bundle loan_bundle = new Bundle();
        loan_bundle.putString("ID",getIntent().getExtras().getString("ID"));
        remittance_history_loan_fragment.setArguments(loan_bundle);
        Bundle saving_bundle = new Bundle();
        saving_bundle.putString("ID",getIntent().getExtras().getString("ID"));
        remittance_history_savings_fragment.setArguments(saving_bundle);
        Bundle funding_bundle  = new Bundle();
        funding_bundle.putString("ID",getIntent().getExtras().getString("ID"));
        remittance_history_funding_fragment.setArguments(funding_bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.remittance_history_frame,
                remittance_history_deposit_fragment).commit();
        saving = (Button)findViewById(R.id.remittance_history_savings_button);
        funding = (Button)findViewById(R.id.remittance_history_funding_button);
        deposit = (Button)findViewById(R.id.remittance_history_deposit_button);
        loan = (Button) findViewById(R.id.remittance_history_loan_button);
        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Fragment fragment : getSupportFragmentManager().getFragments()){
                    if(fragment.isVisible()){
                        if(!(fragment instanceof remittance_history_deposit_fragment)){
                            setColor(deposit);
                            getSupportFragmentManager().beginTransaction().replace(R.id.remittance_history_frame,
                                        remittance_history_deposit_fragment).commit(); } } } }});
        saving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Fragment fragment : getSupportFragmentManager().getFragments()){
                    if(fragment.isVisible()){
                        if(!(fragment instanceof remittance_history_savings_fragment)){
                            setColor(saving);
                            getSupportFragmentManager().beginTransaction().replace(R.id.remittance_history_frame,
                                    remittance_history_savings_fragment).commit();
                        } } } }});
        funding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Fragment fragment : getSupportFragmentManager().getFragments()){
                    if(fragment.isVisible()){
                        if(!(fragment instanceof remittance_history_funding_fragment)){
                            setColor(funding);
                            getSupportFragmentManager().beginTransaction().replace(R.id.remittance_history_frame,
                                    remittance_history_funding_fragment).commit();
                        } } } }});
        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                    if (fragment.isVisible()) {
                        if (!(fragment instanceof remittance_history_loan_fragment)) {
                            setColor(loan);
                            getSupportFragmentManager().beginTransaction().replace(R.id.remittance_history_frame,
                                    remittance_history_loan_fragment).commit(); } } } }});


    }
    public void setColor(TextView text){
        String stringText = text.getText().toString();
        if(stringText.equals("입·출금")){
            setMainColor(deposit);
            setGrayColor(loan);
            setGrayColor(saving);
            setGrayColor(funding);
        }else if(stringText.equals("예·적금")){
            setMainColor(saving);
            setGrayColor(loan);
            setGrayColor(deposit);
            setGrayColor(funding);
        }else if(stringText.equals("펀드")){
            setMainColor(funding);
            setGrayColor(loan);
            setGrayColor(saving);
            setGrayColor(deposit);
        }else if(stringText.equals("대출")){
            setMainColor(loan);
            setGrayColor(deposit);
            setGrayColor(saving);
            setGrayColor(funding);
        }
    }
    public TextView setMainColor(TextView text){
        text.setTextColor(mainColor);
        return text;
    }
    public TextView setGrayColor(TextView text){
        text.setTextColor(grayColor);
        return text;
    }
}
