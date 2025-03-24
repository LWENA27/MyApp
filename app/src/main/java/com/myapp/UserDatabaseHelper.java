package com.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user_info.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USER = "user";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_PHONE = "phone";

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_LOCATION + " TEXT,"
                + COLUMN_PHONE + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void addUser(String username, String location, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_LOCATION, location);
        values.put(COLUMN_PHONE, phone);

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public Cursor getUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USER, new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_LOCATION, COLUMN_PHONE},
                null, null, null, null, null);
    }

    public int updateUser(String username, String location, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_LOCATION, location);
        values.put(COLUMN_PHONE, phone);

        return db.update(TABLE_USER, values, COLUMN_ID + " = ?",
                new String[]{"1"});
    }
}