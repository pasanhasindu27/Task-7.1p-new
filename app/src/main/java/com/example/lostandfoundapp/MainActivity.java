package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button createNewAdvertBtn;
    private Button showAllItemsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        createNewAdvertBtn = findViewById(R.id.createNewAdvertBtn);
        showAllItemsBtn = findViewById(R.id.showAllItemsBtn);

        // Set up click listeners
        createNewAdvertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Create New Advert screen
                Intent intent = new Intent(MainActivity.this, com.example.lostandfoundapp.CreateAdvertActivity.class);
                startActivity(intent);
            }
        });

        showAllItemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Show All Items screen
                Intent intent = new Intent(MainActivity.this, com.example.lostandfoundapp.ItemListActivity.class);
                startActivity(intent);
            }
        });
    }
}