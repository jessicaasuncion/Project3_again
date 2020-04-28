package com.example.cs401_project3;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.FieldClassification;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    public static final String UPDATE_KEY = "update_key";

    private FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private Button btnMatch, btnMessage, btnUpdate, btnSignOut;

    private TextView profileFirst, profileLast, profileBirthday, profileMajor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        String userId = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        // initialize views
        profileFirst = findViewById(R.id.firstNameID);
        profileLast = findViewById(R.id.lastNameID);
        profileBirthday = findViewById(R.id.birthdayID);
        profileMajor = findViewById(R.id.majorID);

        // no user is signed in
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        // user is signed in
        ValueEventListener userInfoListener = (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                profileFirst.setText(userInfo.firstName);
                profileLast.setText(userInfo.lastName);
                profileBirthday.setText(userInfo.birthday);
                profileMajor.setText(userInfo.major);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
        databaseReference.addValueEventListener(userInfoListener);

        // match button
        btnMatch = findViewById(R.id.matchBtn);
        btnMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(HomeActivity.this, MatchActivity.class));
            }
        });

        // message button
        btnMessage = findViewById(R.id.messageBtn);
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(HomeActivity.this, MessageBoxActivity.class));
            }
        });

        // update profile button
        btnUpdate = findViewById(R.id.updateBTN);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            }
        });

        // sign out button
        btnSignOut = findViewById(R.id.signOutBtn);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });
    }
}
