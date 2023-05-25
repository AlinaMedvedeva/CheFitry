package com.example.project20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    TextView logo;
    Intent i;
    SharedPreferences sharedPreferences;
    String proverka;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        logo = findViewById(R.id.welcome);
        sharedPreferences = getSharedPreferences("myShared", MODE_PRIVATE);
        proverka = sharedPreferences.getString("Вес", "Нет");
        //если нет данных о человеке, переходим в регистрацию
        if(proverka != "Нет")
        {
            logo.setText("Welcome\n" + sharedPreferences.getString("Имя", "Нет"));
        }
        Animation logoAnim =
                AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logo.setAnimation(logoAnim);
        logoAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(proverka == "Нет")
                {
                    i = new Intent(SplashActivity.this, Registration.class);
                }
                else {
                    i = new Intent(SplashActivity.this, MainActivity.class);
                }
                startActivity(i);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}