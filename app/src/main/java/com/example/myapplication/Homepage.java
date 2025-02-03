package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;


public class Homepage extends Fragment {


    public Homepage() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);

        ViewFlipper viewFlipper = view.findViewById(R.id.view_flipper);

        // Set in and out animations
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right));

        // Set flip interval and start flipping
        viewFlipper.setFlipInterval(3000); // Flip every 3 seconds
        viewFlipper.startFlipping();


        // Find the ImageView elements by their IDs
        ImageView chickenImage = view.findViewById(R.id.chickenImage);
        ImageView beefImage = view.findViewById(R.id.BeefImage);
        ImageView seafoodImage = view.findViewById(R.id.chineseImage);
        ImageView desertImage = view.findViewById(R.id.DesertImage);

        // Set onClickListeners for each ImageView
        chickenImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Bundle data = new Bundle();
                data.putString("key", "Chicken");
                data.putString("name", "Chicken Recipes");

                Chicken chick = new Chicken();
                chick.setArguments(data);
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.homefrag, chick)
                        .commit();

            }
        });

        beefImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle data = new Bundle();
                data.putString("key", "Beef");
                data.putString("name", "Beef Recipes");

                Chicken chick = new Chicken();
                chick.setArguments(data);
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.homefrag, chick)
                        .commit();
            }

        });

        seafoodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle data = new Bundle();
                data.putString("key", "Seafood");
                data.putString("name", "Sea food Recipes");

                Chicken chick = new Chicken();
                chick.setArguments(data);
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.homefrag, chick)
                        .commit();
            }
        });
        desertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle data = new Bundle();
                data.putString("key", "Dessert");
                data.putString("name", "Dessert Recipes");

                Chicken chick = new Chicken();
                chick.setArguments(data);
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.homefrag, chick)
                        .commit();

            }
        });



        return view;


    }


}