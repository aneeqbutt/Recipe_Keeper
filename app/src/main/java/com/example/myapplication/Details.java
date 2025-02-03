package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Details extends AppCompatActivity {
private int chickenId;
    private ImageView favoriteImageView, topimage;

private  TextView tv_categoryInfo , detailsTextView;
private  boolean isFav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
         chickenId = getIntent().getIntExtra("CHICKEN_ID", -1);
    Toast.makeText(this, "chickenId: " + chickenId, Toast.LENGTH_SHORT).show();
        // Use chickenId to fetch and display details
         detailsTextView = findViewById(R.id.tv_content);
         favoriteImageView = findViewById(R.id.fav1);
            topimage= findViewById(R.id.imageView4);
         tv_categoryInfo = findViewById(R.id.tv_categoryInfo);
        fetchChickenRecipesFromFirebase();
        fetchInitialFavoriteStatus();

        // Set click listener for favorite icon
        favoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavorite();
            }
        });
    }
    private void fetchChickenRecipesFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("menu");

        // Query the database to get recipes matching the category
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Iterate through the data under "menu" node
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Each child node will have the recipe information
                    int id = Integer.parseInt(snapshot.child("id").getValue(String.class));
                    if (id==chickenId) {
                        // Get values from Firebase
                        // id is a string in Firebase, convert it to int
                        String recipeDetails = snapshot.child("details").getValue(String.class);
                        String category = snapshot.child("category").getValue(String.class);
                        if (category.equals("Chicken")){
                            topimage.setImageResource(R.drawable.chickenegg);
                        }
                        else  if (category.equals("Seafood")){
                            topimage.setImageResource(R.drawable.buttershrimp);
                        }
                        else if (category.equals("Dessert")){
                            topimage.setImageResource(R.drawable.chcoclatelava);
                        }
                        else if (category.equals("Beef")){
                            topimage.setImageResource(R.drawable.beefstrew);
                        }
                        tv_categoryInfo.setText(category);
                        detailsTextView.setText(recipeDetails);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseError", "Error fetching data: " + databaseError.getMessage());
            }
        });
    }
    private void toggleFavorite() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("menu"); // Directly access the relevant category

        databaseReference.orderByChild("id").equalTo(String.valueOf(chickenId))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot dishSnapshot : dataSnapshot.getChildren()) {
                                // Toggle the favorite status
                                isFav = !isFav;

                                // Update in database
                                dishSnapshot.getRef().child("isFav")
                                        .setValue(isFav)
                                        .addOnSuccessListener(aVoid -> {
                                            updateFavoriteIcon(); // Update UI
                                            Toast.makeText(Details.this,
                                                    isFav ? "Added to favorites" : "Removed from favorites",
                                                    Toast.LENGTH_SHORT).show();
                                        })
                                        .addOnFailureListener(e -> {
                                            // Revert the local state if update fails
                                            isFav = !isFav;
                                            updateFavoriteIcon(); // Reset UI
                                            Toast.makeText(Details.this,
                                                    "Failed to update favorite status: " + e.getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                        });
                            }
                        } else {
                            Toast.makeText(Details.this, "Dish not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Details.this,
                                "Error updating favorite status: " + databaseError.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }


    // Also update fetchInitialFavoriteStatus() similarly:
    private void fetchInitialFavoriteStatus() {


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("menu");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dishTypeSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot dishSnapshot : dishTypeSnapshot.getChildren()) {
                        String dishIdStr = dishSnapshot.child("id").getValue(String.class);
                        if (dishIdStr != null) {
                            int dishId = Integer.parseInt(dishIdStr);
                            if (dishId == chickenId) {
                                Boolean currentFavStatus = dishSnapshot.child("isFav").getValue(Boolean.class);
                                if (currentFavStatus != null) {
                                    isFav = currentFavStatus;
                                    updateFavoriteIcon();  // Update UI to show current status
                                }
                                return;
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Details.this,
                        "Error fetching favorite status: " + databaseError.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateFavoriteIcon() {
        // Update the favorite icon based on isFav status
        if (isFav) {
            favoriteImageView.setImageResource(R.drawable.bg);  // Your filled star icon
        } else {
            favoriteImageView.setImageResource(R.drawable.seafood);  // Your empty star icon
        }
    }
}
