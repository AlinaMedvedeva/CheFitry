package com.example.project20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    public static final String KEY = "Hello";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        bottomNavigationView = findViewById(R.id.nav);
        bottomNavigationView.setSelectedItemId(R.id.homeitem);
        //переключаемся между активностями
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.dashboard:
                        startActivity(new Intent(MainActivity.this, DashBoard.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.homeitem:
                        return true;
                    case R.id.notification:
                        startActivity(new Intent(MainActivity.this, Notification.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    public void addBreakfast(View view) {
        Intent i = new Intent(MainActivity.this, AddActivity.class);
        i.putExtra(KEY, "Завтрак");
        startActivity(i);
    }

    public void addLunch(View view) {
        Intent i = new Intent(MainActivity.this, AddActivity.class);
        i.putExtra(KEY, "Обед");
        startActivity(i);;
    }

    public void addDinner(View view) {
        Intent i = new Intent(MainActivity.this, AddActivity.class);
        i.putExtra(KEY, "Ужин");
        startActivity(i);
    }
}