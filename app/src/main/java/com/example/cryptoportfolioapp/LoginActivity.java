package com.example.cryptoportfolioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        TextView labelSwitchToRegister = findViewById(R.id.labelSwitchToRegister);
        labelSwitchToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToRegister();
            }
        });
    }

    private void switchToRegister(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}