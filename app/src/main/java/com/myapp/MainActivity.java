package com.myapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText usernameInput, locationInput, phoneInput;
    private UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.usernameInput);
        locationInput = findViewById(R.id.locationInput);
        phoneInput = findViewById(R.id.phoneInput);
        dbHelper = new UserDatabaseHelper(this);

        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });

        findViewById(R.id.userInfoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveUserData() {
        String username = usernameInput.getText().toString();
        String location = locationInput.getText().toString();
        String phone = phoneInput.getText().toString();

        dbHelper.addUser(username, location, phone);
    }
}