package com.example.project20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.project20.Classes.Account;
import com.example.project20.OpenHelpers.CalendarDataBase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashBoard extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    SharedPreferences sharedPreferences;
    TextView name, weight, height, date, gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        sharedPreferences = getSharedPreferences("myShared", MODE_PRIVATE);

        name = findViewById(R.id.name_dash);
        weight = findViewById(R.id.weight_dash);
        height = findViewById(R.id.height_dash);
        date = findViewById(R.id.date_dash);
        gender = findViewById(R.id.gender_dash);
        bottomNavigationView = findViewById(R.id.nav);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        name.setText(sharedPreferences.getString("Имя", "НЕТ"));
        weight.setText(sharedPreferences.getString("Вес", "НЕТ"));
        height.setText(sharedPreferences.getString("Рост", "НЕТ"));
        gender.setText(sharedPreferences.getString("Пол", "НЕТ"));
        if(sharedPreferences.getString("Пол", "НЕТ").equals("Ж"))
        {
            gender.setText("Женский");
        }
        else gender.setText("Мужской");
        String s[] = sharedPreferences.getString("Дата", "НЕТ").split("\\.");
        date.setText(s[0] + " " + getMonthFormat(Integer.parseInt(s[1])) + " " + s[2] + " года");
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.dashboard:overridePendingTransition(0, 0);
                        return true;
                    case R.id.homeitem:
                        startActivity(new Intent(DashBoard.this, MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.notification:
                        startActivity(new Intent(DashBoard.this, Notification.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    public void Edit(View view) {
        Intent i = new Intent(DashBoard.this, EditActivity.class);
        startActivity(i);
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
}