package com.example.businessapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class BusinessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        Button btnStock = findViewById(R.id.btnStock);
        Button btnAccount = findViewById(R.id.btnAccount);
        Button btnCustomer = findViewById(R.id.btnCustomer);
        Button btnLogout = findViewById(R.id.button_Log_out);

        btnStock.setOnClickListener(v -> {
            startActivity(new Intent(BusinessActivity.this, StockManagement.class));
            finish();
        });

        btnAccount.setOnClickListener(v -> {
            startActivity(new Intent(BusinessActivity.this, AccountManagement.class));
            finish();
        });

        btnCustomer.setOnClickListener(v -> {
            startActivity(new Intent(BusinessActivity.this, CustomerManagement.class));
            finish();
        });

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(BusinessActivity.this, MainActivity.class));
            finish();
        });
    }
}
