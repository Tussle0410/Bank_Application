package com.example.bank_application;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

public class remittance_activity extends AppCompatActivity {
    private ImageButton back_button;
    private TextView address,limit,money;
    private Button address_remittance,email_remittance;
    address_remittance_fragment address_remittance_fragment;
    email_remittance_fragment email_remittance_fragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remittance_page);
        int main_color = ContextCompat.getColor(this,R.color.main_color);   //색 가져오기
        int gray_color = ContextCompat.getColor(this,R.color.gray);
        back_button = (ImageButton) findViewById(R.id.remittance_back_button);  //뒤로가기 버튼 관련
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        address = (TextView)findViewById(R.id.remittance_address);      //TextView 선언 및 값 등록
        money = (TextView)findViewById(R.id.remittance_money);
        limit = (TextView) findViewById(R.id.remittance_limit);
        address.setText(getIntent().getExtras().getString("Address_hyphen"));
        money.setText(getIntent().getExtras().getString("Money"));
        limit.setText(String.valueOf(getIntent().getExtras().getInt("Limit")));
        email_remittance_fragment = new email_remittance_fragment();
        address_remittance_fragment = new address_remittance_fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.remittance_frame,address_remittance_fragment).commit();
        address_remittance = (Button) findViewById(R.id.remittance_address_send);
        address_remittance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(address_remittance.getSolidColor()!=main_color){
                    email_remittance.setTextColor(gray_color);
                    address_remittance.setTextColor(main_color);
                    getSupportFragmentManager().beginTransaction().replace(R.id.remittance_frame,address_remittance_fragment).commit();
                }
            }
        });
        email_remittance = (Button) findViewById(R.id.remittance_email_send);
        email_remittance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email_remittance.getSolidColor()!=main_color){
                    address_remittance.setTextColor(gray_color);
                    email_remittance.setTextColor(main_color);
                    getSupportFragmentManager().beginTransaction().replace(R.id.remittance_frame,email_remittance_fragment).commit();
                }
            }
        });

    }
}
