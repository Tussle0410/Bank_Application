package com.example.bank_application;

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

import java.util.zip.Inflater;

public class email_remittance_fragment extends Fragment {
    Button email_button;
    EditText name,email;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.email_remittance_frame,container,false);
        email = (EditText) view.findViewById(R.id.remittance_receive_email);
        name = (EditText) view.findViewById(R.id.remittance_receive_name);
        return view;
    }
}
