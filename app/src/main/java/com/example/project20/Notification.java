package com.example.project20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.project20.OpenHelpers.CalendarDataBase;
import com.example.project20.OpenHelpers.ProductDataBase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.HashMap;

public class Notification extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView calendar;
    SQLiteDatabase sdb;
    CalendarDataBase calendarDataBase;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        calendar = findViewById(R.id.notification_textView);

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
        cursor = sdb.rawQuery("SELECT * FROM breakfast", null);
        cursor.moveToFirst();
        calendar.append("Breakfast: \n");
        while(!cursor.isAfterLast())
        {
            calendar.append(cursor.getString(0) + " - " +
                    cursor.getString(1)  + "\n");
            cursor.moveToNext();
        }
        cursor = sdb.rawQuery("SELECT * FROM lunch", null);
        cursor.moveToFirst();
        calendar.append("lunch: \n");
        while(!cursor.isAfterLast())
        {
            calendar.append(cursor.getString(0) + " - " +
                    cursor.getString(1)  + "\n");
            cursor.moveToNext();
        }
        cursor = sdb.rawQuery("SELECT * FROM dinner", null);
        cursor.moveToFirst();
        calendar.append("dinner: \n");
        while(!cursor.isAfterLast())
        {
            calendar.append(cursor.getString(0) + " - " +
                    cursor.getString(1)  + "\n");
            cursor.moveToNext();
        }
        cursor.close();

        bottomNavigationView = findViewById(R.id.nav);
        bottomNavigationView.setSelectedItemId(R.id.notification);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.dashboard:
                        startActivity(new Intent(Notification.this, DashBoard.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.homeitem:
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