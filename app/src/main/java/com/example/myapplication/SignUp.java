package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        EditText email = findViewById(R.id.email1);
        EditText pas1 = findViewById(R.id.cfmpass1);
        EditText pas2 = findViewById(R.id.cfmpass2);
        Button btn2 = findViewById(R.id.signupbtn);



        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = email.getText().toString();
                String password = pas1.getText().toString();
                String confirmPassword = pas2.getText().toString();

                if (!username.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
                    if (!ValidEmail(username)) {
                        Toast.makeText(SignUp.this, "Invalid email format. Please include '@'.", Toast.LENGTH_LONG).show();
                    } else if (!PasswordStrength(password)) {
                        Toast.makeText(SignUp.this, "Password is too weak.", Toast.LENGTH_LONG).show();
                    } else if (!password.equals(confirmPassword)) {
                        Toast.makeText(SignUp.this, "Passwords do not match.", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(SignUp.this, Login.class);
                       storeUserData(username, password);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(SignUp.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void storeUserData(String useremail, String userpass) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users");
        String userId = userRef.push().getKey();
        HashMap<String, String> userData = new HashMap<>();

        userData.put("email", useremail);
        userData.put("password", userpass);
        Log.v("SIgnUP", "This is an error message");

        userRef.child(userId).setValue(userData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to register user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }


    private boolean ValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean PasswordStrength(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }


    public void open_login(View view) {
        startActivity(new Intent(SignUp.this, Login.class));
    }
}
