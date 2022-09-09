package com.example.businessapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateStockData extends AppCompatActivity {

    private EditText fieldStockName, fieldAmount;
    private final String TAG = "Match";
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stock_data);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        fieldStockName = findViewById(R.id.fieldStockName);
        fieldAmount = findViewById(R.id.fieldStockPrice);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnBackToStock = findViewById(R.id.btnBackToStock);

        btnUpdate.setOnClickListener(v -> updateStockDetail());
        btnBackToStock.setOnClickListener(v -> {
            startActivity(new Intent(UpdateStockData.this, StockManagement.class));
            finish();
        });
    }

    // Function to take details of sold item i.e., stock name and quantity
    // check for the item in database and if available
    // update the quantity of stock and update
    // the account balance accordingly
    private void updateStockDetail() {
        // Take the input from user into edit fields and convert them into string
        String stockName = fieldStockName.getText().toString();
        String amount = fieldAmount.getText().toString();

        // Check whether the stock name field is non-empty
        // if empty show a message to enter it.
        if (TextUtils.isEmpty(stockName)) {
            Toast.makeText(getApplicationContext(),
                            "Please Enter Stock Name!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Check whether the stock quantity field is non-empty
        // if empty show a message to enter it.
        if (TextUtils.isEmpty(amount)) {
            Toast.makeText(getApplicationContext(),
                            "Please Enter Stock Price!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Find the stock item in the database using the entered name
        // if available update the quantity of stock and update
        // the account balance accordingly
        DocumentReference docRef = db.collection("stocks").document(stockName);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            Stock stock  = documentSnapshot.toObject(Stock.class);

            if (stock == null) {
                // stock not found add the details
                stock = new Stock(stockName, "0", amount);
            } else {
                // stock found update details
                stock.setStockPrice(amount);
            }

            docRef.set(stock)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(getApplicationContext(),
                                        "Successfully updated: " + stockName + ".",
                                        Toast.LENGTH_LONG)
                                .show();
                    })
                    .addOnFailureListener(e -> {
                        Log.w(TAG, "Error writing document", e);
                        Toast.makeText(getApplicationContext(),
                                        "Sorry! Error occurred.",
                                        Toast.LENGTH_LONG)
                                .show();
                    });
        });
    }
}