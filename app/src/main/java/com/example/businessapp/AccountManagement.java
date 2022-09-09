package com.example.businessapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountManagement extends AppCompatActivity {

    TextView textCurrentBalance;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        Button btnAddSoldItem = findViewById(R.id.btnAddSoldItem);
        Button btnCurrentBalance = findViewById(R.id.btnCurrentBalance);
        textCurrentBalance = findViewById(R.id.textCurrentBalance);
        Button btnBackToHome = findViewById(R.id.btnBackToHome);
        //Button btnViewSalesRecord = findViewById(R.id.btnViewSalesRecord);

        btnAddSoldItem.setOnClickListener(v -> {
            startActivity(new Intent(AccountManagement.this, AddSoldRecord.class));
            finish();
        });

        btnCurrentBalance.setOnClickListener(v -> showBalance());

        /*btnViewSalesRecord.setOnClickListener(v -> {
            startActivity(new Intent(AccountManagement.this, MainActivity.class));
            finish();
        });*/

        btnBackToHome.setOnClickListener(v -> {
            startActivity(new Intent(AccountManagement.this, BusinessActivity.class));
            finish();
        });
    }

    private void showBalance(){
        // Find the current balance in the database
        DocumentReference docRef = db.collection("account").document("balance");
        docRef.get().addOnSuccessListener(documentSnapshot -> {

            Balance balance = documentSnapshot.toObject(Balance.class);
            assert balance != null;
            textCurrentBalance.setText(String.format("Current Balance: %s", balance.getAmount()));

            Toast.makeText(getApplicationContext(),
                            "Imp: Debt is not subtracted.",
                            Toast.LENGTH_LONG)
                    .show();
        });
    }
}
