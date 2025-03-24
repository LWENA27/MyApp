package com.myapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MyInfoActivity extends AppCompatActivity {
    private EditText editUsername, editLocation, editPhoneNumber;
    private UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        editUsername = findViewById(R.id.editUsername);
        editLocation = findViewById(R.id.editLocation);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        dbHelper = new UserDatabaseHelper(this);

        loadUserData();

        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });
    }

    private void loadUserData() {
        Cursor cursor = dbHelper.getUser();
        if (cursor.moveToFirst()) {
            int usernameIndex = cursor.getColumnIndex("username");
            int locationIndex = cursor.getColumnIndex("location");
            int phoneIndex = cursor.getColumnIndex("phone");

            if (usernameIndex != -1) {
                String username = cursor.getString(usernameIndex);
                editUsername.setText(username);
            }

            if (locationIndex != -1) {
                String location = cursor.getString(locationIndex);
                editLocation.setText(location);
            }

            if (phoneIndex != -1) {
                String phone = cursor.getString(phoneIndex);
                editPhoneNumber.setText(phone);
            }
        }
        cursor.close();
    }

    private void saveUserData() {
        String username = editUsername.getText().toString();
        String location = editLocation.getText().toString();
        String phone = editPhoneNumber.getText().toString();

        int rowsUpdated = dbHelper.updateUser(username, location, phone);
        if (rowsUpdated > 0) {
            Toast.makeText(this, "User data updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to update user data", Toast.LENGTH_SHORT).show();
        }
    }
}