package com.example.cs401_project3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private Button btnMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        btnMessage = (Button)findViewById(R.id.messageBtn);
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HomeActivity.this, MessageBoxActivity.class);
                startActivity(intent);
            }
        });
    }
}
