package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Favourite extends Fragment {
    private RecyclerView recyclerView;
    private chickenAdapter adapter;
    private List<chickken> chickenList;
    public Favourite() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        recyclerView = view.findViewById(R.id.chicken);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chickenList = new ArrayList<>();
        adapter = new chickenAdapter(chickenList);
        recyclerView.setAdapter(adapter);
        fetchFavoriteRecipesFromFirebase();
    return view;

    }

    private void fetchFavoriteRecipesFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("menu");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chickenList.clear(); // Clear the list before adding new data

                // Loop through each dish type (beefDish, chickenDish, etc.)
                for (DataSnapshot dishSnapshot : dataSnapshot.getChildren()) {
                    Boolean isFav = dishSnapshot.child("isFav").getValue(Boolean.class);
                      if (isFav != null && isFav) { // Check if this dish is marked as favorite
                        int id = Integer.parseInt(dishSnapshot.child("id").getValue(String.class));
                        String recipeName = dishSnapshot.child("recipeName").getValue(String.class);
                        String recipeDescription = dishSnapshot.child("recipeDescription").getValue(String.class);

                        // Add to favorite list
                        chickenList.add(new chickken(id, recipeName, recipeDescription));
                    }
                }

                adapter.notifyDataSetChanged(); // Notify adapter after updating the list
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseError", "Error fetching favorites: " + databaseError.getMessage());
            }
        });
    }



}