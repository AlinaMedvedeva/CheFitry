package com.example.project20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.project20.OpenHelpers.CalendarDataBase;
import com.example.project20.OpenHelpers.ProductDataBase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

public class Notification extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    SQLiteDatabase sdb;
    CalendarDataBase calendarDataBase;
    Cursor cursor;
    CalendarView calendarView;
    TextView information;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        calendarView = findViewById(R.id.notification_calendar);
        information = findViewById(R.id.notification_textview);

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
        try {
            calendarDataBase.updateDataBase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String s = dayOfMonth + "." + (month + 1) + "." + year;
                cursor = sdb.rawQuery("SELECT * FROM " + CalendarDataBase.TABLE_DAY+
                        " WHERE date='" + s + "';", null);
                if(cursor.getCount() > 0)
                {
                    cursor.moveToFirst();
                    information.setText(Double.toString(cursor.getDouble(2)));
                }
            }
        });

        bottomNavigationView = findViewById(R.id.nav);
        bottomNavigationView.setSelectedItemId(R.id.notification);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.dashboard:
                        cursor.close();
                        startActivity(new Intent(Notification.this, DashBoard.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.homeitem:
                        cursor.close();
                        startActivity(new Intent(Notification.this, MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.notification:
                        return true;
                }
                return false;
            }
        });
    }
}