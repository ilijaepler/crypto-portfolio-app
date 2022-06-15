package com.example.cryptoportfolioapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

public class SetCryptoAssetActivity extends AppCompatActivity {

    private final static String SHARED_PREFERENCES_PREFIX = "CryptoPrice";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_set_crypto_asset);

        String[] symbols = {"BTCUSDT", "ETHUSDT", "ADAUSDT",
                "XRPUSDT", "SOLUSDT", "DOTUSDT",
                "AVAXUSDT", "MATICUSDT", "LINKUSDT"};

        Spinner spinnerChooseAsset = findViewById(R.id.spinnerChooseCrypto);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, symbols);
        spinnerChooseAsset.setAdapter(adapter);

        EditText inputSetValue = findViewById(R.id.inputSetValue);
        Button buttonAddAsset = findViewById(R.id.buttonAddAsset);
        Button buttonGoBack = findViewById(R.id.buttonAssetGoBack);
        buttonAddAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedItem = String.valueOf(spinnerChooseAsset.getSelectedItem());
                String value = inputSetValue.getText().toString();
                if(value.isEmpty()){
                    Toast.makeText(SetCryptoAssetActivity.this, "You need to enter the amount!", Toast.LENGTH_LONG).show();
                    return;
                }

                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_PREFIX, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                String symbolValueMultipliedByAmount = selectedItem + "ByAmount";
                float valueMultipliedWithAmount = Float.parseFloat(sharedPreferences.getString(selectedItem, "")) * Float.parseFloat(value);
                editor.putFloat(symbolValueMultipliedByAmount, valueMultipliedWithAmount);

                editor.commit();

                Intent intent = new Intent(SetCryptoAssetActivity.this, PortfolioActivity.class);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(SetCryptoAssetActivity.this,
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                startActivity(intent, bundle);
                finish();

            }
        });

        buttonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetCryptoAssetActivity.this, PortfolioActivity.class);
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(SetCryptoAssetActivity.this,
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                startActivity(intent, bundle);
                finish();
            }
        });
    }
}