package com.example.project20;

import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.project20.OpenHelpers.ProductDataBase;

import java.io.IOException;

public class NewProductActivity extends AppCompatActivity {

    SQLiteDatabase sdb;
    ProductDataBase myDataBase;
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
        //TODO доделать активность и разметку
    }
}