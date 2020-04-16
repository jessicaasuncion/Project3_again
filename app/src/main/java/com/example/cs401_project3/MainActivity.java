package com.example.cs401_project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button btnSignUp, btnRegister;
    EditText emailID, passwordID;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //STEP1: firebase setup
        // create a Firebase instance
        mFirebaseAuth = FirebaseAuth.getInstance();

        //STEP 2:  setup of login/password gui ojects
        // grab objects for email and password EditTexts
        emailID = findViewById(R.id.emailEnter);
        passwordID = findViewById(R.id.passwordEnter);
        btnSignUp = findViewById(R.id.signUpBTN);

        //STUEP 3:  Login using Firebase
        // Register event listener to login given email & password from Firebase
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailID.getText().toString();
                String pwd = passwordID.getText().toString();
                // error if user doesn't enter an email
                if(email.isEmpty()) {
                    emailID.setError("Please enter your email");
                    emailID.requestFocus();
                }
                // error if user doesn't enter a password
                else if(pwd.isEmpty()) {
                    passwordID.setError("Please enter your password");
                    passwordID.requestFocus();
                }
                // error if user doesn't enter both
                else if(email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
                }
                // if no error...
                else if(!(email.isEmpty() && pwd.isEmpty())) {
                    //Toast.makeText(MainActivity.this, email + " " + pwd, Toast.LENGTH_LONG).show();
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // if email & password is correct: login to home page
                            if(task.isSuccessful()) {
                                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                                String s = "Welcome back " + user.getEmail();
                                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            }
                            // if not correct: error message to re try
                            else {
                                Toast.makeText(MainActivity.this, "Incorrect password. Please try again", Toast.LENGTH_SHORT).show();
                                // updateUI(null);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister = (Button) findViewById(R.id.registerBTN);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }
}
