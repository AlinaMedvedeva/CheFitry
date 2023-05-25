package com.example.project20;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project20.Classes.Account;
import com.example.project20.OpenHelpers.CalendarDataBase;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class Registration extends AppCompatActivity {

    EditText name, weight, height;
    Account account;
    SharedPreferences sharedPreferences;
    String today;
    CalendarDataBase calendarDataBase;
    DatePickerDialog datePickerDialog;
    String key = "Сегодня", birth, gender;
    SQLiteDatabase sdb;
    Button dateButton, women, men;
    boolean select = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initDatePicker();
        name = findViewById(R.id.name_regist);
        weight = findViewById(R.id.weight_regist);
        height = findViewById(R.id.height_regist);
        dateButton = findViewById(R.id.button_date);
        dateButton.setText(getTodaysDate());
        men = findViewById(R.id.men_gender);
        women = findViewById(R.id.women_gender);


        calendarDataBase = new CalendarDataBase(this);
        try{
            calendarDataBase.updateDataBase();
        } catch (IOException e) {
            throw new Error("UnableToUpdateDatabase");
        }
        try{
            sdb = calendarDataBase.getWritableDatabase();
        }catch (SQLException mSQLException)
        {
            throw mSQLException;
        }
        String where = "name ='"+key+"'";
        ContentValues updateValues = new ContentValues();
        updateValues.put("date", today);
        sdb.update(CalendarDataBase.TABLE_DAY, updateValues, where, null);
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month++;
        int day = cal.get(Calendar.DATE);
        today = day + "." + month + "." + year;
        return makeDateString(day, month, year);
    }

    public void toRegistration(View view) {
         if (name.getText().toString().equals("")||name.getText().toString().charAt(0) == ' ') {
            Toast.makeText(this, "Вы не указали имя", Toast.LENGTH_SHORT).show();
        } else if (weight.getText().toString().equals("")||weight.getText().toString().charAt(0) == ' ') {
            Toast.makeText(this, "Вы не указали вес", Toast.LENGTH_SHORT).show();
        } else if (height.getText().toString().equals("")||height.getText().toString().charAt(0) == ' ') {
            Toast.makeText(this, "Вы не указали рост", Toast.LENGTH_SHORT).show();
        }else if(!select)
         {
             Toast.makeText(this, "Вы не указали пол", Toast.LENGTH_SHORT).show();
         }
         else {
            account = new Account(name.getText().toString(),
                    weight.getText().toString(), height.getText().toString(),
                    birth, gender);
            sharedPreferences = getSharedPreferences("myShared", MODE_PRIVATE);
            account.ToRegistration(sharedPreferences);
            Intent i = new Intent(Registration.this, MainActivity.class);
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
                String date = makeDateString(dayOfMonth, month, year);
                dateButton.setText(date);
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

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    public void womenGender(View view) {
        gender = "Ж";
        women.setBackgroundColor(Color.parseColor("#853154"));
        women.setTextColor(Color.parseColor("#FFFFFF"));
        men.setBackgroundColor(Color.parseColor("#FFFFFF"));
        men.setTextColor(Color.parseColor("#000000"));
        select = true;
    }

    public void menGender(View view) {
        gender = "М";
        men.setBackgroundColor(Color.parseColor("#853154"));
        men.setTextColor(Color.parseColor("#FFFFFF"));
        women.setBackgroundColor(Color.parseColor("#FFFFFF"));
        women.setTextColor(Color.parseColor("#000000"));
        select = true;
    }
}