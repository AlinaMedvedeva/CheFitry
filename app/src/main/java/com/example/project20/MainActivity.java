package com.example.project20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.project20.OpenHelpers.CalendarDataBase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    public static final String KEY = "Hello";
    ProgressBar kalorProgressBar;
    int koeff_kalor = 0;
    TextView norma;
    CalendarDataBase myDataBase;
    SQLiteDatabase sdb;
    Cursor cursor;
    Double todayKalor = 0.0;
    SharedPreferences sharedPreferences;
    boolean changeDate = false;
    String [] key = {"Сегодня", "Вчера", "Позавчера"};
    Calendar calendar;
    int i = 0;
    String [] keys = {"belki", "jir", "uglevod", "kalori"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPreferences = getSharedPreferences("myShared", MODE_PRIVATE);
        norma = findViewById(R.id.norma);
        myDataBase = new CalendarDataBase(this);
        try{
            myDataBase.updateDataBase();
        } catch (IOException e) {
            throw new Error("UnableToUpdateDatabase");
        }

        try{
            sdb = myDataBase.getWritableDatabase();
        }catch (SQLException mSQLException)
        {
            throw mSQLException;
        }
        calendar = Calendar.getInstance();
        String TodayDate = calendar.get(Calendar.DAY_OF_MONTH) + "." +
                (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.YEAR);
        String query = "SELECT * FROM " + CalendarDataBase.TABLE_DAY;
        query += " WHERE name=";
        cursor = sdb.rawQuery(query +
                "'Сегодня';", null);
        cursor.moveToFirst();
        String date = cursor.getString(1);
        if(!date.equals(TodayDate))
            changeDate = true;
        cursor = sdb.rawQuery("SELECT * FROM days WHERE name='Сегодня'", null);
        cursor.moveToFirst();
        todayKalor += cursor.getDouble(2);
        if(changeDate)
        {
            //сдвинуть вчера на позавчера
            //обнулить завтрак, обед и ужин
            //сменить даты и калории
            query = "SELECT * FROM " + CalendarDataBase.TABLE_DAY;
            query += " WHERE name=";
            query += "'" + key[1] + "';";
            cursor = sdb.rawQuery(query, null);
            cursor.moveToFirst();
            String where = "name ='" + key[2] + "'";
            ContentValues updateValues = new ContentValues();
            updateValues.put("date", cursor.getString(1));
            updateValues.put("kalori", cursor.getDouble(2));
            sdb.update(CalendarDataBase.TABLE_DAY, updateValues, where, null);
            where = "name ='" + key[1] + "'";
            ContentValues values = new ContentValues();
            values.put("date", date);
            values.put("kalori", TodayDate);
            sdb.update(CalendarDataBase.TABLE_DAY, values, where, null);
            for(int i = 0; i < 3; i++)
            {
                for(int j = 0; j < 4; j++)
                {
                    ContentValues contentValues = new ContentValues();
                    where = "name ='" + keys[j] + "'";
                    contentValues.put("value", 0);
                    sdb.update(CalendarDataBase.TABLES_NAME[i], contentValues,
                            where, null);
                }
            }
            ContentValues todayValues = new ContentValues();
            todayValues.put("date", TodayDate);
            where = "name='" + key[0] + "'";
            sdb.update(CalendarDataBase.TABLE_DAY, todayValues,
                    where, null);
            todayKalor = 0.0;
        }
        kalorProgressBar = findViewById(R.id.progress_bar);
        Double norma_person = Double.parseDouble(sharedPreferences.getString("Норма", "Нет").
                replace(',', '.'));
        koeff_kalor = (int)((todayKalor*100)/norma_person);
        norma.setText(todayKalor + "/" + norma_person);

        bottomNavigationView = findViewById(R.id.nav);
        bottomNavigationView.setSelectedItemId(R.id.homeitem);

        //переключаемся между активностями
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.dashboard:
                        cursor.close();
                        startActivity(new Intent(MainActivity.this, DashBoard.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.homeitem:
                        return true;
                    case R.id.notification:
                        cursor.close();
                        startActivity(new Intent(MainActivity.this, Notification.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(i <= koeff_kalor && i != 0)
                {
                    kalorProgressBar.setProgress(i);
                    i++;
                    handler.postDelayed(this, 50);
                }
                else {
                    handler.removeCallbacks(this);
                    i = 0;
                }
            }
        }, 50);
    }

    @Override
    protected void onResume() {
        super.onResume();

        cursor = sdb.rawQuery("SELECT * FROM days WHERE name='Сегодня'", null);
        cursor.moveToFirst();
        todayKalor += cursor.getDouble(2);
        Double norma_person = Double.parseDouble(sharedPreferences.getString("Норма", "Нет").
                replace(',', '.'));
        koeff_kalor = (int)((todayKalor*100)/norma_person);
        norma.setText(todayKalor + "/" + norma_person);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(i <= koeff_kalor)
                {
                    kalorProgressBar.setProgress(i);
                    i++;
                    handler.postDelayed(this, 50);
                }
                else {
                    handler.removeCallbacks(this);
                    i = 0;
                }
            }
        }, 50);
    }

    public void addBreakfast(View view) {
        Intent i = new Intent(MainActivity.this, AddActivity.class);
        i.putExtra(KEY, "Завтрак");
        cursor.close();
        startActivity(i);
    }

    public void addLunch(View view) {
        Intent i = new Intent(MainActivity.this, AddActivity.class);
        i.putExtra(KEY, "Обед");
        cursor.close();
        startActivity(i);
    }

    public void addDinner(View view) {
        Intent i = new Intent(MainActivity.this, AddActivity.class);
        i.putExtra(KEY, "Ужин");
        cursor.close();
        startActivity(i);
    }
}