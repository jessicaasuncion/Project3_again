package com.example.cs401_project3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MessageBoxActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_message);

        textView = (TextView) findViewById(R.id.nameTextView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MessageBoxActivity.this, MessageListActivity.class);
                startActivity(intent);

                Toast.makeText(MessageBoxActivity.this, "Error in MessageBoxActivity", Toast.LENGTH_SHORT).show();
            }
        });

    }




}
