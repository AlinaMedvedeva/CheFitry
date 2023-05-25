package com.example.project20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project20.OpenHelpers.CalendarDataBase;
import com.example.project20.OpenHelpers.ProductDataBase;

import java.io.IOException;
import java.text.DecimalFormat;

public class ProductActivity extends AppCompatActivity {

    CalendarDataBase calendarDataBase;
    Cursor cursor;
    SQLiteDatabase AccountSDB, ProductSDB;
    ProductDataBase productDataBase;
    String MainKEY, ProductName;
    TextView name, belki, jir, uglevod, kalor;
    Double[] ProductParametrs = new Double[4];
    EditText volume;
    static final String[] key = {"belki", "jir", "uglevod", "kalori"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        MainKEY = getIntent().getStringExtra(AddActivity.KEY);
        ProductName = getIntent().getStringExtra(AddActivity.ADDKEY);
        name = findViewById(R.id.name_product);
        belki = findViewById(R.id.belki_product);
        jir = findViewById(R.id.jir_product);
        uglevod = findViewById(R.id.uglevod_product);
        kalor = findViewById(R.id.kalor_product);
        volume = findViewById(R.id.edit);

        calendarDataBase = new CalendarDataBase(this);
        try {
            calendarDataBase.updateDataBase();
        } catch (IOException e) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            AccountSDB = calendarDataBase.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        productDataBase = new ProductDataBase(this);
        try {
            productDataBase.updateDataBase();
        } catch (IOException e) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            ProductSDB = productDataBase.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        name.setText(ProductName);
        String query = "SELECT * FROM table_kalor WHERE name = '" + ProductName + "';";
        cursor = ProductSDB.rawQuery
                (query, null);
        cursor.moveToFirst();
        for (int i = 0; i < 4; i++) {
            ProductParametrs[i] = cursor.getDouble(i + 1);
        }
        belki.setText(ProductParametrs[0].toString());
        jir.setText(ProductParametrs[1].toString());
        uglevod.setText(ProductParametrs[2].toString());
        kalor.setText(ProductParametrs[3].toString());
        volume.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (volume.getText().toString().length() != 0) {
                    DecimalFormat decimalFormat = new DecimalFormat(("#.##"));
                    double koeff = Double.parseDouble(volume.getText().toString());
                    belki.setText(decimalFormat.format(ProductParametrs[0]/100 * koeff));
                    jir.setText(decimalFormat.format(ProductParametrs[1]/100 * koeff));
                    uglevod.setText(decimalFormat.format(ProductParametrs[2]/100 * koeff));
                    kalor.setText(decimalFormat.format(ProductParametrs[3]/100 * koeff));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void AddProduct(View view) {
        Double koeff = Double.parseDouble(String.valueOf(volume.getText())) / 100;
        String query1 = "SELECT * FROM ";
        String table = "";
        Double[] info = new Double[4];
        //0 - belki
        //1 - jir
        //2 - uglevod
        //3 - kalor
        switch (MainKEY) {
            case "Завтрак":
                query1 += "breakfast;";
                table += "breakfast";
                break;
            case "Обед":
                query1 += "lunch;";
                table += "lunch";
                break;
            case "Ужин":
                query1 += "dinner;";
                table += "dinner";
                break;
        }
        cursor = AccountSDB.rawQuery(query1, null);
        cursor.moveToFirst();
        for (int i = 0; i < 4; i++) {
            info[i] = cursor.getDouble(1);
            cursor.moveToNext();
        }
        for (int i = 0; i < 4; i++) {
            info[i] += ProductParametrs[i] * koeff;
        }
        for (int i = 0; i < 4; i++) {
            String where = "name ='" + key[i] + "'";
            ContentValues updateValues = new ContentValues();
            updateValues.put("value", info[i]);
            AccountSDB.update(table, updateValues, where, null);
        }
        cursor = AccountSDB.rawQuery("SELECT * FROM days WHERE name='Сегодня';", null);
        cursor.moveToFirst();
        ContentValues dayValues= new ContentValues();
        double dayKalor = cursor.getDouble(2) + ProductParametrs[3];
        dayValues.put("kalori", dayKalor);
        String where = "name ='Сегодня'";
        AccountSDB.update(CalendarDataBase.TABLE_DAY, dayValues, where, null);
        Intent i = new Intent(ProductActivity.this, MainActivity.class);
        cursor.close();
        startActivity(i);
        finish();
    }
}