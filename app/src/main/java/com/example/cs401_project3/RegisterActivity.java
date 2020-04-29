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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText regFirstName, regLastName, regMajor, regEmail, regPassword;
    private Button registerBtn;

    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        fAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                /*if(user != null)
                {
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                    return;
                }*/
            }
        };

        regFirstName = findViewById(R.id.firstNameEnter);
        regLastName = findViewById(R.id.lastNameEnter);
        regMajor = findViewById(R.id.majorEnter);
        regEmail = findViewById(R.id.emailEnter);
        regPassword = findViewById(R.id.passwordEnter);
        registerBtn = findViewById(R.id.registerBTN);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = regEmail.getText().toString();
                final String password = regPassword.getText().toString();

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
