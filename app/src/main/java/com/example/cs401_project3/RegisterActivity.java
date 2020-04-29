package com.example.cs401_project3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private final static String TAG = "RegisterActivity";

    private FirebaseAuth fAuth;
    private DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private EditText regFirstName, regLastName, regMajor, regEmail, regPassword;
    private Button registerBtn;

    private String firstName, lastName, birthday, major;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        // set text views
        regFirstName = findViewById(R.id.firstNameEnter);
        regLastName = findViewById(R.id.lastNameEnter);
        regMajor = findViewById(R.id.majorEnter);
        regEmail = findViewById(R.id.emailEnter);
        regPassword = findViewById(R.id.passwordEnter);

        // set button views
        registerBtn = findViewById(R.id.registerBTN);


        profileInformation();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            }
        };



        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = regEmail.getText().toString();
                final String password = regPassword.getText().toString();
                final String lastName = regLastName.getText().toString();
                final String firstName = regFirstName.getText().toString();
                final String major = regMajor.getText().toString();


                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Registration error", Toast.LENGTH_LONG).show();
                        } else {
                            String currentUserId = fAuth.getCurrentUser().getUid();
                            DatabaseReference currentUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
                            Map userInfo = new HashMap<>();
                            userInfo.put("FirstName", regFirstName);
                            userInfo.put("LastName", regLastName);
                            userInfo.put("Major", regMajor);
                            userInfo.put("Email", regEmail);
                            userInfo.put("Password", regPassword);

                            currentUserRef.updateChildren(userInfo);
                        }
                    }
                });
            }
        });
    }

    private void profileInformation() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0)
                {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("FirstName") != null)
                    {
                        firstName = map.get("FirstName").toString();
                        regFirstName.setText(firstName);
                    }

                    if(map.get("LastName") != null)
                    {
                        lastName = map.get("LastName").toString();
                        regLastName.setText(lastName);
                    }

                    if(map.get("Major") != null)
                    {
                        major = map.get("Major").toString();
                        regMajor.setText(major);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error seeing profile");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        fAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
