package com.example.bank_application;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class register_info_activity extends AppCompatActivity {
    private Button day_of_birth_change,info_next_button;
    private ImageButton back_button;
    private EditText userName;
    private CheckBox personal_info_agree,man_checkBox,girl_checkBox;
    private String gender,userID, email;
    TextView year,month,day;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_info_page);

        userID = getIntent().getExtras().getString("userID");   //Intent 정보 가져오기
        email = getIntent().getExtras().getString("email");

        year = (TextView) findViewById(R.id.register_year);
        month = (TextView)findViewById(R.id.register_month);
        day = (TextView)findViewById(R.id.register_day);
        userName = (EditText)findViewById(R.id.register_name);
        personal_info_agree = (CheckBox) findViewById(R.id.personal_info_agree);
        man_checkBox = (CheckBox)findViewById(R.id.register_gender_man);
        man_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {    //남성 체크시 여성 체크 풀기
                if(isChecked){
                    girl_checkBox.setChecked(false);
                    gender = "남";
                }
            }
        });
        girl_checkBox = (CheckBox)findViewById(R.id.register_gender_girl);
        girl_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {     //여성 체크시 남성 체크 풀기
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    man_checkBox.setChecked(false);
                    gender="여";
                }
            }
        });

        info_next_button = (Button)findViewById(R.id.register_info_button);
        info_next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().toString().equals("")) {     //이름 미작성시 Toast 메세지 발생
                    Toast.makeText(register_info_activity.this,
                            "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    if (personal_info_agree.isChecked()) {      //다음 페이지로 이동
                        Intent pw_intent = new Intent(getApplicationContext(), register_pw_activity.class);
                        pw_intent.putExtra("userID", userID);
                        pw_intent.putExtra("email", email);
                        pw_intent.putExtra("birth", year.getText().toString() + "-" +
                                month.getText().toString() + "-" +  day.getText().toString());
                        pw_intent.putExtra("gender", gender);
                        pw_intent.putExtra("name", userName.getText().toString());
                        startActivity(pw_intent);
                    } else {    //개인정보 사용 동의 안할 시 Toast 메세지 발생
                        Toast.makeText(register_info_activity.this,
                                "개인정보 사용 동의를 눌러주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
        back_button = (ImageButton)findViewById(R.id.register_info_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {     //뒤로가기 버튼시 activity 종료
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        day_of_birth_change = (Button)findViewById(R.id.day_of_birth_change_button);
        day_of_birth_change.setOnClickListener(new View.OnClickListener() {         //DatePicker 다이얼 출력
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

    }
    private void showDialog(){      //DatePickerDialog 출력 메소드
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int years, int monthOfYear, int dayOfMonth) {
                        year.setText(String.valueOf(years));
                        month.setText(String.valueOf(monthOfYear));
                        day.setText(String.valueOf(dayOfMonth));
                    }
                },c.get(Calendar.YEAR),c.get(Calendar.MONDAY),c.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setCalendarViewShown(false);     //Spinner로 보이게 설정
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
    }
}
