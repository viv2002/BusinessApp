package com.example.businessapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StockManagement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_management);


        Button btnSearchItem = findViewById(R.id.btnSearchItem);
        Button btnViewStockList = findViewById(R.id.btnViewStockList);
        Button btnUpdateStockData = findViewById(R.id.btnUpdateStockData);
        Button btnBackToHome = findViewById(R.id.btnBackToHome);


        btnViewStockList.setOnClickListener(v -> {
            startActivity(new Intent(StockManagement.this, StockList.class));
            finish();
        });

        btnSearchItem.setOnClickListener(v -> {
            startActivity(new Intent(StockManagement.this, StockSearch.class));
            finish();
        });

        btnUpdateStockData.setOnClickListener(v -> {
            startActivity(new Intent(StockManagement.this, UpdateStockData.class));
            finish();
        });

        btnBackToHome.setOnClickListener(v -> {
            startActivity(new Intent(StockManagement.this, BusinessActivity.class));
            finish();
        });
    }
}
