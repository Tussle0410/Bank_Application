package com.example.bank_application;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class profile_activity extends AppCompatActivity {
    private ImageButton back_button;
    private TextView name,birth,email;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);
        back_button = (ImageButton)findViewById(R.id.profile_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        name = (TextView) findViewById(R.id.profile_name);
        birth = (TextView) findViewById(R.id.profile_birth);
        email = (TextView) findViewById(R.id.profile_email);
        name.setText(getIntent().getExtras().getString("Name"));
        birth.setText(getIntent().getExtras().getString("Birth"));
        email.setText(getIntent().getExtras().getString("Email"));

    }
}
