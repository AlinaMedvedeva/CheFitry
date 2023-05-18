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

        name.setText("Имя: " + sharedPreferences.getString("Имя", "НЕТ"));
        weight.setText("Вес: " + sharedPreferences.getString("Вес", "НЕТ"));
        height.setText("Рост: " + sharedPreferences.getString("Рост", "НЕТ"));
        date.setText("Дата рождения: " + sharedPreferences.getString("Дата", "НЕТ"));
        gender.setText("Пол: " + sharedPreferences.getString("Пол", "НЕТ"));
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
}