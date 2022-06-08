package com.example.cryptoportfolioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        TextView switchToLogin = findViewById(R.id.labelSwitchToLogin);
        switchToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToLogin();
            }
        });
    }

    private void switchToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}