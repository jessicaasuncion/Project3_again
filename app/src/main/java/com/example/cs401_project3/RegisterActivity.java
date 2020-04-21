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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText et_firstName, et_lastName, et_email, et_pass1, et_pass2;
    Button btn_signUp;
    User user;

    // declare database reference
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        // show views
        et_firstName = findViewById(R.id.edit_first);
        et_lastName = findViewById(R.id.edit_last);
        et_email = findViewById(R.id.edit_email);
        et_pass1 = findViewById(R.id.edit_pass1);
        et_pass2 = findViewById(R.id.edit_pass2);
        btn_signUp = findViewById(R.id.signUpBTN);

        // Create a Firebase database instance
        database = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        firebaseAuth = FirebaseAuth.getInstance();

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstName = et_firstName.getText().toString();
                final String lastName = et_lastName.getText().toString();
                final String email = et_email.getText().toString();
                final String password = et_pass1.getText().toString();
                final String c_password = et_pass2.getText().toString();

                // if any field is empty...
                if (TextUtils.isEmpty(firstName)) {
                    Toast.makeText(RegisterActivity.this, "Please enter first name", Toast.LENGTH_LONG).show();
                }
                if (TextUtils.isEmpty(lastName)) {
                    Toast.makeText(RegisterActivity.this, "Please enter last", Toast.LENGTH_LONG).show();
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "Please enter email", Toast.LENGTH_LONG).show();
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
                }
                if (TextUtils.isEmpty(c_password)) {
                    Toast.makeText(RegisterActivity.this, "Please confirm password", Toast.LENGTH_LONG).show();
                }

                User information = new User(firstName, lastName, email, password, c_password);
                register(email, password);
            }
        });
    }

    public void register(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateDatabase(user);
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void updateDatabase(FirebaseUser currentUser) {
        String keyid = databaseReference.push().getKey();
        databaseReference.child(keyid).setValue(user);
        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }
}
