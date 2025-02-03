package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User extends Fragment {
    private TextView username , email;

    public User() {
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
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        // Logout button and its click listener
        Button logoutButton = view.findViewById(R.id.logOutbtn);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity().finish(); // Finish the hosting activity
            }
        });

        // Chatbot button and its click listener
        Button chatbotButton = view.findViewById(R.id.chatbot);
        chatbotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Chatbot.class);
                startActivity(intent);
            }
        });

        // Maps button and its click listener (Fixed incorrect intent)
        Button mapsButton = view.findViewById(R.id.maps);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
    private void setvalues(String userID) {
        DatabaseReference userSpecificRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(userID);

        userSpecificRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Dbemail = snapshot.child("email").getValue(String.class);

                if (Dbemail != null && !Dbemail.isEmpty()) {
                    email.setText(Dbemail);
                } else {
                    email.setText("No email provided");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error retrieving user information", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

