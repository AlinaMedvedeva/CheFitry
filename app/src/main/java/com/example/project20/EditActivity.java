package com.example.project20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.project20.Classes.Account;

public class EditActivity extends AppCompatActivity {

    EditText name, weight, height, date, gender;
    Account account;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        name = findViewById(R.id.name_edit);
        weight = findViewById(R.id.weight_edit);
        height = findViewById(R.id.height_edit);
        date = findViewById(R.id.date_edit);
        gender = findViewById(R.id.gender_edit);

        sharedPreferences = getSharedPreferences("myShared", MODE_PRIVATE);
        name.setText(sharedPreferences.getString("Имя", "НЕТ"));
        weight.setText(sharedPreferences.getString("Вес", "НЕТ"));
        height.setText(sharedPreferences.getString("Рост", "НЕТ"));
        date.setText(sharedPreferences.getString("Дата", "НЕТ"));
        gender.setText(sharedPreferences.getString("Пол", "НЕТ"));
    }

    public void SaveEdit(View view) {
        account = new Account(name.getText().toString(),
                weight.getText().toString(), height.getText().toString(),
                date.getText().toString(), gender.getText().toString());
        account.ToRegistration(sharedPreferences);
        Intent i = new Intent(EditActivity.this, DashBoard.class);
        startActivity(i);
        finish();
    }
}