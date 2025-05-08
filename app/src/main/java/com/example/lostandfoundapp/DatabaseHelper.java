package com.example.lostandfoundapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "lostAndFound.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_ITEMS = "items";

    // Items Table Columns
    private static final String KEY_ITEM_ID = "id";
    private static final String KEY_ITEM_TYPE = "type";  // "Lost" or "Found"
    private static final String KEY_ITEM_NAME = "name";
    private static final String KEY_ITEM_PHONE = "phone";
    private static final String KEY_ITEM_DESCRIPTION = "description";
    private static final String KEY_ITEM_DATE = "date";
    private static final String KEY_ITEM_LOCATION = "location";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS +
                "(" +
                KEY_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_ITEM_TYPE + " TEXT," +
                KEY_ITEM_NAME + " TEXT," +
                KEY_ITEM_PHONE + " TEXT," +
                KEY_ITEM_DESCRIPTION + " TEXT," +
                KEY_ITEM_DATE + " TEXT," +
                KEY_ITEM_LOCATION + " TEXT" +
                ")";
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        // Create tables again
        onCreate(db);
    }

    // Adding new item
    public long addItem(String type, String name, String phone, String description, String date, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put(KEY_ITEM_TYPE, type);
        values.put(KEY_ITEM_NAME, name);
        values.put(KEY_ITEM_PHONE, phone);
        values.put(KEY_ITEM_DESCRIPTION, description);
        values.put(KEY_ITEM_DATE, date);
        values.put(KEY_ITEM_LOCATION, location);
        
        // Inserting Row
        long id = db.insert(TABLE_ITEMS, null, values);
        db.close();
        return id;
    }

    // Getting single item
    public Cursor getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_ITEMS, new String[] {
                KEY_ITEM_ID, KEY_ITEM_TYPE, KEY_ITEM_NAME, KEY_ITEM_PHONE, 
                KEY_ITEM_DESCRIPTION, KEY_ITEM_DATE, KEY_ITEM_LOCATION
            }, KEY_ITEM_ID + "=?", new String[] { String.valueOf(id) }, 
            null, null, null, null);
        
        if (cursor != null)
            cursor.moveToFirst();
        
        return cursor;
    }

    // Getting All Items
    public Cursor getAllItems() {
        String selectQuery = "SELECT * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    // Getting Items by Type (Lost or Found)
    public Cursor getItemsByType(String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_ITEMS, new String[] {
                KEY_ITEM_ID, KEY_ITEM_TYPE, KEY_ITEM_NAME, KEY_ITEM_PHONE, 
                KEY_ITEM_DESCRIPTION, KEY_ITEM_DATE, KEY_ITEM_LOCATION
            }, KEY_ITEM_TYPE + "=?", new String[] { type }, 
            null, null, null, null);
        
        return cursor;
    }

    // Updating single item
    public int updateItem(int id, String type, String name, String phone, String description, String date, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put(KEY_ITEM_TYPE, type);
        values.put(KEY_ITEM_NAME, name);
        values.put(KEY_ITEM_PHONE, phone);
        values.put(KEY_ITEM_DESCRIPTION, description);
        values.put(KEY_ITEM_DATE, date);
        values.put(KEY_ITEM_LOCATION, location);
        
        // Updating row
        return db.update(TABLE_ITEMS, values, KEY_ITEM_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    // Deleting single item
    public boolean deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_ITEMS, KEY_ITEM_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
        return result > 0;
    }

    // Getting items count
    public int getItemsCount() {
        String countQuery = "SELECT * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}