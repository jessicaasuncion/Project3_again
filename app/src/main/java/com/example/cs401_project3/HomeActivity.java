package com.example.cs401_project3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private Button btnMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        else {
            btnMessage = (Button)findViewById(R.id.messageBtn);
            btnMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    startActivity(new Intent(HomeActivity.this, MessageBoxActivity.class));
                }
            });
        }

    }
}
