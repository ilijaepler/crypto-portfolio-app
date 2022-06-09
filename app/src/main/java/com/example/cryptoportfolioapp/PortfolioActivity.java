package com.example.cryptoportfolioapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class PortfolioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_portfolio);

        // Initialize Firebase Auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationViewPortfolio);
        bottomNavigationView.setSelectedItemId(R.id.menuItemPortfolio);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.menuItemPortfolio:
                        return true;
                    case R.id.menuItemPrices:
                        Intent intent = new Intent(PortfolioActivity.this, MainActivity.class);
                        Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(PortfolioActivity.this,
                                android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                        startActivity(intent, bundle);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menuItemLogout:
                        // Logout user
                        logoutUser();
                        return true;
                }

                return false;
            }
        });
    }

    // Logging out the user
    private void logoutUser(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(PortfolioActivity.this,
                android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
        startActivity(intent, bundle);
        finish();
    }
}