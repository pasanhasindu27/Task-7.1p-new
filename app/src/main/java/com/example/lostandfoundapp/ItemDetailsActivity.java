package com.example.lostandfoundapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lostandfoundapp.DatabaseHelper;
import com.example.lostandfoundapp.R;

public class ItemDetailsActivity extends AppCompatActivity {

    private TextView itemTitleTextView;
    private TextView itemDaysAgoTextView;
    private TextView itemLocationTextView;
    private Button removeButton;

    private DatabaseHelper databaseHelper;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize UI elements
        itemTitleTextView = findViewById(R.id.itemTitleTextView);
        itemDaysAgoTextView = findViewById(R.id.itemDaysAgoTextView);
        itemLocationTextView = findViewById(R.id.itemLocationTextView);
        removeButton = findViewById(R.id.removeButton);

        // Get intent data
        if (getIntent().hasExtra("ITEM_ID")) {
            itemId = getIntent().getIntExtra("ITEM_ID", -1);
            String itemType = getIntent().getStringExtra("ITEM_TYPE");
            String itemName = getIntent().getStringExtra("ITEM_NAME");
            String itemDate = getIntent().getStringExtra("ITEM_DATE");
            String itemLocation = getIntent().getStringExtra("ITEM_LOCATION");

            // Set values to UI
            itemTitleTextView.setText(itemType + " " + itemName);

            // For this simple implementation, we'll just show the date as is
            // In a real app, you'd calculate days ago from the actual date
            itemDaysAgoTextView.setText(itemDate);

            itemLocationTextView.setText("At " + itemLocation);
        }

        // Set up remove button click listener
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem();
            }
        });
    }

    private void removeItem() {
        if (itemId != -1) {
            boolean result = databaseHelper.deleteItem(itemId);

            if (result) {
                Toast.makeText(this, "Item removed successfully", Toast.LENGTH_SHORT).show();
                finish(); // Return to previous screen
            } else {
                Toast.makeText(this, "Failed to remove item", Toast.LENGTH_SHORT).show();
            }
        }
    }
}