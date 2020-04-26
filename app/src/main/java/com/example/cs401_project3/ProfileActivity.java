package com.example.cs401_project3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        else {
            btnUpdate = (Button)findViewById(R.id.updateBTN);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
                }
            });
        }

    }
}