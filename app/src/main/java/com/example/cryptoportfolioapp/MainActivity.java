package com.example.cryptoportfolioapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;

import java.util.LinkedList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private final static String SHARED_PREFERENCES_PREFIX = "CryptoPrice";
    public static LinkedList<String> symbolsSharedPreferences = new LinkedList<>();
    public static LinkedList<String> pricesSharedPreferences = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Redirecting to Login activity if the user is not authenticated
        if(currentUser == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        TextView labelMainTitle = findViewById(R.id.labelMainTitle);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationViewMain);
        bottomNavigationView.setSelectedItemId(R.id.menuItemPrices);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.menuItemPortfolio:
                        Intent intent = new Intent(MainActivity.this, PortfolioActivity.class);
                        Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(MainActivity.this,
                                android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                        startActivity(intent, bundle);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menuItemPrices:
                        return true;
                    case R.id.menuItemLogout:
                        // Logout user
                        logoutUser();
                        return true;
                }

                return false;
            }
        });

        // Getting database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users").child(currentUser.getUid());

        // Getting the information about the user
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if(user != null){
                    labelMainTitle.setText("Hello " + user.getFirstName() + ".");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        initCryptoPairsAndSharedPreferences();
    }

    private void initCryptoPairsAndSharedPreferences() {
        // Read data from Binance API
        API.getJSON("https://api.binance.com/api/v3/ticker/price", new ReadDataHandler(){

            @Override
            public void handleMessage(@NonNull Message msg) {
                String response = getJson();

                try{
                    JSONArray array = new JSONArray(response);
                    LinkedList<CryptoPairModel> cryptoPairs = CryptoPairModel.parseJSONArray(array);
                    LinearLayout mainScrollView = findViewById(R.id.mainScrollView);
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    String[] symbols = {"BTCUSDT", "ETHUSDT", "ADAUSDT",
                                        "XRPUSDT", "SOLUSDT", "DOTUSDT",
                                        "AVAXUSDT", "MATICUSDT", "LINKUSDT"};

                    for(CryptoPairModel cp : cryptoPairs){
                        ConstraintLayout item = (ConstraintLayout) inflater.inflate(R.layout.crypto_pair_view, null);

                        for(int i = 0; i < symbols.length; i++){
                            if(symbols[i].equals(cp.getSymbol())){
                                TextView labelSymbol = item.findViewById(R.id.labelViewSymbol);
                                labelSymbol.setText(cp.getSymbol());

                                labelSymbol.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String url = "https://www.tradingview.com/symbols/" + cp.getSymbol() + "/";

                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse(url));
                                        startActivity(intent);
                                    }
                                });

                                TextView labelValue = item.findViewById(R.id.labelViewPrice);
                                labelValue.setText("$" + cp.getPrice());

                                mainScrollView.addView(item);

                                symbolsSharedPreferences.add(cp.getSymbol());
                                pricesSharedPreferences.add(cp.getPrice());

                            }
                        }
                    }

                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_PREFIX, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    for(int i = 0; i < symbolsSharedPreferences.size(); i++){
                        String symbolSharedPreferences = symbolsSharedPreferences.get(i);
                        String priceSharedPreference = pricesSharedPreferences.get(i);
                        editor.putString(symbolSharedPreferences, priceSharedPreference);
                    }

                    editor.commit();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


    // Logging out the user
    private void logoutUser(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(MainActivity.this,
                android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
        startActivity(intent, bundle);
        finish();
    }
}