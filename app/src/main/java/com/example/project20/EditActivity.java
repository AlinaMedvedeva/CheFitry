package com.example.project20;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project20.Classes.Account;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

    EditText name, weight, height;
    Button date, men, women;
    Account account;
    DatePickerDialog datePickerDialog;
    SharedPreferences sharedPreferences;
    String birth, gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initDatePicker();
        name = findViewById(R.id.name_edit);
        weight = findViewById(R.id.weight_edit);
        height = findViewById(R.id.height_edit);
        date = findViewById(R.id.button_date_edit);
        women = findViewById(R.id.women_edit);
        men = findViewById(R.id.men_edit);

        sharedPreferences = getSharedPreferences("myShared", MODE_PRIVATE);
        name.setText(sharedPreferences.getString("Имя", "НЕТ"));
        weight.setText(sharedPreferences.getString("Вес", "НЕТ"));
        height.setText(sharedPreferences.getString("Рост", "НЕТ"));
        birth = sharedPreferences.getString("Дата", "НЕТ");
        String s [] = birth.split("\\.");
        date.setText(makeDateString(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])));
        gender = sharedPreferences.getString("Пол", "НЕТ");
        if(gender.equals("Ж"))
        {
            womenGender(women);
        }
        else menGender(men);
    }

    public void SaveEdit(View view) {
        if (name.getText().toString().equals("")||name.getText().toString().charAt(0) == ' ') {
            Toast.makeText(this, "Вы не указали имя", Toast.LENGTH_SHORT).show();
        } else if (weight.getText().toString().equals("")||weight.getText().toString().charAt(0) == ' ') {
            Toast.makeText(this, "Вы не указали вес", Toast.LENGTH_SHORT).show();
        } else if (height.getText().toString().equals("")||height.getText().toString().charAt(0) == ' ') {
            Toast.makeText(this, "Вы не указали рост", Toast.LENGTH_SHORT).show();
        }
        else {
            account = new Account(name.getText().toString(),
                    weight.getText().toString(),
                    height.getText().toString(),
                    birth, gender);
            sharedPreferences = getSharedPreferences("myShared", MODE_PRIVATE);
            account.ToRegistration(sharedPreferences);
            Intent i = new Intent(EditActivity.this, DashBoard.class);
            startActivity(i);
            finish();
        }
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String dateBirth = makeDateString(dayOfMonth, month, year);
                date.setText(dateBirth);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener,
                year, month, day);
    }

    private String makeDateString(int day, int month, int year)
    {
        birth = day + "." + month + "." + year;
        return getMonthFormat(month) + " " + day + " " + year;
    }
    private String getMonthFormat(int month) {
        switch (month)
        {
            case 1: return "ЯНВ";
            case 2: return "ФЕВ";
            case 3: return "МАР";
            case 4: return "АПР";
            case 5: return "МАЙ";
            case 6: return "ИЮН";
            case 7: return "ИЮЛ";
            case 8: return "АВГ";
            case 9: return "СЕН";
            case 10: return "ОКТ";
            case 11: return "НОЯ";
            case 12: return "ДЕК";
        }
        return "ЯНВ";
    }


    public void menGender(View view) {
        gender = "М";
        men.setBackgroundColor(Color.parseColor("#853154"));
        men.setTextColor(Color.parseColor("#FFFFFF"));
        women.setBackgroundColor(Color.parseColor("#FFFFFF"));
        women.setTextColor(Color.parseColor("#000000"));
    }

    public void womenGender(View view) {
        gender = "Ж";
        women.setBackgroundColor(Color.parseColor("#853154"));
        women.setTextColor(Color.parseColor("#FFFFFF"));
        men.setBackgroundColor(Color.parseColor("#FFFFFF"));
        men.setTextColor(Color.parseColor("#000000"));
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}