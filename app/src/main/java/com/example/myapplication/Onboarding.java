package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Onboarding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.onboarding);

    }

    public void open_login(View view) {

        startActivity(new Intent(this, Login.class));
    }
    public void sign_up_btn(View view) {
        startActivity(new Intent(this, SignUp.class));
    }

}