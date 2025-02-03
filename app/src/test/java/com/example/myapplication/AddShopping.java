package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddShopping extends AppCompatActivity {

    private ArrayList<String> shoppingList; // List to store shopping items
    private ArrayAdapter<String> adapter; // Adapter for ListView
    private EditText itemEditText; // Input field for new items
    private ListView shoppingListView; // ListView to display items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping);

        // Initialize views
        itemEditText = findViewById(R.id.itemEditText);
        Button addItemButton = findViewById(R.id.addItemButton);
        shoppingListView = findViewById(R.id.shoppingListView);

        // Initialize the shopping list and adapter
        shoppingList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shoppingList);
        shoppingListView.setAdapter(adapter);

        // Add item button click listener
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem = itemEditText.getText().toString();
                if (!newItem.isEmpty()) {
                    shoppingList.add(newItem); // Add item to the list
                    adapter.notifyDataSetChanged(); // Refresh the ListView
                    itemEditText.setText(""); // Clear the input field
                    Toast.makeText(AddShopping.this, "Item added: " + newItem, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddShopping.this, "Please enter an item!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Item click listener to remove items
        shoppingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String removedItem = shoppingList.remove(position); // Remove the item
                adapter.notifyDataSetChanged(); // Refresh the ListView
                Toast.makeText(AddShopping.this, "Item removed: " + removedItem, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
