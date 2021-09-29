package com.example.bank_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class register_id_activity extends AppCompatActivity {
    private Button next_button;
    private ImageButton back_button;
    private EditText ID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_id_page);
        back_button = (ImageButton) findViewById(R.id.register_id_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        next_button = (Button) findViewById(R.id.register_id_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next_intent = new Intent(getApplicationContext(),register_email_activity.class);
                startActivity(next_intent);
            }
        });

    }
}
