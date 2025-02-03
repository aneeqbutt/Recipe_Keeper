package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {





        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText txt1 = findViewById(R.id.email2);
        EditText pas1 = findViewById(R.id.pas2);
        Button btn1 = findViewById(R.id.loginbtn);

        Intent intent = getIntent();
        String regemail = intent.getStringExtra("email");
        String regpassword = intent.getStringExtra("password");

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid = txt1.getText().toString();
                String pass2 = pas1.getText().toString();

                if (ValidEmail(userid) && PasswordStrength(pass2)) {
                    if (userid.equals(regemail) && pass2.equals(regpassword)) {
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, AddShopping.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Login.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (!ValidEmail(userid)) {
                        Toast.makeText(Login.this, "Invalid email format. Please include '@'.", Toast.LENGTH_LONG).show();
                    }
                    if (!PasswordStrength(pass2)) {
                        Toast.makeText(Login.this, "Password is too weak.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private boolean ValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean PasswordStrength(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\] {};':\"\\\\|,.<>\\/?]).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void sign_up_btn(View view) {
        startActivity(new Intent(this, SignUp.class));
    }


}
