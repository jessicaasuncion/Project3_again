package com.example.cs401_project3;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

    private EditText et_firstName, et_lastName, et_username, et_email, et_pass1, et_pass2;
    private Button btn_signUp;

    // declare database reference
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference.CompletionListener firebaseRefListener;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // user is logged in
                if (user != null) {
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };

        // show views
        et_firstName = findViewById(R.id.edit_first);
        et_lastName = findViewById(R.id.edit_last);
        et_username = findViewById(R.id.edit_username);
        et_email = findViewById(R.id.edit_email);
        et_pass1 = findViewById(R.id.edit_pass1);
        et_pass2 = findViewById(R.id.edit_pass2);
        btn_signUp = findViewById(R.id.signUpBTN);

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a Firebase database instance
                //database = FirebaseDatabase.getInstance();
                databaseReference = FirebaseDatabase.getInstance().getReference();
                //firebaseAuth = FirebaseAuth.getInstance();

                final String firstName = et_firstName.getText().toString();
                final String lastName = et_lastName.getText().toString();
                final String username = et_username.getText().toString();
                final String email = et_email.getText().toString();
                final String password = et_pass1.getText().toString();
                final String c_password = et_pass2.getText().toString();

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Registration error", Toast.LENGTH_LONG).show();
                        }
                        else {
                            String userId = firebaseAuth.getCurrentUser().getUid();
                            databaseReference.child("Users").child(userId).setValue(username);
                            firebaseAuth.addAuthStateListener(firebaseAuthStateListener);
                            Intent loginIntent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(loginIntent);
                        }
                    }
                });
            }
        });
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthStateListener);
    }*/

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}