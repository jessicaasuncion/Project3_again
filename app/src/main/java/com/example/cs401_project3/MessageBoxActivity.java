package com.example.cs401_project3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MessageBoxActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_message);
    }

    public void ContentActivity(View view)
    {
        Intent intent = new Intent(this, MessageListActivity.class);
        startActivity(intent);
    }
}