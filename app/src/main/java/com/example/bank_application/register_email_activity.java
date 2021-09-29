package com.example.bank_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class register_email_activity extends AppCompatActivity{
    private ImageButton back_button;
    private Spinner email_spinner;
    private Button next_button;
    private EditText email_ID,email_address_edit;
    private String email_address;
    private TextView address_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_email_page);
        address_text = (TextView)findViewById(R.id.register_email_address_text);
        email_address_edit = (EditText)findViewById(R.id.register_email_address);
        back_button = (ImageButton)findViewById(R.id.register_email_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        email_spinner = (Spinner) findViewById(R.id.register_email_spinner);
        ArrayAdapter spinner_Adapter = ArrayAdapter.createFromResource(
                this,R.array.email_spinner, android.R.layout.simple_spinner_dropdown_item);
        spinner_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        email_spinner.setAdapter(spinner_Adapter);
        email_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(email_spinner.getItemAtPosition(position).equals("직접입력")){
                    address_text.setVisibility(View.VISIBLE);
                    email_address_edit.setVisibility(View.VISIBLE);
                }else{
                    email_address = String.valueOf(email_spinner.getItemAtPosition(position));
                    address_text.setVisibility(View.INVISIBLE);
                    email_address_edit.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        next_button = (Button)findViewById(R.id.register_email_button);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info_intent = new Intent(getApplicationContext(),register_info_activity.class);
                startActivity(info_intent);
            }
        });
    }
}