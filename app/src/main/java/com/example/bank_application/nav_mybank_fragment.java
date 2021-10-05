package com.example.bank_application;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class nav_mybank_fragment extends Fragment {
    private ImageButton setting_button;
    private Button profile_button;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_nav_mybank_page,container,false);
        setting_button = (ImageButton) view.findViewById(R.id.nav_mybank_setting_button);       //설정버튼 선언
        profile_button = (Button) view.findViewById(R.id.nav_mybank_profile_button);            //자기정보보기 버튼 선언
        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile_intent = new Intent(view.getContext(),profile_activity.class);    //자기정보 페이지로 이동
                startActivity(profile_intent);
            }
        });
        return view;
    }
}
