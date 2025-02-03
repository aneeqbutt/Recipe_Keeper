package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.view.View;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash4);

        new Handler().postDelayed(()->{
            Intent intent = new Intent(Splash.this , Onboarding.class);
            startActivity(intent);
            finish();
        }, 800);
    }
    public void back_homepage(View view) {

        startActivity(new Intent(this, Onboarding.class));
    }

}