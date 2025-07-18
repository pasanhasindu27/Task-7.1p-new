package com.example.lostandfoundapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity {

    private ListView itemsListView;
    private TextView titleTextView;
    private DatabaseHelper databaseHelper;
    private ItemAdapter itemAdapter;
    private ArrayList<Item> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize UI elements
        titleTextView = findViewById(R.id.titleTextView);
        itemsListView = findViewById(R.id.itemsListView);

        // Set title
        titleTextView.setText("LOST & FOUND ITEMS");

        // Initialize the list and adapter
        itemsList = new ArrayList<>();
        itemAdapter = new ItemAdapter(this, R.layout.item_row, itemsList);
        itemsListView.setAdapter(itemAdapter);

        // Load items from database
        loadItemsFromDatabase();

        // Set item click listener
        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item selectedItem = itemsList.get(position);

                // Navigate to item details/remove screen
                Intent intent = new Intent(ItemListActivity.this, com.example.lostandfoundapp.ItemDetailsActivity.class);
                intent.putExtra("ITEM_ID", selectedItem.getId());
                intent.putExtra("ITEM_TYPE", selectedItem.getType());
                intent.putExtra("ITEM_NAME", selectedItem.getName());
                intent.putExtra("ITEM_PHONE", selectedItem.getPhone());
                intent.putExtra("ITEM_DESC", selectedItem.getDescription());
                intent.putExtra("ITEM_DATE", selectedItem.getDate());
                intent.putExtra("ITEM_LOCATION", selectedItem.getLocation());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the list when returning to this activity
        loadItemsFromDatabase();
    }

    private void loadItemsFromDatabase() {
        itemsList.clear();

        Cursor cursor = databaseHelper.getAllItems();

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex("id");
                int typeIndex = cursor.getColumnIndex("type");
                int nameIndex = cursor.getColumnIndex("name");
                int phoneIndex = cursor.getColumnIndex("phone");
                int descriptionIndex = cursor.getColumnIndex("description");
                int dateIndex = cursor.getColumnIndex("date");
                int locationIndex = cursor.getColumnIndex("location");

                // Only proceed if all column indices are valid
                if (idIndex >= 0 && typeIndex >= 0 && nameIndex >= 0 && phoneIndex >= 0 
                    && descriptionIndex >= 0 && dateIndex >= 0 && locationIndex >= 0) {
                
                    int id = cursor.getInt(idIndex);
                    String type = cursor.getString(typeIndex);
                    String name = cursor.getString(nameIndex);
                    String phone = cursor.getString(phoneIndex);
                    String description = cursor.getString(descriptionIndex);
                    String date = cursor.getString(dateIndex);
                    String location = cursor.getString(locationIndex);

                    Item item = new Item(id, type, name, phone, description, date, location);
                    itemsList.add(item);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        itemAdapter.notifyDataSetChanged();
    }
}