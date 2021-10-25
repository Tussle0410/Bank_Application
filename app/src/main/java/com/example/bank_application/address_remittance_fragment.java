package com.example.bank_application;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class address_remittance_fragment extends Fragment {
    Button address_button;
    View view;
    EditText address;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.address_remittance_frame,container,false);
        address = (EditText)view.findViewById(R.id.remittance_address);
        address_button = (Button) view.findViewById(R.id.remittance_address_button);
        address_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent remittance_amount_intent = new Intent(view.getContext(),remittance_amount_activity.class);
                startActivity(remittance_amount_intent);
            }
        });
        return view;
    }
}
