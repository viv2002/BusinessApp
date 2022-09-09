package com.example.businessapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CustomerManagement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_management);

        Button btnAddRecord = findViewById(R.id.btnAddCustomerRecord);
        Button btnSearchRecord = findViewById(R.id.btnSearchRecord);
        Button btnViewDebtList = findViewById(R.id.btnViewDebtList);
        Button btnUpdateCustomerRecord = findViewById(R.id.btnUpdateCustomerRecord);
        Button btnBackToHome = findViewById(R.id.btnBackToHome);

        btnAddRecord.setOnClickListener(v -> {
            startActivity(new Intent(CustomerManagement.this, AddCustomerRecord.class));
            finish();
        });

        btnSearchRecord.setOnClickListener(v -> {
            startActivity(new Intent(CustomerManagement.this, CustomerSearch.class));
            finish();
        });

        btnViewDebtList.setOnClickListener(v -> {
            startActivity(new Intent(CustomerManagement.this, CustomerList.class));
            finish();
        });

        btnUpdateCustomerRecord.setOnClickListener(v -> {
            startActivity(new Intent(CustomerManagement.this, UpdateCustomerRecord.class));
            finish();
        });

        btnBackToHome.setOnClickListener(v -> {
            startActivity(new Intent(CustomerManagement.this, BusinessActivity.class));
            finish();
        });
    }
}
