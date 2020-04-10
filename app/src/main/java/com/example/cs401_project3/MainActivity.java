package com.example.cs401_project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRegister = (Button) findViewById(R.id.button3);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                launchRegisterActivity();
            }
        });
    }

    public void launchRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }
}
