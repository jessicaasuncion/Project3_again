package com.example.cs401_project3;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private Button btnUpdate;
    private EditText profileFirst, profileLast, profileBirthday, profileMajor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users/userId");

        profileFirst = findViewById(R.id.firstNameEnter);
        profileLast = findViewById(R.id.lastNameEnter);
        profileBirthday = findViewById(R.id.birthdayEnter);
        profileMajor = findViewById(R.id.majorEnter);

        btnUpdate = findViewById(R.id.updateBTN);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = profileFirst.getText().toString();
                String lastName = profileLast.getText().toString();
                String birthday = profileBirthday.getText().toString();
                String major = profileMajor.getText().toString();

                String userId = firebaseAuth.getCurrentUser().getUid();
                UserInfo userInfo = new UserInfo(firstName, lastName, birthday, major);
                databaseReference.child(userId).child("firstName").setValue(firstName);
                databaseReference.child(userId).child("lastName").setValue(lastName);
                databaseReference.child(userId).child("birthday").setValue(birthday);
                databaseReference.child(userId).child("major").setValue(major);

                startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
                }
        });
    }
}
