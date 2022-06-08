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
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            finish();
            return;
        }

        // Registration of the user
        Button buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        // Switching to the login activity
        TextView switchToLogin = findViewById(R.id.labelSwitchToLogin);
        switchToLogin.setOnClickListener(view -> switchToLogin());
    }

    // Registration of the user
    private void registerUser() {
        EditText inputRegisterFirstName = findViewById(R.id.inputRegisterFirstName);
        EditText inputRegisterLastName = findViewById(R.id.inputRegisterLastName);
        EditText inputRegisterEmail = findViewById(R.id.inputRegisterEmail);
        EditText inputRegisterPassword = findViewById(R.id.inputRegisterPassword);

        String firstName = inputRegisterFirstName.getText().toString();
        String lastName = inputRegisterLastName.getText().toString();
        String email = inputRegisterEmail.getText().toString();
        String password = inputRegisterPassword.getText().toString();

        // Checking if the inputs are correct(Regex in the future)
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please fill all the fields!", Toast.LENGTH_LONG).show();
            return;
        }

        // Creation and registration of the user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(firstName, lastName, email);
                            FirebaseDatabase.getInstance().getReference("users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    showMainActivity();
                                }
                            });
                        } else {
                            Toast.makeText(RegisterActivity.this, "Authentication has failed.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void showMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void switchToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}