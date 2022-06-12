package com.example.cryptoportfolioapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.Objects;

public class PortfolioActivity extends AppCompatActivity {

    private final static String SHARED_PREFERENCES_PREFIX = "CryptoPrice";

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

        // testing scrollViewPortfolio
        /*String[] sym = {"1", "2", "3", "4", "5", "6"};


        LinearLayout scrollViewPortfolio = findViewById(R.id.scrollViewPortfolio);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ConstraintLayout item = (ConstraintLayout) inflater.inflate(R.layout.crypto_percentage_view, null);

        for(int i = 0; i < sym.length; i++){
            TextView labelPercentageViewSymbol = item.findViewById(R.id.labelPercentageViewSymbol);
            labelPercentageViewSymbol.setText(sym[i]);

            TextView labelPercentageViewPercentage = item.findViewById(R.id.labelPercentageViewPercentage);
            labelPercentageViewPercentage.setText(sym[i]);
            scrollViewPortfolio.addView(item);
        }*/

        setPieChartData();
        setPercentages();
    }

    private void setPercentages() {

    }

    private void setPieChartData() {
        PieChart pieChart;

        pieChart = findViewById(R.id.piechart);

        pieChart.addPieSlice(
                new PieModel(
                        "R",
                        10,
                        Color.parseColor("#FF3700B3")));
        pieChart.addPieSlice(
                new PieModel(
                        "Python",
                        0,
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "C++",
                        0,
                        Color.parseColor("#EF5350")));
        pieChart.addPieSlice(
                new PieModel(
                        "Java",
                        0,
                        Color.parseColor("#29B6F6")));

        pieChart.startAnimation();
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