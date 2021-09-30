package com.example.bank_application;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class register_info_activity extends AppCompatActivity {
    private Button day_of_birth_change;
    private ImageButton back_button;
    TextView year,month,day;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_info_page);
        back_button = (ImageButton)findViewById(R.id.register_info_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {     //뒤로가기 버튼시 activity 종료
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        year = (TextView) findViewById(R.id.register_year);
        month = (TextView)findViewById(R.id.register_month);
        day = (TextView)findViewById(R.id.register_day);
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
