package com.example.cryptoportfolioapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

        Button buttonPortfolio = findViewById(R.id.buttonPortfolio);
        buttonPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PortfolioActivity.this, SetCryptoAssetActivity.class);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(PortfolioActivity.this,
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                startActivity(intent, bundle);
                finish();
            }
        });

        setPieChartData();
        setAssetsValue();
    }

    private void setAssetsValue() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_PREFIX, 0);

        TextView tvBitcoinPercentage = findViewById(R.id.tvBitcoinInDolars);
        tvBitcoinPercentage.setText("$" + String.valueOf(sharedPreferences.getFloat("BTCUSDTByAmount", 0)));

        TextView tvEthereumPercentage = findViewById(R.id.tvEthereumInDolars);
        tvEthereumPercentage.setText("$" + String.valueOf(sharedPreferences.getFloat("ETHUSDTByAmount", 0)));

        TextView tvCardanoPercentage = findViewById(R.id.tvCardanoInDolars);
        tvCardanoPercentage.setText("$" + String.valueOf(sharedPreferences.getFloat("ADAUSDTByAmount", 0)));

        TextView tvXRPPercentage = findViewById(R.id.tvXRPInDolars);
        tvXRPPercentage.setText("$" + String.valueOf(sharedPreferences.getFloat("XRPUSDTByAmount", 0)));

        TextView tvSolanaPercentage = findViewById(R.id.tvSolanaInDolars);
        tvSolanaPercentage.setText("$" + String.valueOf(sharedPreferences.getFloat("SOLUSDTByAmount", 0)));

        TextView tvPolkadotPercentage = findViewById(R.id.tvPolkadotInDolars);
        tvPolkadotPercentage.setText("$" + String.valueOf(sharedPreferences.getFloat("DOTUSDTByAmount", 0)));

        TextView tvAvaxPercentage = findViewById(R.id.tvAvaxInDolars);
        tvAvaxPercentage.setText("$" + String.valueOf(sharedPreferences.getFloat("AVAXUSDTByAmount", 0)));

        TextView tvMaticPercentage = findViewById(R.id.tvMaticInDolars);
        tvMaticPercentage.setText("$" + String.valueOf(sharedPreferences.getFloat("MATICUSDTByAmount", 0)));

        TextView tvLinkPercentage = findViewById(R.id.tvLinkInDolars);
        tvLinkPercentage.setText("$" + String.valueOf(sharedPreferences.getFloat("LINKUSDTByAmount", 0)));
    }

    private void setPieChartData() {
        PieChart pieChart;

        pieChart = findViewById(R.id.piechart);

        pieChart.addPieSlice(
                new PieModel(
                        "BTCUSDT",
                        10,
                        Color.parseColor("#FF3700B3")));
        pieChart.addPieSlice(
                new PieModel(
                        "ETHUSDT",
                        0,
                        Color.parseColor("#FF6200EE")));
        pieChart.addPieSlice(
                new PieModel(
                        "ADAUSDT",
                        0,
                        Color.parseColor("#FFBB86FC")));
        pieChart.addPieSlice(
                new PieModel(
                        "XRPUSDT",
                        0,
                        Color.parseColor("#29B6F6")));
        pieChart.addPieSlice(
                new PieModel(
                        "SOLUSDT",
                        0,
                        Color.parseColor("#FF03DAC5")));
        pieChart.addPieSlice(
                new PieModel(
                        "DOTUSDT",
                        0,
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "AVAXUSDT",
                        0,
                        Color.parseColor("#FF018786")));
        pieChart.addPieSlice(
                new PieModel(
                        "MATICUSDT",
                        0,
                        Color.parseColor("#29B6F6")));
        pieChart.addPieSlice(
                new PieModel(
                        "LINKUSDT",
                        0,
                        Color.parseColor("#024265")));

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