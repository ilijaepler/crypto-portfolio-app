package com.example.cryptoportfolioapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Objects;

public class SetCryptoAssetActivity extends AppCompatActivity {

    private final static String SHARED_PREFERENCES_PREFIX = "CryptoPrice";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_set_crypto_asset);
    }
}