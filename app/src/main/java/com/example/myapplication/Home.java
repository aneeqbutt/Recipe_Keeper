package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Home extends AppCompatActivity {
    public Home() {
        // Required empty public constructor
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        Log.e("HomeActivity", "This is an error message");

        AddShoppingList newshop = new AddShoppingList();
        Homepage home = new Homepage();
        Favourite fav = new Favourite();
        User user = new User();

        BottomNavigationView bottomNavigationView = findViewById(R.id.btmnav);

        FragmentManager homeman = getSupportFragmentManager();
        FragmentTransaction transaction  =  homeman.beginTransaction();
        transaction.add(R.id.homefrag,home);
        transaction.commit();

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.nav_home){
                    FragmentTransaction transaction  =  homeman.beginTransaction();
                    transaction.replace(R.id.homefrag, home);
                    transaction.commit();
                    return true;
                }
               else if (item.getItemId() == R.id.nav_stats) {
                    FragmentTransaction transaction  =  homeman.beginTransaction();
                    transaction.replace(R.id.homefrag, fav);

                    transaction.commit();
                    return true;
                }
                else if (item.getItemId() == R.id.nav_wallet) {
                    FragmentTransaction transaction  =  homeman.beginTransaction();
                    transaction.replace(R.id.homefrag, newshop);

                    transaction.commit();
                    return true;
                }
                else if (item.getItemId() == R.id.nav_users) {
                    FragmentTransaction transaction  =  homeman.beginTransaction();
                    transaction.replace(R.id.homefrag, user);

                    transaction.commit();
                    return true;
                }
                return false;}
        });

    }


}