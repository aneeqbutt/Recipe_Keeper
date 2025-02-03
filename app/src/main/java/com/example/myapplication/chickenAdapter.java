package com.example.myapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class chickenAdapter extends RecyclerView.Adapter<chickenAdapter.ChickenViewHolder> {

    private List<chickken> chickenList;

    public chickenAdapter(List<chickken> chickenList) {
        this.chickenList = chickenList;
    }

    @NonNull
    @Override
    public ChickenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chicken, parent, false);
        return new ChickenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChickenViewHolder holder, int position) {
        chickken chickken = chickenList.get(position);
        holder.name.setText(chickken.getRecipeName());
        holder.description.setText(chickken.getRecipeDescription());

        // Set click listener to navigate to details page
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), Details.class);
            intent.putExtra("CHICKEN_ID", chickken.getId()); // Pass the id of the chicken
            v.getContext().startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return chickenList != null ? chickenList.size() : 0;
    }

    static class ChickenViewHolder extends RecyclerView.ViewHolder {
        TextView name, description;

        public ChickenViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.chicken_name);
            description = itemView.findViewById(R.id.chicken_description);
        }
    }
}
