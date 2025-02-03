package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class AddShoppingList extends Fragment {

    private ArrayList<String> shoppingList; // List to store shopping items
    private ArrayAdapter<String> adapter; // Adapter for ListView
    private EditText itemEditText; // Input field for new items
    private ListView shoppingListView; // ListView to display items


    public AddShoppingList() {
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
        View view = inflater.inflate(R.layout.fragment_add_shopping_list, container, false);

        // Use the 'view' object instead of 'getView()'
        itemEditText = view.findViewById(R.id.itemEditText);
        Button addItemButton = view.findViewById(R.id.addItemButton);
        shoppingListView = view.findViewById(R.id.shoppingListView);

        // Initialize the shopping list and adapter
        shoppingList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, shoppingList);
        shoppingListView.setAdapter(adapter);

        // Add item button click listener
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem = itemEditText.getText().toString();
                if (!newItem.isEmpty()) {
                    shoppingList.add(newItem);
                    adapter.notifyDataSetChanged();
                    itemEditText.setText(""); // Clear the input field
                }
            }
        });

        // Item click listener to remove items
        shoppingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                shoppingList.remove(position);
                adapter.notifyDataSetChanged(); // Refresh the list
            }
        });

        return view; // Return the inflated view
    }

}