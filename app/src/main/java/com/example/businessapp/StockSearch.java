package com.example.businessapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class StockSearch extends AppCompatActivity {

    private EditText fieldStockName;
    TextView textStockName, textStockQuantity, textStockPrice;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_search);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        fieldStockName = findViewById(R.id.fieldStockName);
        Button btnSearchStockData = findViewById(R.id.btnSearchStockData);
        textStockName = findViewById(R.id.textStockName);
        textStockQuantity = findViewById(R.id.textStockQuantity);
        textStockPrice = findViewById(R.id.textStockPrice);
        Button btnBackToStock = findViewById(R.id.btnBackToStock);

        btnSearchStockData.setOnClickListener(v -> searchStockItem());
        btnBackToStock.setOnClickListener(v -> {
            startActivity(new Intent(StockSearch.this, StockManagement.class));
            finish();
        });
    }

    // Function to search whether an item is available in the store
    // and if available how much quantity is present
    private void searchStockItem() {

        // Take the input from user into edit fields and convert them into string
        String stockName = fieldStockName.getText().toString();

        // Check whether the stock name field is non-empty
        // if empty show a message to enter it.
        if (TextUtils.isEmpty(stockName)) {
            Toast.makeText(getApplicationContext(),
                    "Please Enter Stock Name!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Find the stock item in the database using the entered name
        // if available show the details
        DocumentReference docRef = db.collection("stocks").document(stockName);
        docRef.get().addOnSuccessListener(documentSnapshot -> {

            Stock stocks = documentSnapshot.toObject(Stock.class);

            if(stocks != null && stocks.getStockName().equals(stockName)){
                // stock found
                // show the details
                textStockName.setText(String.format("Stock Name: %s", stocks.getStockName()));
                textStockQuantity.setText(String.format("Stock Quantity: %s", stocks.getStockQuantity()));
                textStockPrice.setText(String.format("Stock Price: %s", stocks.getStockPrice()));
            }
            else{
                // stock not found
                // show not found message
                Toast.makeText(getApplicationContext(),
                        "Sorry No Record Found With Name: "+ stockName +".",
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}
