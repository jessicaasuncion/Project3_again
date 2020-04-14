package com.example.cs401_project3;

import android.content.Intent;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {

    EditText firstName, lastName, horizonEmail, pass1, pass2;
    Button btnSignIn;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        mFirebaseAuth = FirebaseAuth.getInstance();
        firstName = findViewById(R.id.edit_first);
        lastName = findViewById(R.id.edit_last);
        horizonEmail = findViewById(R.id.edit_email);
        pass1 = findViewById(R.id.edit_pass1);
        pass2 = findViewById(R.id.edit_pass2);
        btnSignIn = findViewById(R.id.signUpBTN);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mFirebaseUser != null) {
                    Toast.makeText(RegisterActivity.this, "Logged in!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Please log in.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = firstName.getText().toString();
                String last = lastName.getText().toString();
                String email = horizonEmail.getText().toString();
                String pwd1 = pass1.getText().toString();
                String pwd2 = pass2.getText().toString();

                if (first.isEmpty()) {
                    firstName.setError("Please enter your email");
                    firstName.requestFocus();
                } else if (last.isEmpty()) {
                    lastName.setError("Please enter your email");
                    lastName.requestFocus();
                }
                // error if user doesn't enter an email
                else if (email.isEmpty()) {
                    horizonEmail.setError("Please enter your email");
                    horizonEmail.requestFocus();
                }
                // error if user doesn't enter a password
                else if (pwd1.isEmpty()) {
                    pass1.setError("Please enter your password");
                    pass1.requestFocus();
                }
                // error if user doesn't enter a confirm password
                else if (pwd2.isEmpty()) {
                    pass2.setError("Please enter your password");
                    pass2.requestFocus();
                }
                // error if user doesn't enter both
                else if (first.isEmpty() && last.isEmpty() && email.isEmpty() && pwd1.isEmpty() && pwd2.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                // if no error...
                else if (!(first.isEmpty() && last.isEmpty() && email.isEmpty() && pwd1.isEmpty() && pwd2.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd1).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Login error. Please login again. ", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intentToHome = new Intent(RegisterActivity.this, HomeActivity.class);
                                startActivity(intentToHome);
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
