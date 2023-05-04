package com.example.project20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.example.project20.Classes.Account;
import com.example.project20.OpenHelpers.CalendarDataBase;

public class Registration extends AppCompatActivity {

    EditText name, weight, height, date, gender;
    Account account;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name = findViewById(R.id.name_regist);
        weight = findViewById(R.id.weight_regist);
        height = findViewById(R.id.height_regist);
        date = findViewById(R.id.date_regist);
        gender = findViewById(R.id.gender_regist);
    }

    public void toRegistration(View view) {
        account = new Account(name.getText().toString(),
                weight.getText().toString(), height.getText().toString(),
                date.getText().toString(), gender.getText().toString());
        sharedPreferences = getSharedPreferences("myShared", MODE_PRIVATE);
        account.ToRegistration(sharedPreferences);
        Intent i = new Intent(Registration.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}