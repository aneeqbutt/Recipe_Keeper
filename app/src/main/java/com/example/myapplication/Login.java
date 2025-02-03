package com.example.myapplication;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user input
                String userEmail = txt1.getText().toString().trim();
                String userPass = pas1.getText().toString().trim();

                // Validate input fields
                if (userEmail.isEmpty() || userPass.isEmpty()) {
                    Toast.makeText(Login.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidEmail(userEmail)) {
                    Toast.makeText(Login.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isStrongPassword(userPass)) {
                    Toast.makeText(Login.this, "Password must contain at least 8 characters, including uppercase, lowercase, number, and special character", Toast.LENGTH_LONG).show();
                    return;
                }

                // Attempt to login
                loginUser(userEmail, userPass);
            }
        });
    }

    private void loginUser(String userEmail, String userPass) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean loginSuccess = false;
                String loggedInUserId = null;

                // Check each user in the database
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String dbEmail = userSnapshot.child("email").getValue(String.class);
                    String dbPassword = userSnapshot.child("password").getValue(String.class);

                    if (userEmail.equals(dbEmail) && userPass.equals(dbPassword)) {
                        loginSuccess = true;
                        loggedInUserId = userSnapshot.getKey();
                        break;
                    }
                }

                // Handle login success or failure
                if (loginSuccess) {
                    Toast.makeText(Login.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Home.class);
                    intent.putExtra("userID", loggedInUserId);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, "Invalid email or password!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isStrongPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
