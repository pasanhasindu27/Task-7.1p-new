package com.example.lostandfoundapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {
    private Context context;
    private int resource;

    public ItemAdapter(Context context, int resource, ArrayList<Item> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the item data for this position
        Item item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        // Get references to view elements
        TextView itemTypeTextView = convertView.findViewById(R.id.itemTypeTextView);
        TextView itemNameTextView = convertView.findViewById(R.id.itemNameTextView);
        TextView itemLocationTextView = convertView.findViewById(R.id.itemLocationTextView);

        // Set values
        if (item != null) {
            itemTypeTextView.setText(item.getType());
            itemNameTextView.setText(item.getName());
            itemLocationTextView.setText(item.getLocation());

            // Set text color based on item type
            if (item.getType().equals("Lost")) {
                itemTypeTextView.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
            } else {
                itemTypeTextView.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
            }
        }

        return convertView;
    }
}