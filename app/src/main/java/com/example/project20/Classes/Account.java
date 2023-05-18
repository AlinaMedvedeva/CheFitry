package com.example.project20.Classes;

import android.content.SharedPreferences;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class Account {
    String name;
    String weight;
    String height;
    String date;
    String gender;
    int birth;
    String norma;
    String [] key = {"Имя", "Вес", "Рост", "Дата", "Пол", "Норма"};

    public Account(String name, String weight, String height, String date, String gender) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.date = date;
        this.gender = gender;
        birth = OldDate();
        norma = Norma();
    }

    public void ToRegistration(SharedPreferences sharedPreferences)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key[0], name);
        editor.putString(key[1], weight);
        editor.putString(key[2], height);
        editor.putString(key[3], date);
        editor.putString(key[4], gender);
        editor.putString(key[5], norma);
        editor.commit();
    }
    //высчитывем возраст
    public int OldDate()
    {
        Calendar calendar = Calendar.getInstance();
        String [] s = date.split("\\.");
        Date date = new Date(Integer.parseInt(s[2]), Integer.parseInt(s[1]) - 1, Integer.parseInt(s[0]));
        birth = calendar.get(Calendar.YEAR)- date.getYear();
        if(date.getMonth() > calendar.get(Calendar.MONTH) + 1)
        {
            birth--;
        }
        if(date.getMonth() == calendar.get(Calendar.MONTH))
        {
            if(date.getDay() > calendar.get(Calendar.DAY_OF_MONTH))
                birth--;
        }
        return birth;
    }
    //высчитываем норму КБЖУ
    public String Norma()
    {
        double n;
        DecimalFormat decimalFormat = new DecimalFormat(("#.##"));
        int mas = Integer.parseInt(weight);
        int hegh = Integer.parseInt(height);
        if(gender.charAt(0) == 'Ж' || gender.charAt(0) == 'ж')
        {
            n = (447.593 + (9.247*mas) + (3.098*hegh)) - (4.33*birth)*1.2;
        }
        else {
            n = (88.362 + (13.397*mas)+(3.098*hegh)-(4.33*birth)*1.2);
        }
        return decimalFormat.format(n);
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
