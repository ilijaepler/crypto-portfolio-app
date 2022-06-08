package com.example.cryptoportfolioapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            finish();
            return;
        }

        Button buttonLogin = findViewById(R.id.buttonLogin);

        // Authentication of the user
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticateUser();
            }
        });

        TextView labelSwitchToRegister = findViewById(R.id.labelSwitchToRegister);

        // Switching to the register activity
        labelSwitchToRegister.setOnClickListener(view -> switchToRegister());
    }

    // Authentication of the user
    private void authenticateUser() {
        EditText inputLoginEmail = findViewById(R.id.inputLoginEmail);
        EditText inputLoginPassword = findViewById(R.id.inputLoginPassword);

        String email = inputLoginEmail.getText().toString();
        String password = inputLoginPassword.getText().toString();

        // Checking if the inputs are correct(Regex in the future)
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please fill all the fields!", Toast.LENGTH_LONG).show();
            return;
        }

        // Signing in the user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            showMainActivity();
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication has failed.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void showMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void switchToRegister(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}