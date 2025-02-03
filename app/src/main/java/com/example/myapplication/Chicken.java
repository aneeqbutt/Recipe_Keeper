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
    import android.widget.TextView;

    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    import java.util.ArrayList;
    import java.util.List;

    public class Chicken extends Fragment {

        private RecyclerView recyclerView;
        private chickenAdapter adapter;
        private List<chickken> chickenList;

        public Chicken() {
            // Required empty public constructor
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            String name = "";
            View view = inflater.inflate(R.layout.fragment_chicken, container, false);
            Bundle bundle = getArguments();
            String category = "";
            if (bundle != null) {
                category = bundle.getString("key");
                 name = bundle.getString("name");

            }

            TextView textView = view.findViewById(R.id.textView);
            textView.setText(name);
            recyclerView = view.findViewById(R.id.chicken);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            chickenList = new ArrayList<>();
            adapter = new chickenAdapter(chickenList);
            recyclerView.setAdapter(adapter);

            // Fetch data from Firebase
            fetchChickenRecipesFromFirebase(category);

            return view;
        }

        private void fetchChickenRecipesFromFirebase(String category) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("menu");

            // Query the database to get recipes matching the category
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    chickenList.clear(); // Clear the list before adding new data

                    // Iterate through the data under "menu" node
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Each child node will have the recipe information
                        String recipeCategory = snapshot.child("category").getValue(String.class);
                        if (recipeCategory != null && recipeCategory.equals(category)) {
                            // Get values from Firebase
                            int id = Integer.parseInt(snapshot.child("id").getValue(String.class));  // id is a string in Firebase, convert it to int
                            String recipeName = snapshot.child("recipeName").getValue(String.class);
                            String recipeDescription = snapshot.child("recipeDescription").getValue(String.class);

                            // Add the recipe to the list
                            chickenList.add(new chickken(id, recipeName, recipeDescription));
                        }
                    }
                    // Notify the adapter about the data change
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("FirebaseError", "Error fetching data: " + databaseError.getMessage());
                }
            });
        }

    }
