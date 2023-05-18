package com.example.project20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.project20.OpenHelpers.ProductDataBase;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class AddActivity extends AppCompatActivity {

    EditText search;
    ListView lw;
    SimpleAdapter adapter;
    Cursor cursor;
    Button addProduct;
    ProductDataBase myDataBase;
    SQLiteDatabase sdb;
    public final static String ADDKEY = "Product";
    public final static String KEY = "Hello";
    String MainKey;
    LinkedList<HashMap<String, Object>> adapterProductList = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        MainKey = getIntent().getStringExtra(MainActivity.KEY);

        search = findViewById(R.id.search);
        lw = findViewById(R.id.list_products);
        addProduct = findViewById(R.id.addProducts);

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
        cursor = sdb.rawQuery("SELECT * FROM table_kalor", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", cursor.getString(0));
            adapterProductList.add(map);
            cursor.moveToNext();
        }
        cursor.close();
        String [] from = {"name"};
        int [] to = {R.id.name_info};
        adapter = new SimpleAdapter(this, adapterProductList, R.layout.list_item, from, to);
        lw.setAdapter(adapter);
        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = parent.getAdapter().getItem(position).toString();
                Intent i = new Intent(AddActivity.this, ProductActivity.class);
                i.putExtra(ADDKEY, Obrez(s));
                i.putExtra(KEY, MainKey);
                cursor.close();
                startActivity(i);
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AddActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void AddNewProduct(View view) {
        Intent i = new Intent(AddActivity.this, NewProductActivity.class);
        cursor.close();
        startActivity(i);
        finish();
    }

    public String Obrez(String s)
    {
        String s1 = "";
        boolean flag = false;
        for (int i = 0; i < s.length() - 1; i++) {
            if(flag)
            {
                s1 += s.charAt(i);
            }
            if(s.charAt(i) == '=')
                flag = true;
        }
        return s1;
    }
}