package com.example.project20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project20.OpenHelpers.ProductDataBase;

import java.io.IOException;

public class NewProductActivity extends AppCompatActivity {

    SQLiteDatabase sdb;
    ProductDataBase myDataBase;
    EditText name, belki, jir, uglevod, kalor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        myDataBase = new ProductDataBase(this);
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
        name = findViewById(R.id.name_new);
        belki = findViewById(R.id.belki_new);
        jir = findViewById(R.id.jir_new);
        uglevod = findViewById(R.id.uglevod_new);
        kalor = findViewById(R.id.kalori_new);
    }

    public void Add(View view) {
        ContentValues values = new ContentValues();
        values.put(ProductDataBase.COLUMN_NAME, name.getText().toString());
        values.put(ProductDataBase.COLUMN_BELKI, belki.getText().toString());
        values.put(ProductDataBase.COLUMN_JIR, jir.getText().toString());
        values.put(ProductDataBase.COLUMN_UGLEVOD, uglevod.getText().toString());
        values.put(ProductDataBase.COLUMN_KALOR, kalor.getText().toString());
        sdb.insert(ProductDataBase.TABLE_NAME, null, values);
        Toast.makeText(this, "Новый продукт добавлен", Toast.LENGTH_SHORT);
        name.setText("");
        belki.setText("");
        jir.setText("");
        uglevod.setText("");
        kalor.setText("");
        finish();
    }
}