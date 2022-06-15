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

        setAssetsValue();
        setPieChartData();
    }

    private void setAssetsValue() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_PREFIX, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        TextView tvBitcoinInDolars = findViewById(R.id.tvBitcoinInDolars);
        tvBitcoinInDolars.setText("$" + String.valueOf(sharedPreferences.getFloat("BTCUSDTByAmount", 0)));

        TextView tvEthereumInDolars = findViewById(R.id.tvEthereumInDolars);
        tvEthereumInDolars.setText("$" + String.valueOf(sharedPreferences.getFloat("ETHUSDTByAmount", 0)));

        TextView tvCardanoInDolars = findViewById(R.id.tvCardanoInDolars);
        tvCardanoInDolars.setText("$" + String.valueOf(sharedPreferences.getFloat("ADAUSDTByAmount", 0)));

        TextView tvXRPInDolars = findViewById(R.id.tvXRPInDolars);
        tvXRPInDolars.setText("$" + String.valueOf(sharedPreferences.getFloat("XRPUSDTByAmount", 0)));

        TextView tvSolanaInDolars = findViewById(R.id.tvSolanaInDolars);
        tvSolanaInDolars.setText("$" + String.valueOf(sharedPreferences.getFloat("SOLUSDTByAmount", 0)));

        TextView tvPolkadotInDolars = findViewById(R.id.tvPolkadotInDolars);
        tvPolkadotInDolars.setText("$" + String.valueOf(sharedPreferences.getFloat("DOTUSDTByAmount", 0)));

        TextView tvAvaxInDolars = findViewById(R.id.tvAvaxInDolars);
        tvAvaxInDolars.setText("$" + String.valueOf(sharedPreferences.getFloat("AVAXUSDTByAmount", 0)));

        TextView tvMaticInDolars = findViewById(R.id.tvMaticInDolars);
        tvMaticInDolars.setText("$" + String.valueOf(sharedPreferences.getFloat("MATICUSDTByAmount", 0)));

        TextView tvLinkInDolars = findViewById(R.id.tvLinkInDolars);
        tvLinkInDolars.setText("$" + String.valueOf(sharedPreferences.getFloat("LINKUSDTByAmount", 0)));

        float sumOfAssets = sharedPreferences.getFloat("BTCUSDTByAmount", 0)
                          + sharedPreferences.getFloat("ETHUSDTByAmount", 0)
                          + sharedPreferences.getFloat("ADAUSDTByAmount", 0)
                          + sharedPreferences.getFloat("XRPUSDTByAmount", 0)
                          + sharedPreferences.getFloat("SOLUSDTByAmount", 0)
                          + sharedPreferences.getFloat("DOTUSDTByAmount", 0)
                          + sharedPreferences.getFloat("AVAXUSDTByAmount", 0)
                          + sharedPreferences.getFloat("MATICUSDTByAmount", 0)
                          + sharedPreferences.getFloat("LINKUSDTByAmount", 0);

        editor.putFloat("sumOfAssets", sumOfAssets);
        editor.commit();
    }

    private void setPieChartData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_PREFIX, 0);
        PieChart pieChart;

        pieChart = findViewById(R.id.piechart);

        pieChart.addPieSlice(
                new PieModel(
                        "BTCUSDT",
                        (sharedPreferences.getFloat("BTCUSDTByAmount", 0)/sharedPreferences.getFloat("sumOfAssets", 0))*100,
                        Color.parseColor("#FF3700B3")));
        pieChart.addPieSlice(
                new PieModel(
                        "ETHUSDT",
                        (sharedPreferences.getFloat("ETHUSDTByAmount", 0)/sharedPreferences.getFloat("sumOfAssets", 0))*100,
                        Color.parseColor("#FF6200EE")));
        pieChart.addPieSlice(
                new PieModel(
                        "ADAUSDT",
                        (sharedPreferences.getFloat("ADAUSDTByAmount", 0)/sharedPreferences.getFloat("sumOfAssets", 0))*100,
                        Color.parseColor("#FFBB86FC")));
        pieChart.addPieSlice(
                new PieModel(
                        "XRPUSDT",
                        (sharedPreferences.getFloat("XRPUSDTByAmount", 0)/sharedPreferences.getFloat("sumOfAssets", 0))*100,
                        Color.parseColor("#29B6F6")));
        pieChart.addPieSlice(
                new PieModel(
                        "SOLUSDT",
                        (sharedPreferences.getFloat("SOLUSDTByAmount", 0)/sharedPreferences.getFloat("sumOfAssets", 0))*100,
                        Color.parseColor("#FF03DAC5")));
        pieChart.addPieSlice(
                new PieModel(
                        "DOTUSDT",
                        (sharedPreferences.getFloat("DOTUSDTByAmount", 0)/sharedPreferences.getFloat("sumOfAssets", 0))*100,
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "AVAXUSDT",
                        (sharedPreferences.getFloat("AVAXUSDTByAmount", 0)/sharedPreferences.getFloat("sumOfAssets", 0))*100,
                        Color.parseColor("#FF018786")));
        pieChart.addPieSlice(
                new PieModel(
                        "MATICUSDT",
                        (sharedPreferences.getFloat("MATICUSDTByAmount", 0)/sharedPreferences.getFloat("sumOfAssets", 0))*100,
                        Color.parseColor("#29B6F6")));
        pieChart.addPieSlice(
                new PieModel(
                        "LINKUSDT",
                        (sharedPreferences.getFloat("LINKUSDTByAmount", 0)/sharedPreferences.getFloat("sumOfAssets", 0))*100,
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